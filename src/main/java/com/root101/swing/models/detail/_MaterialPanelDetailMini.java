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

import com.root101.clean.core.domain.DomainObject;
import com.root101.swing.material.components.table.Column;
import com.root101.swing.material.standards.MaterialColors;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public abstract class _MaterialPanelDetailMini<T extends DomainObject> extends _MaterialPanelDetail<T> {

    public _MaterialPanelDetailMini() {
        this(new Column[]{});
    }
    private boolean vis = false;

    public _MaterialPanelDetailMini(Column... arr) {
        super(arr);
        this.setOptionPanelVisibility(false);
        this.setActionColumnVisivility(false);
        this.getLabelHeader().setVisible(false);
        this.setActionColumnButtonsVisivility(true, true, false);
        this.setElevationVisibility(false);
        this.setBackground(MaterialColors.TRANSPARENT);
        this.getLabelHeader().setForeground(MaterialColors.BLACK);
    }

    @Override
    public double getElevation() {
        return vis ? 1 : 0;
    }

    public void setElevationVisibility(boolean vis) {
        this.vis = vis;
        this.repaint();
    }

}
