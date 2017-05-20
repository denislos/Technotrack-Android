package com.technotrack.denislos.loadingimagesservice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by denis on 4/22/17.
 */

public class DataManager
  {
    public static final String DEFAULT_INTENT_FILTER = "REQUESTED_IMAGE";

    public DataManager(MainActivity listener)
      {
        mListener = new WeakReference<MainActivity>(listener);
      }

    public void loadJSON()
      {
        JSONLoader jsonLoader = new JSONLoader(mListener);
        jsonLoader.execute(JSONLoader.JSON_URL);
      }


     public void requestImage(int position, final String URL)
      {
        Bitmap bm = null;

        MainActivity listener = mListener.get();
        if ( listener != null)
          {

            if ((bm = findInExternalStorage(position)) == null)
              {
                if ((bm = findInInternalStorage(position)) == null)
                  {
                    Intent intent = new Intent(listener, ImageLoader.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt(ImageLoader.POSITION, position);
                    bundle.putString(ImageLoader.URL, URL);
                    intent.putExtras(bundle);

                    listener.startService(intent);
                  }
                else
                  {
                    listener.onImageLoaded(position, bm);
                  }
              }
            else
              {
                listener.onImageLoaded(position, bm);
              }
          }
      }

    public Bitmap findInInternalStorage(int position)
      {
        MainActivity listener = mListener.get();
        if ( listener != null)
          {
            File root = listener.getCacheDir();
            File directory = new File(root.getAbsolutePath() + "/images");
            if ( directory.exists() )
              {
                File file = new File(directory, String.format("%d.png", position));
                if ( file.exists())
                  {
                    Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());

                    return bm;
                  }
              }
          }

        return null;
      }



    public Bitmap findInExternalStorage(int position)
      {
        MainActivity listener = mListener.get();
        if ( listener != null )
          if ( isExternalStoragePermitted(listener) && isExternalStorageReadable())
            {
              File root = listener.getExternalCacheDir();
              File directory = new File(root.getAbsolutePath() + "/images");
              if ( directory.exists())
                {
                  File file = new File(directory, String.format("%d.png", position));
                  if ( file.exists())
                      {
                        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());

                        return bm;
                      }
                }
            }

        return null;
      }



    public void putInStorage(int position, Bitmap bm)
      {
        MainActivity listener = mListener.get();
        if (listener != null)
          {
            File root;

            if (isExternalStoragePermitted(listener) && isExternalStorageWritable())
              root = listener.getExternalCacheDir();
            else
              root = listener.getCacheDir();


            File directory = new File(root.getAbsolutePath() + "/images");
            if (!directory.exists())
              directory.mkdir();

            File file = new File(directory, String.format("%d.png", position));
              {
                try
                  {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.close();
                  }
                catch (IOException e)
                  {
                    e.printStackTrace();
                  }

              }
          }

      }

    public boolean isExternalStoragePermitted(MainActivity listener)
      {
        return listener.isExternalStoragePermitted();
      }


    public boolean isExternalStorageWritable()
      {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
          return true;
        else
          return false;
      }

    public boolean isExternalStorageReadable()
      {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
          return true;
        else
          return false;
      }



    private WeakReference<MainActivity> mListener;
  }
