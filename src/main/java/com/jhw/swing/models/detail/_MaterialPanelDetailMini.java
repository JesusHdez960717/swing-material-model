package com.jhw.swing.models.detail;

import com.clean.core.domain.DomainObject;
import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.material.standards.MaterialColors;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
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
