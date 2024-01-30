package ru.zaralx.bridgebuilders.commons.npc.database.models;

public class ModelColumn {
    private final String name;
    private final String  type;
    private Boolean notNull = false;
    private Boolean primary = false;
    private Boolean autoIncrement = false;

    public ModelColumn(String name, String type, Boolean notNull) {
        this.name = name;
        this.type = type;
        this.notNull = notNull;
    }

    public ModelColumn(String name, String type, Boolean notNull, Boolean primary) {
        this.name = name;
        this.type = type;
        this.notNull = notNull;
        this.primary = primary;
    }

    public ModelColumn(String name, String type, Boolean notNull, Boolean primary, Boolean autoIncrement) {
        this.name = name;
        this.type = type;
        this.notNull = notNull;
        this.primary = primary;
        this.autoIncrement = autoIncrement;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public Boolean getAutoIncrement() {
        return autoIncrement;
    }
}
