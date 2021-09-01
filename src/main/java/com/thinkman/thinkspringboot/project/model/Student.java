package com.thinkman.thinkspringboot.project.model;

import java.util.Date;

public class Student {
    private int s_id;
    private String s_name;
    private Date s_birthday;
    private int c_id;

    public int getS_id() {
        return s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public Date getS_birthday() {
        return s_birthday;
    }

    public int getC_id() {
        return c_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public void setS_birthday(Date s_birthday) {
        this.s_birthday = s_birthday;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
}
