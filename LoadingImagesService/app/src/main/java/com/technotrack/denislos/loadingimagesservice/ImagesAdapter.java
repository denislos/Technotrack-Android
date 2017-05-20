package com.technotrack.denislos.loadingimagesservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by denis on 4/22/17.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder>
  {
    public static class ViewHolder extends RecyclerView.ViewHolder
      {
        public ViewHolder(View itemView)
          {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.imageView);
          }

        public ImageView imageView;
      }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
      {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View imageView = inflater.inflate(R.layout.item_image, parent, false);

        ImagesAdapter.ViewHolder viewHolder = new ViewHolder(imageView);

        return viewHolder;
      }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
      {
        onBind = true;

        Bitmap bm = ImageCache.getInstance().get(position);
        if ( bm != null)
          {
            holder.imageView.setImageBitmap(bm);
          }
        else
          {
            dataManager.requestImage(position, ImageCache.getInstance().getUrl(position));
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
          }

        onBind = false;
      }

    public void onItemChanged(int position)
      {
        if (!onBind)
          {
            notifyItemChanged(position);
          }
      }

    public int getItemCount()
      {
        return ImageCache.getInstance().size();
      }

    public ImagesAdapter(DataManager manager)
      {
        dataManager = manager;
      }

    private DataManager dataManager;

    private boolean onBind;

  }
