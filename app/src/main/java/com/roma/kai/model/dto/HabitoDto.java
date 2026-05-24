package com.roma.kai.model.dto;

import com.roma.kai.model.entity.HabitoCatalogoEntity;
import com.roma.kai.model.entity.RegistroHabitoEntity;

import java.util.List;

public class HabitoDto extends HabitoCatalogoEntity {
    private List<RegistroHabitoEntity> registros;

    public List<RegistroHabitoEntity> getRegistros() {
        return registros;
    }
}
