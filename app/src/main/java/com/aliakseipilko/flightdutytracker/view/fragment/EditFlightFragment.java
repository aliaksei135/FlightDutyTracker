package com.aliakseipilko.flightdutytracker.view.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.dagger.components.DaggerStorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.modules.PrefsModule;
import com.aliakseipilko.flightdutytracker.presenter.impl.EditFlightPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.view.activity.FlightDetailsActivity;
import com.aliakseipilko.flightdutytracker.view.dialog.DateTimePickerDialog;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditFlightFragment extends BaseFragment {

    @BindView(R.id.edit_departure_et)
    EditText departureEditText;
    @BindView(R.id.edit_arrival_et)
    EditText arrivalEditText;
    @BindView(R.id.edit_flight_number_et)
    EditText flightNumberEditText;
    @BindView(R.id.edit_ac_type_et)
    EditText acTypeEditText;
    @BindView(R.id.edit_duty_from_date_tv)
    TextView dutyFromDateTextView;
    @BindView(R.id.edit_duty_to_date_tv)
    TextView dutyToDateTextView;
    @BindView(R.id.edit_flight_from_date_tv)
    TextView flightFromDateTextView;
    @BindView(R.id.edit_flight_to_date_tv)
    TextView flightToDateTextView;

    Date startDutyDate, endDutyDate, startFlightDate, endFlightDate;

    Unbinder unbinder;

    EditFlightPresenter presenter;

    Flight editFlight;
    long flightId;

    @Inject
    SharedPreferences prefs;

    public EditFlightFragment() {
        // Required empty public constructor
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_flight, container, false);

        unbinder = ButterKnife.bind(this, view);
        DaggerStorageComponent.builder().prefsModule(new PrefsModule(getContext())).build().inject(this);

        presenter = new EditFlightPresenter(this);
        presenter.subscribeAllCallbacks();

        presenter.getSingleFlightById(flightId);

        setCurrentValues();

        return view;
    }

    public void setEditFlight(Flight editFlight) {
        this.editFlight = editFlight;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showError(String message) {
        super.showError(message);
        Intent intent = new Intent(getActivity(), FlightDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("flightId", flightId);
        startActivity(intent);
    }

    @Override
    public void showSuccess(String message) {
        super.showSuccess(message);
        Intent intent = new Intent(getActivity(), FlightDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("flightId", flightId);
        startActivity(intent);
    }

    public void saveFlight() {
        if (validateAllInputs()) {
            presenter.editFlight(editFlight.getId(),
                    departureEditText.getText().toString().trim().toUpperCase(),
                    arrivalEditText.getText().toString().trim().toUpperCase(),
                    startDutyDate,
                    startFlightDate,
                    endFlightDate,
                    endDutyDate,
                    acTypeEditText.getText().toString().trim().toUpperCase(),
                    flightNumberEditText.getText().toString().trim().toUpperCase());
        }
    }

    private void setCurrentValues() {
        flightNumberEditText.setText(editFlight.getFlightNumber());
        if (prefs.getString("airportCodeType", "IATA").equals("IATA")) {
            departureEditText.setText(editFlight.getDepartureIATACode());
            arrivalEditText.setText(editFlight.getArrivalIATACode());
        } else {
            departureEditText.setText(editFlight.getDepartureICAOCode());
            arrivalEditText.setText(editFlight.getArrivalICAOCode());
        }
        acTypeEditText.setText(editFlight.getAcType());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());

        startDutyDate = editFlight.getStartDutyTime();
        dutyFromDateTextView.setText(sdf.format(editFlight.getStartDutyTime()));
        endDutyDate = editFlight.getEndDutyTime();
        dutyToDateTextView.setText(sdf.format(editFlight.getEndDutyTime()));
        startFlightDate = editFlight.getStartFlightTime();
        flightFromDateTextView.setText(sdf.format(editFlight.getStartFlightTime()));
        endFlightDate = editFlight.getEndFlightTime();
        flightToDateTextView.setText(sdf.format(editFlight.getEndFlightTime()));
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

    void pickDateTime(String title, final CreateFlightFragment.TimeType type, final TextView displayField) {
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

    @Override
    public void onFABClicked() {

    }

    enum TimeType {
        DUTY_START,
        DUTY_END,
        FLIGHT_START,
        FLIGHT_END
    }

}
