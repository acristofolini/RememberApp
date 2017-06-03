package com.example.fabiano.rememberapp.data;

import java.io.Serializable;

/**
 * Created by Ana on 5/28/2017.
 * Este registro define os campos e os tipos dos campos da tabela GROUP
 * assim como os métodos de get e set
 */

public class Group implements Serializable {

    public static final String TABLE_NAME  = "groups";
    public static final String ID_COLUMN   = "id";
    public static final String NAME_COLUMN = "name";

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return name;
    }

    public void setGroup(String group) {
        this.name = group;
    }

    @Override
    public String toString() {
        return name;
    }
}
