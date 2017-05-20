package com.technotrack.denislos.loadingimagesservice;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by denis on 4/22/17.
 */

public class ImageCache
  {
    private ImageCache()
      {
        mUrls = new ArrayList<String>();
        cache = new LruCache<Integer, Bitmap>(MAX_CACHE_SIZE);
      }

    public static synchronized  ImageCache getInstance()
      {
        if ( mImageCache == null)
          {
            mImageCache = new ImageCache();
          }

        return mImageCache;
      }

    public synchronized void setUrls(JSONArray jsonArray)
      {
        int length = jsonArray.length();

        try
          {
            for (int cnt = 0; cnt < length; cnt++)
              mUrls.add(cnt, jsonArray.getString(cnt));
          }
        catch(JSONException ex)
          {
          }
      }

    public synchronized String getUrl(int position)
      {
        return mUrls.get(position);
      }

    public synchronized void setImage(int position, Bitmap bm)
      {
        cache.put(position, bm);
      }

    public synchronized Bitmap get(int position)
      {
        return cache.get(position);
      }


    public synchronized  int size()
      {
        return mUrls.size();
      }


    private static volatile ImageCache mImageCache;
    private ArrayList<String> mUrls;
    private LruCache<Integer, Bitmap> cache;

    private static final int MAX_CACHE_SIZE = 8192;
  }
