/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.utils;

import com.jhw.module.util.personalization.services.PersonalizationHandler;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class PersonalizationModel {

    private static final String INIT = init();

    public static final String KEY_SHOW_ICON_DETAIL = INIT + "boolean.show_icon_detail";
    public static final Object VALUE_SHOW_ICON_DETAIL = true;

    static {
        PersonalizationHandler.putObjectIfNotContain(KEY_SHOW_ICON_DETAIL, VALUE_SHOW_ICON_DETAIL);
    }

    private static String init() {
        return "model_personalization.key.";
    }
}
