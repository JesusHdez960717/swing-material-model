package com.jhw.swing.models.detail;

import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.material.standards.MaterialColors;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public abstract class _MaterialPanelDetailMini<T> extends _MaterialPanelDetail<T> {

    public _MaterialPanelDetailMini() {
        this(new Column[]{});
    }

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

    public void setElevationVisibility(boolean vis) {
        this.setElevation(vis ? 1 : 0);
    }

}
