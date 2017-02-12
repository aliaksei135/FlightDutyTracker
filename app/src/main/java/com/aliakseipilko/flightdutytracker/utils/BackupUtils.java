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


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.view.fragment.backupRestoreFragments.base.BackupRestoreBaseFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class BackupUtils {

    public static File serialiseFlightsRealmToFile(File destFile, RealmResults<Flight> data) throws IOException {
        destFile.setWritable(true);
        if (!destFile.canWrite()) {
            return null;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder
                .registerTypeAdapter(Flight.class, new FlightSerialiser())
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat(DateFormat.FULL, DateFormat.FULL)
                .create();

        JsonWriter writer = gson.newJsonWriter(new FileWriter(destFile));

        for (Flight f : data) {
            writer.beginObject();
            gson.toJson(f, Flight.class, writer);
            writer.endObject();
        }

        return destFile;
    }

    public static void deserialiseFlightsFileToRealm(Realm realm, final File srcFile, final BackupRestoreBaseFragment callingView) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    InputStream is = new FileInputStream(srcFile);
                    realm.createAllFromJson(Flight.class, is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callingView.showSuccess("Restore completed successfully!");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callingView.showError("That didn't work. Try again");
            }
        });
    }

}
