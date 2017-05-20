package com.technotrack.denislos.loadingimagesservice;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.net.URL;

/**
 * Created by denis on 4/25/17.
 */

public class ImageLoader extends IntentService
  {
    public static final String CONTENT_REQUEST = "CONTENT_REQUEST";
    public static final String URL = "URL";
    public static final String POSITION = "POSITION";


    public ImageLoader()
      {
        super("ImageLoader");
      }

    public void onHandleIntent(Intent intent)
      {
        Bundle bundle = intent.getExtras();
        String url = bundle.getString(URL);
        int position = bundle.getInt(POSITION);

        HttpRequest httpRequest = new HttpRequest(url);
        int responseCode = httpRequest.makeHttpImageRequest();

        if ( responseCode == HttpRequest.REQUEST_OK)
          {
            Bitmap bm = httpRequest.getBitmap();

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(ResponseReceiver.DEFAULT_RESPONSE_FILTER);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);

            Bundle broadcastBundle = new Bundle();
            broadcastBundle.putParcelable(ResponseReceiver.IMAGE, bm);
            broadcastBundle.putInt(ResponseReceiver.POSITION, position);
            broadcastIntent.putExtras(broadcastBundle);
            sendBroadcast(broadcastIntent);
          }
      }
  }
