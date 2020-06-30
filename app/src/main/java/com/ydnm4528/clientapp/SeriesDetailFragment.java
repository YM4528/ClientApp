package com.ydnm4528.clientapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesDetailFragment extends Fragment {

    public static   SeriesModel model;
    public static String seriesID;
    static RecyclerView list;
    static ImageView image;
    static TextView seriesName, viewCount;
    static FloatingActionButton epCount;
    private InterstitialAd interstitialAd;


     View myview;

     Handler handler = new Handler();
     Runnable runnable = new Runnable() {
         @Override
         public void run() {
             if (interstitialAd.isLoaded())
             {
                 interstitialAd.show();
             }
         }
     };

    public SeriesDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview =  inflater.inflate(R.layout.fragment_series_detail, container, false);

        GoogleAds googleAds = new GoogleAds();
        googleAds.loadAdVerticalAds(myview, getContext(), getActivity());
        googleAds.loadNativeAds(myview, getContext());

        interstitialAd =  googleAds.loadInterstitialAds(getContext());
        handler.postDelayed(runnable, 20000);

        list = myview.findViewById(R.id.eplist);
        image = myview.findViewById(R.id.image);
        seriesName = myview.findViewById(R.id.series_name);
        viewCount = myview.findViewById(R.id.textviewcount);
        epCount = myview.findViewById(R.id.txtepcount);

        FirebaseConnect firebaseConnect = new FirebaseConnect(getContext(), getFragmentManager());
        firebaseConnect.updateSeriesCount(model, seriesID);

      if(model!= null)
      {
          Glide.with(getContext())
                  .load(model.seriesImage)
                  .into(image);

          seriesName.setText(model.seriesName);
          viewCount.setText(model.seriesCount+"");
      }

      FirebaseConnect fConnect = new FirebaseConnect(getContext(), getFragmentManager());
      fConnect.getEpBySeriesName(model.seriesName);


        return  myview;
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }




}
