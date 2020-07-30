package com.jhw.swing.models.input.dialogs;

import com.clean.core.app.services.Notification;
import com.clean.core.app.services.NotificationsGeneralType;
import com.jhw.swing.models.input.panels.BaseModelInputMixPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import com.jhw.swing.models.input.panels.ModelMixPanel;
import java.awt.event.KeyEvent;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import com.jhw.swing.util.UpdateCascade;
import com.jhw.utils.interfaces.Update;
import com.jhw.swing.util.interfaces.ModelablePanel;

/**
 * Dialogo para la creacion de modelos mixtos.<br/>
 *
 * Ejemplo: new _DialogModelInput(this, new ModelPanelTest(1));
 *
 * @author Yo
 * @param <T>
 */
public class DialogModelMixInput<T> extends JDialog implements ModelablePanel<T> {

    private final BaseModelInputMixPanel<T> basePanel;
    private final UpdateCascade aa;

    public DialogModelMixInput(Update act, ModelMixPanel modelPanel) {
        this(new Update[]{act}, modelPanel);
    }

    public DialogModelMixInput(Update act[], ModelMixPanel modelPanel) {
        super();
        this.aa = new UpdateCascade(act);
        this.basePanel = new BaseModelInputMixPanel(modelPanel);
        this.setLayout(new GridLayout(1, 1));
        this.add(basePanel);

        int width = basePanel.getPreferredSize().width;
        int height = basePanel.getPreferredSize().height + basePanel.getPanelGradientButtons().getPreferredSize().height;

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
        this.setResizable(false);

        addListeners();
        personalize();

        this.setVisible(true);
    }

    private void addListeners() {
        basePanel.getMixPanel().getButtonAddEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCreateAction();
            }
        });
        basePanel.getMaterialButtonCancel().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onCancelAction();
            }
        });
        basePanel.getMaterialButtonDelete().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onDeleteAction();
            }
        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {
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

    public void addDeleteActionListener(ActionListener action) {
        basePanel.getMaterialButtonDelete().addActionListener(action);
    }

    public void addCancelActionListener(ActionListener action) {
        basePanel.getMaterialButtonCancel().addActionListener(action);
    }

    @Override
    public T getNewModel() {
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

    private void personalize() {
        if (getOldModel() == null) {
            this.setTitle("Crear");
        } else {
            this.setTitle("Editar");
        }
    }

    @Override
    public T onDeleteAction() {
        T obj = null;
        try {
            if (Notification.showConfirmDialog(NotificationsGeneralType.CONFIRM_DELETE)) {
                obj = basePanel.onDeleteAction();
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
                    Notification.showNotification(NotificationsGeneralType.NOTIFICATION_CREATE, obj);
                }
            } else {
                if (Notification.showConfirmDialog(NotificationsGeneralType.CONFIRM_EDIT)) {
                    obj = basePanel.onCreateAction();
                    Notification.showNotification(NotificationsGeneralType.NOTIFICATION_EDIT, obj);
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
        if (obj != null) {
            try {
                basePanel.onPostCreateAction(obj);
            } catch (Exception e) {
            }
            actualizarActualizables();
            basePanel.setOldModel(obj);
            basePanel.update();
        }
        return obj;
    }

    @Override
    public T onPostDeleteAction(T obj) {
        if (obj != null) {
            try {
                obj = (T) basePanel.onPostDeleteAction(obj);
            } catch (Exception e) {
            }
            if (obj != null) {
                actualizarActualizables();
                Notification.showNotification(NotificationsGeneralType.NOTIFICATION_DELETE, obj);
                dispose();
            }
        }
        return obj;
    }

    public void actualizarActualizables() {
        aa.updateCascade();
    }
}
