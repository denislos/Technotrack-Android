package com.example.denis.homework1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.denis.homework1.fragments.*;

public class MainActivity extends FragmentActivity
  {

    @Override
    protected void onCreate(Bundle savedInstanceState)
      {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final android.app.FragmentManager fm = getFragmentManager();

        if ( savedInstanceState == null)
          fm.beginTransaction().replace(R.id.MainFragment, new MainFragment()).commit();
      }

  }
