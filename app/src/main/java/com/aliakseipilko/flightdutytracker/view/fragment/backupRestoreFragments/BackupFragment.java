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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.BackupPresenter;
import com.aliakseipilko.flightdutytracker.view.fragment.backupRestoreFragments.base.BackupRestoreBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BackupFragment extends BackupRestoreBaseFragment {

    @BindView(R.id.backupDateTextView)
    TextView backupDate;

    ProgressDialog progressDialog;

    Unbinder unbinder;

    BackupPresenter presenter;

    public BackupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backup, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new BackupPresenter(this);

        backupDate.setText(presenter.getLatestBackupDate());

        return view;
    }

    @OnClick(R.id.backupButton)
    public void doBackup() {
        presenter.backupAllFlights();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Backing up...");
        progressDialog.show();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setBackupDate(String date) {
        backupDate.setText(date);
    }

    @Override
    public void onFABClicked() {
        //No FAB
    }
}
