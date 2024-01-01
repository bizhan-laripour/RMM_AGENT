package com.submodule.dto;


public class ZabbixResultItemDto {

    private String id;
    private String itemid;

    private String name;

    private String lastvalue;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastvalue() {
        return lastvalue;
    }

    public void setLastvalue(String lastvalue) {
        this.lastvalue = lastvalue;
    }
}
