package com.omairtech.apirequest.remote.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageRequest;
import com.omairtech.apirequest.R;
import com.omairtech.apirequest.remote.model.RequestQueue;

public class LoadImage {

    public LoadImage(Context context, String url, ImageView imageView){
        ImageRequest request = new ImageRequest(url, imageView::setImageBitmap
                , 0, 0, null, Bitmap.Config.RGB_565
                , error -> imageView.setImageResource(R.drawable.ic_baseline_broken_image_24));
        RequestQueue.getInstance(context).add(request);
    }
}
