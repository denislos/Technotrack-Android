package com.technotrack.denislos.loadingimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by denis on 4/14/17.
 */

public class StringUtils
  {
    public static Bitmap convertStringToBitmap(String string)
      {
        try
          {
            byte[] bytes = Base64.decode(string, Base64.URL_SAFE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            return bitmap;
          }
         catch(Exception ex)
           {
             return null;
           }
      }

    public static String readInputStream(InputStream is) throws IOException
      {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int read;
        byte[] data = new byte[3000];

        while ((read = is.read(data, 0, data.length)) != -1)
          {
            outputStream.write(data, 0, read);
          }

        outputStream.flush();
        return outputStream.toString("utf-8");
    }
  }
