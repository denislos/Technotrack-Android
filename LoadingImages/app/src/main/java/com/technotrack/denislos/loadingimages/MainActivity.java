package com.technotrack.denislos.loadingimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity implements JsonLoaderListener, ImageLoaderListener, RequestImageListener
  {
    @Override
    public void onJSONLoaded(JSONArray jsonArray)
      {
        ImageCache.getInstance().setUrls(jsonArray);
        imagesAdapter.notifyDataSetChanged();
      }

    @Override
    public void onNoInternetConnection()
      {
        runOnUiThread(new Runnable()
          {
            @Override
            public void run()
              {
                Context context = getApplicationContext();
                CharSequence text = "Cannot load images. There is no internet connection";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
              }
          });
      }

    @Override
    public void onImageLoaded(final int position, final Bitmap bm)
      {
        runOnUiThread(new Runnable()
          {
            @Override
            public void run()
              {
                ImageCache.getInstance().setImage(position,bm);
                imagesAdapter.notifyItemChanged(position);
              }
          });
      }

    @Override
    public void onRequestImage(final int position, final String URL)
      {
        imageLoader.loadImage(position, URL);
      }

    @Override
    protected void onCreate(Bundle savedInstanceState)
      {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = ( RecyclerView) findViewById(R.id.recyclerView);
        imagesAdapter = new ImagesAdapter(this, this);
        mRecyclerView.setAdapter(imagesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        JSONLoader jsonLoader = new JSONLoader(this);
        jsonLoader.execute(JSONLoader.JSON_URL);

        imageLoader = new ImageLoader(this);
      }

    @Override
    protected void onStart()
      {
        super.onStart();
        imageLoader.start();
      }

    @Override
    protected void onStop()
      {
        super.onStop();
        imageLoader.stop();
      }

    private ImagesAdapter imagesAdapter;
    private ImageLoader imageLoader;
    private RecyclerView mRecyclerView;
  }
