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
package com.root101.swing.models.example;

import com.root101.swing.material.standards.MaterialIcons;
import com.root101.swing.models.input.icbs.InputComboBoxSelection;
import com.root101.swing.models.input.panels.ModelPanel;
import java.util.List;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class CargoICBS extends InputComboBoxSelection<CargoModel> {

    public CargoICBS() {
        setLabel("Cargo");
        setHint("Hint de los cojones");
        setIcon(MaterialIcons.EDIT);
    }

    @Override
    public List<CargoModel> getList() throws RuntimeException {
        return CargoModel.getCargos();
    }

    @Override
    public ModelPanel<CargoModel> inputPanel() {
        return new CargoInputView(null);
    }

    @Override
    protected void addPropertyChange() {
        CargoModel.addPropertyChangeListener(this);
    }

}
