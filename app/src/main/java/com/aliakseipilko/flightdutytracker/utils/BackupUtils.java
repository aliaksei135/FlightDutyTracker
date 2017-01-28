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


import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import io.realm.RealmResults;

public class BackupUtils {

    public static File serialiseFlightsRealmToFile(File destFile, Gson gson, RealmResults<Flight> data) throws IOException {
        destFile.setWritable(true);
        if (!destFile.canWrite()) {
            return null;
        }

        JsonWriter writer = gson.newJsonWriter(new FileWriter(destFile));
        FlightSerialiser flightSerialiser = new FlightSerialiser();

        writer.beginObject();
        gson.toJson(data, Flight.class, writer);
        writer.endObject();

        return destFile;
    }

}
