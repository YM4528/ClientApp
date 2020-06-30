package com.ydnm4528.clientapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import java.util.ArrayList;




/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static CarouselView carouselView;
    public static ArrayList<String>  sampleImages = new ArrayList<>();

    View myview;
    static Context context;
   static RecyclerView allmovies;
   static TextView txtallmovie, txtallseries, txtallcategory;
   static RecyclerView allseries, allcategory;

    RewardedVideoAd rewardedVideoAd;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (rewardedVideoAd.isLoaded())
            {
                rewardedVideoAd.show();
            }

        }
    };


    static ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(context)
                    .load(sampleImages.get(position))
                    .into(imageView);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_home, container, false);

        GoogleAds googleAds = new GoogleAds();
        rewardedVideoAd = googleAds.loadRewardedVideoAds(getContext());

        googleAds.loadThreeVerticalAds(myview, getContext(), getActivity());

        handler.postDelayed(runnable, 10000);

        carouselView =  myview.findViewById(R.id.carouselView);
        context = getContext();
        allmovies = myview.findViewById(R.id.allmovies);
        txtallmovie = myview.findViewById(R.id.txtmovie);
        allseries = myview.findViewById(R.id.allseries);
        txtallseries = myview.findViewById(R.id.txtseries);
        txtallcategory = myview.findViewById(R.id.txtcategory);
        allcategory = myview.findViewById(R.id.allcategory);

        SeriesFragment.activity = getActivity();
        MovieFragment.activity = getActivity();



        HomeFragment.carouselView.setPageCount(0);
        HomeFragment.carouselView.setImageListener(imageListener);

        FirebaseConnect fConnect = new FirebaseConnect(getContext(), getFragmentManager());
        fConnect.showSlide();
        fConnect.getAllMovies();
        fConnect.getAllSeries();
        fConnect.getAllCategory();



        return myview;
}



    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(getContext(), adWidth);
    }






}
