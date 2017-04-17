package com.technotrack.denislos.loadingimages;

import android.graphics.Bitmap;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by denis on 4/15/17.
 */

public class ImageCache
  {
    private ImageCache()
      {
        mUrls = new ArrayList<String>();
        mImages = new SparseArray<>();
      }

    public synchronized static ImageCache getInstance()
      {
        if ( imageCache == null)
          imageCache = new ImageCache();

        return imageCache;
      }

    public synchronized void setUrls(JSONArray jsonArray)
      {
        int length = jsonArray.length();

        try
          {
            for (int cnt = 0; cnt < length; cnt++)
              mUrls.add(cnt, jsonArray.getString(cnt));
          }
        catch (JSONException ex)
          {
          }
      }

    public int size()
      {
        return mUrls.size();
      }

    public synchronized  void setImage(int position, Bitmap bm)
      {
        mImages.put(position, bm);
      }

    public synchronized String getUrl(int position)
      {
        return mUrls.get(position);
      }

    public synchronized Bitmap get(int position)
      {
        return mImages.get(position);
      }


    private static volatile ImageCache imageCache;
    private ArrayList<String> mUrls;
    private SparseArray<Bitmap> mImages;
  }
