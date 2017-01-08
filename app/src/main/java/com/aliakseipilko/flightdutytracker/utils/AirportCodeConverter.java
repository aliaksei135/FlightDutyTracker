package com.aliakseipilko.flightdutytracker.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import android.content.Context;

import com.aliakseipilko.flightdutytracker.R;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AirportCodeConverter {
    Gson gson;
    Context ctx;

    List<Airport> airports = new ArrayList<>();

    @Inject
    public AirportCodeConverter(Context appContext, Gson gson) {
        this.gson = gson;
        ctx = appContext;
        generateAirports();
    }

    private void generateAirports() {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(ctx.getResources().openRawResource(R.raw.airports)));
        try {
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                Airport airport = gson.fromJson(jsonReader, Airport.class);
                airports.add(airport);
            }
            jsonReader.endArray();
            jsonReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String convertIATAtoICAO(String IATACode) {
        if (airports == null) {
            generateAirports();
        }
        for (Airport airport : airports) {
            if (airport.getIATA().equalsIgnoreCase(IATACode)) {
                return airport.getICAO();
            }
        }
        return null;
    }

    public String convertICAOtoIATA(String ICAOCode) {
        if (airports == null) {
            generateAirports();
        }
        for (Airport airport : airports) {
            if (airport.getICAO().equalsIgnoreCase(ICAOCode)) {
                return airport.getIATA();
            }
        }
        return null;
    }

    private class Airport {
        long UID;
        String name;
        String city;
        String country;
        String IATA;
        String ICAO;
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

        public String getIATA() {
            return IATA;
        }

        public void setIATA(String IATACode) {
            this.IATA = IATACode;
        }

        public String getICAO() {
            return ICAO;
        }

        public void setICAO(String ICAOCode) {
            this.ICAO = ICAOCode;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = Double.parseDouble(latitude);
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = Double.parseDouble(longitude);
        }

        public int getAltitude() {
            return altitude;
        }

        public void setAltitude(String altitude) {
            this.altitude = Integer.parseInt(altitude);
        }

        public float getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = Float.valueOf(timezone);
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
