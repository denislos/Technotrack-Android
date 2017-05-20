package com.technotrack.denislos.loadingimagesservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.lang.ref.WeakReference;

/**
 * Created by denis on 4/25/17.
 */

public class ResponseReceiver extends BroadcastReceiver
  {
    public static final String DEFAULT_RESPONSE_FILTER = "IMAGE_LOADED";

    public static final String POSITION = "POSITION";
    public static final String IMAGE = "BITMAP";

    public ResponseReceiver(MainActivity listener)
      {
        super();

        mListener = new WeakReference<MainActivity>(listener);
      }

    @Override
    public void onReceive(Context context, Intent intent)
      {
        Bundle bundle =  intent.getExtras();
        Bitmap bm = bundle.getParcelable(IMAGE);
        int position = bundle.getInt(POSITION);

        MainActivity listener = mListener.get();
        if ( listener != null )
          {
            listener.putInStorage(position, bm);
            listener.onImageLoaded(position, bm);
          }
      }

    private WeakReference<MainActivity> mListener;

  }
