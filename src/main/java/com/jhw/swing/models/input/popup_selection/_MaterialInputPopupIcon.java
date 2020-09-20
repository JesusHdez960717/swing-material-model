/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.input.popup_selection;

import com.jhw.swing.material.components.textfield.*;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.material.components.button.MaterialButton;
import com.jhw.swing.material.components.button.MaterialButtonIcon;
import com.jhw.swing.material.components.button.MaterialButtonsFactory;
import com.jhw.swing.material.components.popup.MaterialPopupFactory;
import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.util.PersonalizationMaterial;
import com.jhw.swing.utils.icons.DerivableIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;
import javax.swing.Action;
import javax.swing.Icon;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class _MaterialInputPopupIcon extends MaterialInputPopupIcon {

    public static MaterialInputPopupIcon from() {
        return new _MaterialInputPopupIcon();
    }

    public _MaterialInputPopupIcon() {
        initComponents();
        this.setIcon(MaterialIcons.DATE_RANGE);
    }

    private void initComponents() {
        buttonPopup = MaterialButtonsFactory.buildPopup();
        buttonPopup.setIcon(null);

        buttonIcon = MaterialButtonsFactory.buildIconTransparent();
        buttonIcon.setPaintRipple(false);

        this.setBorder(null);
        this.setLayout(new BorderLayout());
        this.add(buttonPopup, BorderLayout.CENTER);

    }

    private MaterialButton buttonPopup;
    private MaterialButtonIcon buttonIcon;

    @Override
    public void setActions(List<Action> actions) {
        buttonPopup.setComponentPopupMenu(MaterialPopupFactory.buildCircular(actions));
    }

    @Override
    public MaterialButton getButton() {
        return buttonPopup;
    }

    @Override
    public void setIcon(Icon icon) {
        if (!PersonalizationHandler.getBoolean(PersonalizationMaterial.KEY_SHOW_ICON_INPUT)) {
            return;
        }

        int h = (int) this.buttonPopup.getPreferredSize().getHeight();
        if (icon instanceof DerivableIcon) {
            buttonIcon.setIcon(((DerivableIcon) icon).deriveIcon(h * _MaterialTextFieldIcon.ICON_SIZE_REDUCTION));
        } else {
            buttonIcon.setIcon(icon);
        }
        int w = (int) (h * _MaterialTextFieldIcon.ICON_WIDTH_REDUCTION);
        buttonIcon.setPreferredSize(new Dimension(w, h));
        this.add(buttonIcon, BorderLayout.WEST);
    }

    @Override
    public Icon getIcon() {
        return buttonIcon.getIcon();
    }

    @Override
    public void setEnabled(boolean enabled) {
        buttonPopup.setEnabled(enabled);
        buttonIcon.setEnabled(enabled);
    }

    @Override
    public Color getRippleColor() {
        return buttonPopup.getRippleColor();
    }

    @Override
    public void setRippleColor(Color color) {
        buttonPopup.setRippleColor(color);
    }

    @Override
    public void paintRipple(Graphics2D gd) {
        buttonPopup.paintRipple(gd);
    }

    @Override
    public boolean getPaintRipple() {
        return buttonPopup.getPaintRipple();
    }

    @Override
    public void setPaintRipple(boolean bln) {
        buttonPopup.setPaintRipple(bln);
    }

    @Override
    public Color getColorFadeInto() {
        return buttonPopup.getColorFadeInto();
    }

    @Override
    public Color getAccentColorFadeInto() {
        return buttonPopup.getAccentColorFadeInto();
    }

    @Override
    public void setAccentColorFadeInto(Color color) {
        buttonPopup.setAccentColorFadeInto(color);
    }

    @Override
    public double getLevel() {
        return buttonPopup.getLevel();
    }

    @Override
    public double getElevation() {
        return buttonPopup.getElevation();
    }

    @Override
    public void paintElevation(Graphics2D gd) {
        buttonPopup.paintElevation(gd);
    }

    @Override
    public int getBorderRadius() {
        return buttonPopup.getBorderRadius();
    }

    @Override
    public void setBorderRadius(int i) {
        buttonPopup.setBorderRadius(i);
    }

    @Override
    public float getBorderThickness() {
        return buttonPopup.getBorderThickness();
    }

    @Override
    public void setBorderThickness(float f) {
        buttonPopup.setBorderThickness(f);
    }

    @Override
    public Color getBorderColor() {
        return buttonPopup.getBorderColor();
    }

    @Override
    public void setBorderColor(Color color) {
        buttonPopup.setBorderColor(color);
    }

    @Override
    public Stroke getBorderStroke() {
        return buttonPopup.getBorderStroke();
    }

    @Override
    public void setBorderStroke(Stroke stroke) {
        buttonPopup.setBorderStroke(stroke);
    }

    @Override
    public void paintBorder(Component cmpnt, Graphics grphcs, int i, int i1, int i2, int i3) {
        buttonPopup.paintBorder(cmpnt, grphcs, i, i1, i2, i3);
    }

}
