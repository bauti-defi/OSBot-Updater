package org.osbot.updater.analysers;

/**
 * Created by Ethan on 1/25/2018.
 */
public class HookFrame {
    private String key;
    private String clazz;
    private String field;
    private String desc;

    public HookFrame(String key, String clazz, String field, String desc) {
        this.key = key;
        this.clazz = clazz;
        this.field = field;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getClazz() {
        return clazz;
    }

    public String getField() {
        return field;
    }

    public String getDesc() {
        return desc;
    }
}
