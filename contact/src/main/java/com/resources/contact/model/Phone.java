package com.resources.contact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long phoneId;
    @Column(name = "number")
    private String number;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PHONE_TYPES type;

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PHONE_TYPES getType() {
        return type;
    }

    public void setType(PHONE_TYPES type) {
        this.type = type;
    }

    public enum PHONE_TYPES {
        home,
        work,
        mobile
    }
}
