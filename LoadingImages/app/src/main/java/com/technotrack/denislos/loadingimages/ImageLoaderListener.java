package com.technotrack.denislos.loadingimages;

import android.graphics.Bitmap;

/**
 * Created by denis on 4/17/17.
 */

public interface ImageLoaderListener
  {
    void onImageLoaded(final int position, final Bitmap bm);
  }
