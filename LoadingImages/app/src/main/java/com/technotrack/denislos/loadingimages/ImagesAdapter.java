package com.technotrack.denislos.loadingimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by denis on 4/13/17.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder>
  {
    public ImagesAdapter(Context context, MainActivity listener)
      {
        mListener = new WeakReference<MainActivity>(listener);
        mContext = context;
      }

    public static class ViewHolder extends RecyclerView.ViewHolder
      {
        public ViewHolder(View itemView)
          {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
          }

        public ImageView imageView;
      }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
      {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View imageView = inflater.inflate(R.layout.item_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(imageView);

        return viewHolder;
      }

    @Override
    public void onBindViewHolder(ImagesAdapter.ViewHolder holder, int position)
      {;
        Bitmap bm = ImageCache.getInstance().get(position);
        if ( bm != null)
          {
            holder.imageView.setImageBitmap(bm);
          }
        else
          {
            MainActivity listener = mListener.get();
            if ( listener != null)
              {
                listener.onRequestImage(position, ImageCache.getInstance().getUrl(position));
              }
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
          }
      }



    @Override
    public int getItemCount()
      {
        return ImageCache.getInstance().size();
      }

    public Context getContext()
      {
        return mContext;
      }

    private WeakReference<MainActivity> mListener;
    private Context mContext;
  }
