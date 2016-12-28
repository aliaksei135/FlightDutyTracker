package com.aliakseipilko.flightdutytracker.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import android.content.Context;

import com.aliakseipilko.flightdutytracker.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.inject.Inject;

public class AirportCodeConverter {
    @Inject
    Gson gson;
    @Inject
    Context ctx;

    List<Airport> airports;

    @Inject
    public AirportCodeConverter() {

    }

    private void generateAirports() {
        try {
            airports = gson.fromJson(new JsonReader(new FileReader(new File(String.valueOf(ctx.getResources().openRawResource(R.raw.airports))))), Airport.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String convertIATAtoICAO(String IATACode) {
        if (airports == null) {
            generateAirports();
        }
        for (Airport airport : airports) {
            if (airport.getIATACode().equalsIgnoreCase(IATACode)) {
                return airport.getICAOCode();
            }
        }
        return null;
    }

    public String convertICAOtoIATA(String ICAOCode) {
        if (airports == null) {
            generateAirports();
        }
        for (Airport airport : airports) {
            if (airport.getICAOCode().equalsIgnoreCase(ICAOCode)) {
                return airport.getIATACode();
            }
        }
        return null;
    }

    private class Airport {
        long UID;
        String name;
        String city;
        String country;
        String IATACode;
        String ICAOCode;
        double latitude;
        double longitude;
        int altitude;
        float timezone;
        char DST;
        String TZDB;

        public long getUID() {
            return UID;
        }

        public void setUID(long UID) {
            this.UID = UID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getIATACode() {
            return IATACode;
        }

        public void setIATACode(String IATACode) {
            this.IATACode = IATACode;
        }

        public String getICAOCode() {
            return ICAOCode;
        }

        public void setICAOCode(String ICAOCode) {
            this.ICAOCode = ICAOCode;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public int getAltitude() {
            return altitude;
        }

        public void setAltitude(int altitude) {
            this.altitude = altitude;
        }

        public float getTimezone() {
            return timezone;
        }

        public void setTimezone(float timezone) {
            this.timezone = timezone;
        }

        public char getDST() {
            return DST;
        }

        public void setDST(char DST) {
            this.DST = DST;
        }

        public String getTZDB() {
            return TZDB;
        }

        public void setTZDB(String TZDB) {
            this.TZDB = TZDB;
        }
    }
}
