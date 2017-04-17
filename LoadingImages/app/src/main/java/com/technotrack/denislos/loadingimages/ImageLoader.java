package com.technotrack.denislos.loadingimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by denis on 4/16/17.
 */

public class ImageLoader
  {

    public ImageLoader(MainActivity listener)
      {
        mLoader = null;
        mListener = new WeakReference<MainActivity>(listener);
      }

    public void loadImage(final int position, final String URL)
      {
        final MainActivity listener = mListener.get();
        if ( listener != null)
          {
            Future<Bitmap> future = mLoader.submit(new Callable<Bitmap>()
              {
                public Bitmap call()
                  {
                    HttpRequest httpRequest = new HttpRequest(URL);
                    int status = httpRequest.makeHttpImageRequest();

                    Bitmap bm = null;
                    if ( status == HttpRequest.REQUEST_OK)
                      {
                        bm = httpRequest.getBitmap();
                        listener.onImageLoaded(position, bm);
                      }
                    else
                      listener.onNoInternetConnection();

                    return bm;
                  }
              });
          }
      }

    public void start()
      {
        int availableProcessors = Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
        mLoader = Executors.newFixedThreadPool(availableProcessors);
      }

    public void stop()
      {
        mLoader.shutdown();
      }

    private WeakReference<MainActivity> mListener;
    private ExecutorService mLoader;
  }
