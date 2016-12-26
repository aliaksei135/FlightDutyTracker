package com.aliakseipilko.flightdutytracker.realm.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class Flight extends RealmObject {

    @PrimaryKey
    private long id;

    //Dep and Arr
    private String departureIATACode;
    private String departureICAOCode;
    private String arrivalIATACode;
    private String arrivalICAOCode;

    //Times
    @Required private Date startDutyTime;
    private Date startFlightTime;
    @Required private Date endDutyTime;
    private Date endFlightTime;

    private String acType;
    private String flightNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartureIATACode() {
        return departureIATACode;
    }

    public void setDepartureIATACode(String departureIATACode) {
        this.departureIATACode = departureIATACode;
    }

    public String getDepartureICAOCode() {
        return departureICAOCode;
    }

    public void setDepartureICAOCode(String departureICAOCode) {
        this.departureICAOCode = departureICAOCode;
    }

    public String getArrivalIATACode() {
        return arrivalIATACode;
    }

    public void setArrivalIATACode(String arrivalIATACode) {
        this.arrivalIATACode = arrivalIATACode;
    }

    public String getArrivalICAOCode() {
        return arrivalICAOCode;
    }

    public void setArrivalICAOCode(String arrivalICAOCode) {
        this.arrivalICAOCode = arrivalICAOCode;
    }

    public Date getStartDutyTime() {
        return startDutyTime;
    }

    public void setStartDutyTime(Date startDutyTime) {
        this.startDutyTime = startDutyTime;
    }

    public Date getStartFlightTime() {
        return startFlightTime;
    }

    public void setStartFlightTime(Date startFlightTime) {
        this.startFlightTime = startFlightTime;
    }

    public Date getEndDutyTime() {
        return endDutyTime;
    }

    public void setEndDutyTime(Date endDutyTime) {
        this.endDutyTime = endDutyTime;
    }

    public Date getEndFlightTime() {
        return endFlightTime;
    }

    public void setEndFlightTime(Date endFlightTime) {
        this.endFlightTime = endFlightTime;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
