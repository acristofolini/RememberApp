package com.example.fabiano.rememberapp.data;

import com.example.fabiano.rememberapp.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ana on 5/28/2017.
 * Este registro define os campos e os tipos dos campos da tabela HISTORY
 * assim como os métodos de get e set
 */

public class History implements Serializable {

    public static final String TABLE_NAME      = "history";
    public static final String ID_COLUMN       = "id";
    public static final String ID_GROUP_COLUMN = "id_group";
    public static final String TITLE_COLUMN    = "title";
    public static final String DATE_COLUMN     = "date";
    public static final String DETAIL_COLUMN   = "detail";


    private Long id;
    private Long id_group;
    private String title;
    private Date dateHistory;
    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdGroup() {
        return id_group;
    }

    public void setIdGroup(Long id_group) {
        this.id_group = id_group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateHistory() { return dateHistory;}

    public void setDateHistory(Date date_history) {this.dateHistory = date_history;}

    public String getDetail() {
        return detail;
    }

    public void setDetail(String title) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return DateUtil.formatDate(dateHistory) + " - " + title;
    }
}
