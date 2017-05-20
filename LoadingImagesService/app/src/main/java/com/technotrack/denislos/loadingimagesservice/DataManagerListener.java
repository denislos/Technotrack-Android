package com.technotrack.denislos.loadingimagesservice;

import android.graphics.Bitmap;

import org.json.JSONArray;

/**
 * Created by denis on 4/22/17.
 */

public interface DataManagerListener
  {
    void onJSONLoaded(JSONArray jsonArray);
    void onNoInternetConnection();
    void onImageLoaded(int position, Bitmap bm);
  }
