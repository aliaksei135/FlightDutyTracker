/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.utils;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;

import java.lang.reflect.Type;

public class FlightSerialiser implements JsonSerializer<Flight> {

    @Override
    public JsonElement serialize(final Flight src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.add("id", context.serialize(src.getId()));

        jsonObject.addProperty("departureIATACode", src.getDepartureIATACode());
        jsonObject.addProperty("departureICAOCode", src.getDepartureICAOCode());

        jsonObject.addProperty("arrivalIATACode", src.getArrivalIATACode());
        jsonObject.addProperty("arrivalICAOCode", src.getArrivalICAOCode());

        jsonObject.add("startDutyTime", context.serialize(src.getStartDutyTime()));
        jsonObject.add("endDutyTime", context.serialize(src.getEndDutyTime()));

        jsonObject.add("startFlightTime", context.serialize(src.getStartFlightTime()));
        jsonObject.add("endFlightTime", context.serialize(src.getEndFlightTime()));

        jsonObject.addProperty("acType", src.getAcType());
        jsonObject.addProperty("flightNumber", src.getFlightNumber());

        return jsonObject;
    }
}
