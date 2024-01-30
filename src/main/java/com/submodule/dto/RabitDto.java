package com.submodule.dto;

import com.submodule.entity.Alarm;

import java.util.List;

public class RabitDto {

    private List<Alarm> alarms;

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
}
