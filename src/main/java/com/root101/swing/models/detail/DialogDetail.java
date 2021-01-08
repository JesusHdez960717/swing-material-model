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
package com.root101.swing.models.detail;

import com.jhw.module.util.personalization.core.domain.Personalization;
import com.jhw.module.util.personalization.services.PersonalizationHandler;
import com.root101.swing.bundles.dialog.DialogPanel;
import com.root101.swing.util.UpdateCascade;
import javax.swing.JPanel;
import com.root101.utils.interfaces.Update;

/**
 * Dialogo para mostrar las ventanas de detalles de los modelos.<\br>
 * Basicamente lo que hace es recivir un actualizable y actualizarlo cuando se
 * cierre el dialog.
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
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
