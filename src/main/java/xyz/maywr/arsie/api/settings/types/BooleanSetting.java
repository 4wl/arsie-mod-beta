package xyz.maywr.arsie.api.settings.types;

import xyz.maywr.arsie.api.settings.Setting;
import xyz.maywr.arsie.api.settings.Type;

public class BooleanSetting extends Setting {

    private String name;
    private boolean value;

    public BooleanSetting(String name, boolean defaultValue){
        this.value = defaultValue;
        this.name = name;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return Type.BOOLEAN;
    }
}
