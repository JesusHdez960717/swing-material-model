/*
 * Copyright 2021 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Or read it directly from LICENCE.txt file at the root of this project.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.root101.swing.models.input.dialogs;

import com.root101.clean.core.app.services.NotificationHandler;
import com.root101.clean.core.app.services.NotificationsGeneralType;
import com.root101.module.util.personalization.core.domain.Personalization;
import com.root101.module.util.personalization.services.PersonalizationHandler;
import com.root101.swing.material.components.scrollpane._MaterialScrollPaneCore;
import com.root101.swing.models.input.panels.BaseModelInputMixPanel;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import com.root101.swing.models.input.panels.ModelMixPanel;
import java.awt.event.KeyEvent;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import com.root101.swing.util.UpdateCascade;
import com.root101.utils.interfaces.Update;
import com.root101.swing.models.input.ModelablePanel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import javax.swing.ScrollPaneLayout;
import com.root101.swing.util.Utils;
import java.util.Map;

/**
 * Dialogo para la creacion de modelos mixtos.<br/>
 *
 * Ejemplo: new _DialogModelInput(this, new ModelPanelTest(1));
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 *
 * @param <T>
 */
public class DialogModelMixInput<T> extends JDialog implements ModelablePanel<T> {

    public static DialogModelMixInput from(ModelMixPanel modelPanel) {
        return DialogModelMixInput.builder(modelPanel).build();
    }

    private final BaseModelInputMixPanel<T> basePanel;
    private final UpdateCascade aa;

    private final _MaterialScrollPaneCore scrollPane = new _MaterialScrollPaneCore();

    public DialogModelMixInput(ModelMixPanel modelPanel, Update... act) {
        this.aa = new UpdateCascade(act);
        this.basePanel = new BaseModelInputMixPanel(modelPanel);

        this.setLayout(new BorderLayout());

        //add el scroll
        this.add(scrollPane, BorderLayout.CENTER);

        //add el task pane al scroll
        scrollPane.setLayout(new ScrollPaneLayout());
        scrollPane.setViewportView(basePanel);

        Rectangle screen = Utils.getScreenSize();

        int maxWidth = (int) (screen.getWidth() - 75);
        int maxHeight = (int) (screen.getHeight() - 75);
        int width = basePanel.getPreferredSize().width + 25;
        int height = basePanel.getPreferredSize().height + 25;

        this.setSize(Math.min(maxWidth, width) + 25, Math.min(maxHeight, height) + 25);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
        this.setResizable(true);

        addListeners();
        personalize();

        this.setVisible(true);
    }

    private void addListeners() {
        basePanel.getMixPanel().getButtonAddEdit().addActionListener((ActionEvent e) -> {
            onCreateAction();
        });
        basePanel.getButtonCancel().addActionListener((java.awt.event.ActionEvent evt) -> {
            onCancelAction();
        });
        basePanel.getButtonDelete().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onDeleteAction();
            }
        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                actualizarActualizables();
            }
        });
        globalsKeyListeners(basePanel);
    }

    private void globalsKeyListeners(JPanel panel) {
        for (Component c : panel.getComponents()) {
            addGlobalKeyListeners(c);
            if (c instanceof JPanel) {
                globalsKeyListeners((JPanel) c);
            }
        }
    }

    private void addGlobalKeyListeners(Component c) {
        if (c == null) {
            return;
        }
        //si es text area no hago nada
        if (c instanceof JTextArea) {
            return;
        }

        //si es texto me fijo en la posicion del caret para eliminar
        if (c instanceof JTextComponent) {
            c.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    int len = ((JTextComponent) c).getText().length();
                    int pos = ((JTextComponent) c).getCaretPosition();
                    if (evt.getKeyChar() == KeyEvent.VK_DELETE && len == pos && basePanel.getOldModel() != null) {
                        onDeleteAction();
                    }
                }
            });
        }

        //enter y escape para el resto
        c.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                    onCreateAction();
                } else if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    onCancelAction();
                }
            }
        });
    }

    @Override
    public T getNewModel() throws Exception {
        return (T) basePanel.getNewModel();
    }

    @Override
    public T getOldModel() {
        return (T) basePanel.getOldModel();
    }

    @Override
    public Map<String, Object> bindFields() {
        return basePanel.bindFields();
    }

    @Override
    public void setOldModel(T model) {
        basePanel.setOldModel(model);
    }

    private void personalize() {
        if (getOldModel() == null) {
            this.setTitle("Crear");
            this.setIconImage(PersonalizationHandler.getDerivableIcon(Personalization.KEY_ICON_BUTTON_ADD).getImage());
        } else {
            this.setTitle("Editar");
            this.setIconImage(PersonalizationHandler.getDerivableIcon(Personalization.KEY_ICON_BUTTON_EDIT).getImage());
        }
    }

    @Override
    public T onDeleteAction() {
        T obj = null;
        try {
            if (NotificationHandler.showConfirmDialog(NotificationsGeneralType.CONFIRM_DELETE)) {
                obj = basePanel.onDeleteAction();
                if (obj != null) {
                    NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_DELETE, obj);
                }
            }
        } catch (Exception e) {
        }
        return onPostDeleteAction(obj);
    }

    @Override
    public T onCreateAction() {
        T obj = null;
        try {
            boolean create = basePanel.getOldModel() == null;
            if (create) {
                if (NotificationHandler.showConfirmDialog(NotificationsGeneralType.CONFIRM_CREATE)) {
                    obj = basePanel.onCreateAction();
                    if (obj != null) {
                        NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_CREATE, obj);
                    }
                }
            } else {
                if (NotificationHandler.showConfirmDialog(NotificationsGeneralType.CONFIRM_EDIT)) {
                    obj = basePanel.onCreateAction();
                    if (obj != null) {
                        NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_EDIT, obj);
                    }
                }
            }
        } catch (Exception e) {
        }
        return onPostCreateAction(obj);
    }

    @Override
    public boolean onCancelAction() {
        try {
            if (NotificationHandler.showConfirmDialog(NotificationsGeneralType.CONFIRM_CANCEL)) {
                if (basePanel.onCancelAction()) {
                    actualizarActualizables();
                    dispose();
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public T onPostCreateAction(T obj) {
        try {
            basePanel.onPostCreateAction(obj);
        } catch (Exception e) {
        }
        if (obj != null) {
            actualizarActualizables();
            basePanel.setOldModel(obj);
            basePanel.update();
        }
        return obj;
    }

    @Override
    public T onPostDeleteAction(T obj) {
        try {
            obj = (T) basePanel.onPostDeleteAction(obj);
        } catch (Exception e) {
        }
        if (obj != null) {
            actualizarActualizables();
            dispose();
        }
        return obj;
    }

    public void actualizarActualizables() {
        aa.updateCascade();
    }

    public static DialogModelMixInput.builder builder(ModelMixPanel modelPanel) {
        return new DialogModelMixInput.builder(modelPanel);
    }

    public static class builder {

        ModelMixPanel modelPanel;
        Update[] updates = new Update[0];

        public builder(ModelMixPanel modelPanel) {
            this.modelPanel = modelPanel;
        }

        public builder updates(Update... updates) {
            this.updates = updates;
            return this;
        }

        public DialogModelMixInput build() {
            return new DialogModelMixInput(modelPanel, updates);
        }
    }
}
