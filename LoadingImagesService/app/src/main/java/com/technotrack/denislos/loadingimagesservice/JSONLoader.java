package com.technotrack.denislos.loadingimagesservice;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.lang.ref.WeakReference;

/**
 * Created by denis on 4/22/17.
 */

public class JSONLoader extends AsyncTask<String, Integer, JSONArray>
  {
    public JSONLoader(WeakReference<MainActivity> listener)
      {
        mListener = listener;
      }

    @Override
    public JSONArray doInBackground(String... params)
      {
        if ( params != null && params.length > 0 )
          {
            HttpRequest httpRequest = new HttpRequest(params[0]);
            int status = httpRequest.makeHttpRequest();

            JSONArray jsonArray = null;
            if (status == HttpRequest.REQUEST_OK)
              {
                JSONTokener jtk = new JSONTokener(httpRequest.getContent());
                try
                  {
                    jsonArray = (JSONArray) jtk.nextValue();
                  } catch (JSONException ex)
                  {

                  }
              }

            return jsonArray;
          }
        else
          return null;
      }

    @Override
    public void onPostExecute(JSONArray jsonArray)
      {
        if(!isCancelled())
          {
            MainActivity listener = mListener.get();
            if ( listener != null)
              {
                if ( jsonArray != null)
                  {
                    listener.onJSONLoaded(jsonArray);
                  }
                else
                  {
                    listener.onNoInternetConnection();
                  }
              }
          }
      }

    private WeakReference<MainActivity> mListener;
    public static final String JSON_URL = "http://188.166.49.215/tech/imglist.json";
  }
