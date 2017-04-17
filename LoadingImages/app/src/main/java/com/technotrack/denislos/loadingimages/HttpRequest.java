package com.technotrack.denislos.loadingimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by denis on 4/14/17.
 */

public class HttpRequest
  {
    public static int REQUEST_OK = 0;
    public static int REQUEST_ERROR = 1;

    public HttpRequest(String url)
      {
        mUrl = url;
      }

    public String getContent()
      {
        return mContent;
      }

    public Bitmap getBitmap()
      {
        return mBitmap;
      }

    public int makeHttpImageRequest()
      {
        try
          {
            URL url = new URL(mUrl);
            HttpURLConnection connection;
            try
              {
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setInstanceFollowRedirects(true);

                int response_code = connection.getResponseCode();

                if ( response_code == HttpURLConnection.HTTP_OK)
                  {
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    mBitmap = BitmapFactory.decodeStream(inputStream);

                    inputStream.close();

                    return REQUEST_OK;
                  }
                else
                  return REQUEST_ERROR;
              }
            catch(IOException ex)
              {

              }
          }
        catch(MalformedURLException ex)
          {

          }

        return REQUEST_ERROR;
      }

    public int makeHttpRequest()
      {
        try
          {
            URL url = new URL(mUrl);
            HttpURLConnection connection;
            try
              {
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setInstanceFollowRedirects(true);

                int response_code = connection.getResponseCode();

                if ( response_code == HttpURLConnection.HTTP_OK)
                  {
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());

                    mContent = StringUtils.readInputStream(inputStream);

                    inputStream.close();

                    return REQUEST_OK;
                  }
                else
                  return REQUEST_ERROR;
              }
            catch(IOException ex)
              {

              }
          }
        catch(MalformedURLException ex)
          {

          }

        return REQUEST_ERROR;
      }

    final String mUrl;
    private String mContent;
    private Bitmap mBitmap;
  }
