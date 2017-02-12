/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.view.fragment.backupRestoreFragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.BackupPresenter;
import com.aliakseipilko.flightdutytracker.view.fragment.backupRestoreFragments.base.BackupRestoreBaseFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ir.sohreco.androidfilechooser.ExternalStorageNotAvailableException;
import ir.sohreco.androidfilechooser.FileChooserDialog;

public class RestoreFragment extends BackupRestoreBaseFragment implements FileChooserDialog.ChooserListener {

    private static final int PERMISSION_REQUEST_CODE = 5;
    @BindView(R.id.restoreDateTextView)
    TextView restoreDate;
    ProgressDialog progressDialog;

    Unbinder unbinder;

    BackupPresenter presenter;

    public RestoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restore, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new BackupPresenter(this);
        presenter.subscribeAllCallbacks();

        restoreDate.setText("Latest Backup: " + presenter.getLatestBackupDate());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.restoreButton)
    public void doRestore() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            FileChooserDialog.Builder builder = new FileChooserDialog.Builder(FileChooserDialog.ChooserType.FILE_CHOOSER, this)
                    .setTitle("Select restore file:")
                    .setInitialDirectory(Environment.getExternalStorageDirectory());

            try {
                builder.build().show(getChildFragmentManager(), null);
            } catch (ExternalStorageNotAvailableException e) {
                e.printStackTrace();
                showError("Couldn't access storage. Try again");
            }
        }
    }

    @Override
    public void setBackupDate(String date) {
        restoreDate.setText("Latest Backup: " + date);
    }

    @Override
    public void showSuccess(String message) {
        super.showSuccess(message);
        progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        super.showError(message);
        progressDialog.dismiss();
    }

    @Override
    public void onFABClicked() {
        //No FAB
    }

    @Override
    public void onSelect(String path) {
        presenter.restoreFlights(new File(path));
    }
}
