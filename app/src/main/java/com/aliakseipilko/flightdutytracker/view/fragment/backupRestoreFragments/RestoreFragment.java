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


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.BackupPresenter;
import com.aliakseipilko.flightdutytracker.view.fragment.backupRestoreFragments.base.BackupRestoreBaseFragment;
import com.github.developerpaul123.filepickerlibrary.FilePicker;
import com.github.developerpaul123.filepickerlibrary.FilePickerBuilder;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.Scope;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class RestoreFragment extends BackupRestoreBaseFragment {

    private static final int REQUEST_FILE = 5401;

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
        new FilePickerBuilder(getContext())
                .withRequest(Request.FILE)
                .withScope(Scope.ALL)
                .useMaterialActivity(true)
                .launch(REQUEST_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            showError("Couldn't restore flights. Try again");
        }

        if (requestCode == REQUEST_FILE) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Restoring...");
            progressDialog.show();
            File srcFile = new File(data.getStringExtra(FilePicker.FILE_EXTRA_DATA_PATH));
            presenter.restoreFlights(srcFile);
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
}
