package com.jhw.swing.models.detail;

import com.jhw.module.util.personalization.core.domain.Personalization;
import com.jhw.module.util.personalization.services.PersonalizationHandler;
import com.jhw.swing.bundles.dialog.DialogPanel;
import com.jhw.swing.util.UpdateCascade;
import javax.swing.JPanel;
import com.jhw.utils.interfaces.Update;

/**
 * Dialogo para mostrar las ventanas de detalles de los modelos.<\br>
 * Basicamente lo que hace es recivir un actualizable y actualizarlo cuando se
 * cierre el dialog.
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DialogDetail extends DialogPanel {

    private final UpdateCascade actualizables;

    public DialogDetail(Update act, String title, JPanel panel) {
        this(new Update[]{act}, title, panel);
    }

    public DialogDetail(Update act[], String title, JPanel panel) {
        super(title, panel);

        this.actualizables = UpdateCascade.from(act);

        addListeners();

        this.setSize((int) Math.max(600, panel.getPreferredSize().getWidth()), (int) Math.max(500, panel.getPreferredSize().getHeight()));
        this.setLocationRelativeTo(null);
        
        this.setIconImage(PersonalizationHandler.getDerivableIcon(Personalization.KEY_ICON_BUTTON_VIEW).getImage());
    }

    private void addListeners() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                actualizables.updateCascade();
            }
        });
    }

}
