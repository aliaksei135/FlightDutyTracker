/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.CreateFlightPresenter;
import com.aliakseipilko.flightdutytracker.view.dialog.DateTimePickerDialog;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreateFlightFragment extends BaseFragment {

    public static CreateFlightFragmentResultListener callback;

    @BindView(R.id.departure_et)
    EditText departureEditText;
    @BindView(R.id.arrival_et)
    EditText arrivalEditText;
    @BindView(R.id.flight_number_et)
    EditText flightNumberEditText;
    @BindView(R.id.ac_type_et)
    EditText acTypeEditText;
    @BindView(R.id.duty_from_date_tv)
    TextView dutyFromDateTextView;
    @BindView(R.id.duty_to_date_tv)
    TextView dutyToDateTextView;
    @BindView(R.id.flight_from_date_tv)
    TextView flightFromDateTextView;
    @BindView(R.id.flight_to_date_tv)
    TextView flightToDateTextView;

    Date startDutyDate, endDutyDate, startFlightDate, endFlightDate;

    private CreateFlightPresenter presenter;
    private Unbinder unbinder;


    public CreateFlightFragment() {

    }

    public static CreateFlightFragment newInstance(CreateFlightFragmentResultListener callback) {
        CreateFlightFragment.callback = callback;

        return new CreateFlightFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_create_flight, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new CreateFlightPresenter(this);
        presenter.subscribeAllCallbacks();

        setDateTimeListeners();

        return view;
    }

    private void setDateTimeListeners() {
        dutyFromDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDateTime("Duty Start", TimeType.DUTY_START, dutyFromDateTextView);
            }
        });
        dutyToDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDateTime("Duty End", TimeType.DUTY_END, dutyToDateTextView);
            }
        });
        flightFromDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDateTime("Flight Start", TimeType.FLIGHT_START, flightFromDateTextView);
            }
        });
        flightToDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDateTime("Flight End", TimeType.FLIGHT_END, flightToDateTextView);
            }
        });
    }

    @Override
    public void onFABClicked() {
        //No FAB to click
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        callback = null;
    }

    public void saveFlight() {
        if (validateAllInputs()) {
            presenter.addFlight(departureEditText.getText().toString().trim().toUpperCase(),
                    arrivalEditText.getText().toString().trim().toUpperCase(),
                    startDutyDate,
                    startFlightDate,
                    endFlightDate,
                    endDutyDate,
                    acTypeEditText.getText().toString().trim().toUpperCase(),
                    flightNumberEditText.getText().toString().trim().toUpperCase());
        }
    }

    private boolean validateAllInputs() {
        if (TextUtils.isEmpty(flightNumberEditText.getText().toString())) {
            flightNumberEditText.setError("Cannot be empty.");
            return false;
        }
        if (TextUtils.isEmpty(acTypeEditText.getText().toString())) {
            acTypeEditText.setError("Cannot be empty.");
            return false;
        }
        String depText = departureEditText.getText().toString();
        if (TextUtils.isEmpty(depText)) {
            departureEditText.setError("Cannot be empty.");
            return false;
        }
        String arrText = arrivalEditText.getText().toString();
        if (TextUtils.isEmpty(arrText)) {
            arrivalEditText.setError("Cannot be empty.");
            return false;
        }
        if (depText.trim().length() != arrText.trim().length()) {
            departureEditText.setError("Codes must both be either IATA or ICAO.");
            arrivalEditText.setError("Codes must both be either IATA or ICAO.");
            return false;
        }
        if (depText.trim().length() != 3 && depText.trim().length() != 4) {
            departureEditText.setError("Code invalid");
            arrivalEditText.setError("Code invalid");
            return false;
        }
        if (TextUtils.isEmpty(dutyFromDateTextView.getText().toString())) {
            dutyFromDateTextView.setError("Cannot be empty.");
            return false;
        }
        if (TextUtils.isEmpty(dutyToDateTextView.getText().toString())) {
            dutyToDateTextView.setError("Cannot be empty.");
            return false;
        }
        if (TextUtils.isEmpty(flightFromDateTextView.getText().toString()) ^ TextUtils.isEmpty(flightToDateTextView.getText().toString())) {
            flightToDateTextView.setError("Fill both or neither Flight times");
            flightFromDateTextView.setError("Fill both or neither Flight times");
            return false;
        }
        return true;
    }

    public void setResultListener(CreateFlightFragmentResultListener callback) {
        CreateFlightFragment.callback = callback;
    }

    void pickDateTime(String title, final TimeType type, final TextView displayField) {
        final SwitchDateTimeDialogFragment dialog = DateTimePickerDialog.newInstance(title, "OK", "Cancel");
        dialog.setCancelable(false);

        Date now = new Date();
        dialog.setDefaultYear(now.getYear() + 1900);
        dialog.setDefaultMonth(now.getMonth());
        dialog.setDefaultDay(now.getDate());
        dialog.setDefaultHourOfDay(now.getHours());
        dialog.setDefaultMinute(now.getMinutes());
        dialog.set24HoursMode(true);

        dialog.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                switch (type) {
                    case DUTY_START:
                        startDutyDate = date;
                        break;
                    case DUTY_END:
                        endDutyDate = date;
                        break;
                    case FLIGHT_START:
                        startFlightDate = date;
                        break;
                    case FLIGHT_END:
                        endFlightDate = date;
                        break;
                }
                displayField.setText(new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                dialog.dismiss();
            }
        });

        dialog.show(getFragmentManager(), "DateTimePickerDialog");
    }

    enum TimeType {
        DUTY_START,
        DUTY_END,
        FLIGHT_START,
        FLIGHT_END
    }

    public interface CreateFlightFragmentResultListener {
        void onAddFlightComplete();

        void onAddFlightFailed(String message);
    }
}
