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
        View view = inflater.inflate(R.layout.fragment_create_flight, container, true);
        ButterKnife.setDebug(true);
        unbinder = ButterKnife.bind(this, view);

//        flightNumberEditText = (EditText) view.findViewById(R.id.flight_number_et);

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
                    "N/A", //TODO Allow A/C type to be added
                    flightNumberEditText.getText().toString().trim().toUpperCase());
        }
    }

    private boolean validateAllInputs() {
        if (TextUtils.isEmpty(flightNumberEditText.getText().toString())) {
            flightNumberEditText.setError("Cannot be empty.");
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
        }
        if (depText.trim().length() != 3 && depText.trim().length() != 4) {
            departureEditText.setError("Code invalid");
            arrivalEditText.setError("Code invalid");
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
