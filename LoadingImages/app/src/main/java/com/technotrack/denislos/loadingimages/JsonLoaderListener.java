package com.technotrack.denislos.loadingimages;

import org.json.JSONArray;

/**
 * Created by denis on 4/17/17.
 */

public interface JsonLoaderListener
  {
    void onJSONLoaded(JSONArray jsonArray);
    void onNoInternetConnection();
  }
