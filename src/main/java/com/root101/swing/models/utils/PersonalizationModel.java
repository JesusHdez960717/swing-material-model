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
package com.root101.swing.models.utils;

import com.root101.module.util.personalization.services.PersonalizationHandler;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
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
