package com.submodule.dto;

import java.util.Date;
import java.util.List;

public class ZabbixResponseDto {


    private String id;

    private String hostId;
    private String jsonrpc;

    private Date date;

    private List<ZabbixResultItemDto> result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public List<ZabbixResultItemDto> getResult() {
        return result;
    }

    public void setResult(List<ZabbixResultItemDto> result) {
        this.result = result;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
