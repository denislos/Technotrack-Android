package com.example.denis.homework1.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.DateFormatSymbols;
import android.icu.text.SimpleDateFormat;
import android.support.v7.text.*;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by denis on 3/12/17.
 */

public class PickDateDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {



  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    super.onCreateDialog(savedInstanceState);
    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);



    return new DatePickerDialog(getActivity(), this, year, month, day);
  }

  public void onDateSet(DatePicker view, int year, int month, int day)
  {

    Bundle bundle = new Bundle();
    bundle.putString("DATE",String.format(Locale.getDefault(), "%1$02d/%2$02d/%3$04d", view.getDayOfMonth(), view.getMonth(), view.getYear()) );

    Intent intent = new Intent().putExtras(bundle);

    getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intent);

    dismiss();
  }
}
