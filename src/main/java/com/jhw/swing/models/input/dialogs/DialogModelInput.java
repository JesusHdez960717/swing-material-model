package com.jhw.swing.models.input.dialogs;

import com.clean.core.app.services.NotificationsGeneralType;
import com.clean.core.app.services.Notification;
import com.jhw.personalization.core.domain.Personalization;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.material.components.scrollpane._MaterialScrollPaneCore;
import com.jhw.swing.models.input.panels.BaseModelInputPanel;
import com.jhw.swing.models.input.panels.ModelPanel;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import com.jhw.swing.util.UpdateCascade;
import com.jhw.swing.util.Utils;
import com.jhw.utils.interfaces.Update;
import com.jhw.swing.models.input.ModelablePanel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.Map;
import javax.swing.ScrollPaneLayout;

/**
 * Dialogo para la creacion de modelos.<br/>
 * El clasico donde se crean los modelos, es el encargado de mostrar
 * notificaciones de creado o editado y demas.
 *
 * Ejemplo: new DialogModelInput(this, new ModelPanelTest(1));
 *
 * @author Yo
 * @param <T>
 */
public class DialogModelInput<T> extends JDialog implements ModelablePanel<T> {

    private final BaseModelInputPanel<T> basePanel;
    private final UpdateCascade aa;
    private boolean closeAfterCreate = true;

    private final _MaterialScrollPaneCore scrollPane = new _MaterialScrollPaneCore();

    public DialogModelInput(Update act, boolean close, ModelPanel modelPanel) {
        this(new Update[]{act}, modelPanel);
        this.closeAfterCreate = close;
    }

    public DialogModelInput(Update act, ModelPanel modelPanel) {
        this(new Update[]{act}, modelPanel);
    }

    public DialogModelInput(Update act[], ModelPanel modelPanel) {
        super();
        this.aa = new UpdateCascade(act);
        basePanel = new BaseModelInputPanel<>(modelPanel);
        this.setLayout(new BorderLayout());

        //add el scroll
        this.add(scrollPane, BorderLayout.CENTER);

        //add el task pane al scroll
        scrollPane.setLayout(new ScrollPaneLayout());
        scrollPane.setViewportView(basePanel);

        Rectangle screen = Utils.getScreenSize();

        int maxWidth = (int) (screen.getWidth() - 75);
        int maxHeight = (int) (screen.getHeight() - 75);
        int width = basePanel.getPreferredSize().width + 15;
        int height = basePanel.getPreferredSize().height + (isUndecorated() ? 0 : 40);

        this.setSize(Math.min(maxWidth, width) + 25, Math.min(maxHeight, height) + 25);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
        this.setResizable(true);

        addListeners();
        personalize();
        this.setVisible(true);
    }

    private void addListeners() {
        basePanel.getMaterialButtonCancel().addActionListener((java.awt.event.ActionEvent evt) -> {
            onCancelAction();
        });

        basePanel.getMaterialButtonOK().addActionListener((java.awt.event.ActionEvent evt) -> {
            onCreateAction();
        });

        basePanel.getMaterialButtonDelete().addActionListener((java.awt.event.ActionEvent evt) -> {
            onDeleteAction();
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

    public void addOKActionListener(ActionListener action) {
        basePanel.getMaterialButtonOK().addActionListener(action);
    }

    public void addDeleteActionListener(ActionListener action) {
        basePanel.getMaterialButtonDelete().addActionListener(action);
    }

    public void addCancelActionListener(ActionListener action) {
        basePanel.getMaterialButtonCancel().addActionListener(action);
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
    public void setOldModel(T model) {
        basePanel.setOldModel(model);
    }

    @Override
    public Map<String, Object> bindFields() {
        return basePanel.bindFields();
    }

    public BaseModelInputPanel<T> getBasePanel() {
        return basePanel;
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
            if (Notification.showConfirmDialog(NotificationsGeneralType.CONFIRM_DELETE)) {
                obj = basePanel.onDeleteAction();
                if (obj != null) {
                    Notification.showNotification(NotificationsGeneralType.NOTIFICATION_DELETE, obj);
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
                if (Notification.showConfirmDialog(NotificationsGeneralType.CONFIRM_CREATE)) {
                    obj = basePanel.onCreateAction();
                    if (obj != null) {
                        Notification.showNotification(NotificationsGeneralType.NOTIFICATION_CREATE, obj);
                    }
                }
            } else {
                if (Notification.showConfirmDialog(NotificationsGeneralType.CONFIRM_EDIT)) {
                    obj = basePanel.onCreateAction();
                    if (obj != null) {
                        Notification.showNotification(NotificationsGeneralType.NOTIFICATION_EDIT, obj);
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
            if (Notification.showConfirmDialog(NotificationsGeneralType.CONFIRM_CANCEL)) {
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
            obj = basePanel.onPostCreateAction(obj);
        } catch (Exception e) {
        }
        if (obj != null) {
            actualizarActualizables();
            basePanel.setOldModel(obj);
            basePanel.update();
            if (closeAfterCreate) {
                dispose();
            }
        }
        return obj;
    }

    @Override
    public T onPostDeleteAction(T obj) {
        try {
            obj = basePanel.onPostDeleteAction(obj);
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

}
