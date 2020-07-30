package com.jhw.swing.models.detail;

import com.jhw.swing.bundles.dialog.DialogPanel;
import java.awt.Component;
import javax.swing.JPanel;
import com.jhw.utils.interfaces.Update;

/**
 * Dialogo para mostrar las ventanas de detalles de los modelos.<\br>
 * Basicamente lo qeu hace es recivir un actualizable y actualizarlo cuando se
 * cierre el dialog.
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DialogDetail extends DialogPanel {

    private final Update[] actualizables;

    public DialogDetail(Update act, String title, JPanel panel) {
        this(new Update[]{act}, title, panel);
    }

    public DialogDetail(Update act[], String title, JPanel panel) {
        super(title, panel);
        this.actualizables = act;
        addListenrs();
        this.setSize((int) Math.max(600, panel.getPreferredSize().getWidth()), (int) Math.max(500, panel.getPreferredSize().getHeight()));
        this.setLocationRelativeTo(null);
    }

    private void addListenrs() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                actualizarActualizables();
            }
        });
    }

    private void actualizarActualizables() {
        for (Update act : actualizables) {
            act.update();//actualiza todos
            if (act instanceof JPanel) {//si es panel se mete adentro
                actualizarCascade((JPanel) act);
            }
        }
    }

    private void actualizarCascade(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof Update) {//si el actaulizable lo actualiza
                ((Update) c).update();
            }
            if (c instanceof JPanel) {//si es panel se mete adentro
                actualizarCascade((JPanel) c);
            }
        }
    }

}
