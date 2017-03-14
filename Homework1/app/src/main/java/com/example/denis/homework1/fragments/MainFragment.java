package com.example.denis.homework1.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.denis.homework1.CheckDataActivity;
import com.example.denis.homework1.R;

import org.w3c.dom.Text;

/**
 * Created by denis on 3/12/17.
 */

public class MainFragment extends Fragment
  {

    TextView pickDate;

    @Override
    public void onCreate(Bundle savedInstanceState)
      {
        super.onCreate(savedInstanceState);
      }

    @Override
    public void onSaveInstanceState(Bundle outState)
      {
        super.onSaveInstanceState(outState);
      }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
      {
        final LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.fragment_main, container, false);

        final EditText editFirstName  = (EditText)linearLayout.findViewById(R.id.editFirstName);
        final EditText editLastName = (EditText)linearLayout.findViewById(R.id.editLastName);
        pickDate = (TextView)linearLayout.findViewById(R.id.pickDate);
        final Button saveButton = (Button) linearLayout.findViewById(R.id.SaveButton);

        final String DEFAULT_FIRST_NAME = editFirstName.getText().toString();
        final String DEFAULT_LAST_NAME = editLastName.getText().toString();
        final String DEFAULT_DATE_TEXT = pickDate.getText().toString();


        Intent intent = getActivity().getIntent();
        if ( intent.getAction() == "EDIT")
          {
            editFirstName.setText(intent.getExtras().getString("FIRST_NAME"));
            editLastName.setText(intent.getExtras().getString("LAST_NAME"));
            pickDate.setText(intent.getExtras().getString("DATE"));
          }
        final Fragment fragmentThis = this;


        pickDate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            DialogFragment DateDialog = new PickDateDialog();
            DateDialog.setTargetFragment(fragmentThis,0);
            DateDialog.show(getActivity().getFragmentManager(),"Pick Date");
          }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v)
            {
              if ( !editFirstName.getText().toString().isEmpty() && editFirstName.getText().toString().compareTo(DEFAULT_FIRST_NAME) != 0
                && !editLastName.getText().toString().isEmpty() &&  editLastName.getText().toString().compareTo(DEFAULT_LAST_NAME) != 0
                && !pickDate.getText().toString().isEmpty() && pickDate.getText().toString().compareTo(DEFAULT_DATE_TEXT) != 0 )
                {
                  Bundle bundle = new Bundle();
                  bundle.putString("FIRST_NAME", editFirstName.getText().toString());
                  bundle.putString("LAST_NAME", editLastName.getText().toString());
                  bundle.putString("DATE", pickDate.getText().toString());

                  Intent intent = new Intent(getActivity(),CheckDataActivity.class );
                  intent.putExtras(bundle);
                  startActivity(intent);
                  getActivity().finish();
                }
            }
        });

        return linearLayout;
      }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data)
       {
         super.onActivityResult(requestCode, resultCode, data);

         String s = data.getExtras().getString("DATE");
         pickDate.setText(s);
       }

  }
