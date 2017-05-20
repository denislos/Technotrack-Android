    package com.technotrack.denislos.loadingimagesservice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.technotrack.denislos.loadingimagesservice.R.id.recyclerView;

public class MainActivity extends AppCompatActivity implements DataManagerListener
  {
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 5234;

    @Override
    synchronized public void onImageLoaded(final int position, Bitmap bm)
      {
        ImageCache.getInstance().setImage(position, bm);

        runOnUiThread(new Runnable()
          {
            @Override
            public void run()
              {
                imagesAdapter.onItemChanged(position);
              }
          });

      }

    @Override
    public void onJSONLoaded(final JSONArray jsonArray)
      {
        if ( jsonArray != null)
          {
            ImageCache.getInstance().setUrls(jsonArray);

            runOnUiThread(new Runnable()
              {
                @Override
                public void run()
                  {
                    if (jsonArray != null)
                      {
                        imagesAdapter.notifyDataSetChanged();
                      }
                  }
              });
          }
      }

    @Override
    public void onNoInternetConnection()
      {
        runOnUiThread(new Runnable()
          {
            @Override
            public void run()
              {
                Context context = getApplicationContext();
                CharSequence text = "Cannot load images. There is no internet connection";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

              }
          });
      }


    public void putInStorage(int position, Bitmap bm)
      {
        dataManager.putInStorage(position, bm);
      }

    public void showMessage(final String message)
      {
        runOnUiThread(new Runnable()
          {
            @Override
            public void run()
              {
                Context context = getApplicationContext();
                CharSequence text = message.subSequence(0, message.length() - 1);
                int duration =  Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
              }
          });
      }


    @Override
    protected void onCreate(Bundle savedInstanceState)
      {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = new DataManager(this);

        getPermission();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        imagesAdapter = new ImagesAdapter(dataManager);
        recyclerView.setAdapter(imagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        IntentFilter filter = new IntentFilter(ResponseReceiver.DEFAULT_RESPONSE_FILTER);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver(this);
        registerReceiver(receiver, filter);

        dataManager.loadJSON();
      }



    @Override
    protected void onStart()
      {
        super.onStart();

      }

    @Override
    protected void onPause()
      {
        super.onPause();
      }


    @Override
    protected void onStop()
      {
        super.onStop();
        unregisterReceiver(receiver);
      }


    public void getPermission()
      {

        if (!isExternalStoragePermitted())
          {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) )
              {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
              }
            else
              {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
              }
          }
        else
          {
          }
      }

    public boolean isExternalStoragePermitted()
      {
        return ( ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
      }


    private RecyclerView recyclerView;
    private ImagesAdapter imagesAdapter;
    private DataManager dataManager;
    private ResponseReceiver receiver;
  }
