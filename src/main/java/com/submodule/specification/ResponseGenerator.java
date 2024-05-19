package com.submodule.specification;

import java.util.List;

/**
 * @author a.mehdizadeh on 5/5/2024
 */
public class ResponseGenerator<T> {

    private Long total;

    private List<T> object;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getObject() {
        return object;
    }

    public void setObject(List<T> object) {
        this.object = object;
    }
}
