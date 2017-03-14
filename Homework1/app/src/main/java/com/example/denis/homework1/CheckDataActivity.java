package com.example.denis.homework1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by denis on 3/12/17.
 */

public class CheckDataActivity extends Activity
  {
    @Override
    protected void onCreate(Bundle savedInstanceState)
      {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkdata);

        final TextView firstName = (TextView) findViewById(R.id.firstName);
        final TextView lastName = (TextView) findViewById(R.id.lastName);
        final TextView pickedDate = (TextView) findViewById(R.id.pickedDate);
        final Button editButton = ( Button) findViewById(R.id.EditButton);

        Intent intent = getIntent();
        firstName.setText(intent.getExtras().getString("FIRST_NAME"));
        lastName.setText(intent.getExtras().getString("LAST_NAME"));
        pickedDate.setText(intent.getExtras().getString("DATE"));

        final Activity activityThis = this;
        editButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v)
            {
              Bundle bundle = new Bundle();
              bundle.putString("FIRST_NAME", firstName.getText().toString() );
              bundle.putString("LAST_NAME", lastName.getText().toString());
              bundle.putString("DATE", pickedDate.getText().toString());

              Intent intent = new Intent(activityThis, MainActivity.class);
              intent.putExtras(bundle);
              intent.setAction("EDIT");
              startActivity(intent);

              finish();
            }
        });

      }

  }
