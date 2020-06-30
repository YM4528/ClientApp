package com.ydnm4528.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.MovieHolder> {

    ArrayList<SeriesModel> SeriesModels = new ArrayList<>();
    Context context;
    FragmentManager fm;
  private InterstitialAd interstitialAd;

    public SeriesAdapter(ArrayList<SeriesModel> SeriesModels, final Context context, FragmentManager fm) {
        this.SeriesModels = SeriesModels;
        this.context = context;
        this.fm = fm;

     GoogleAds googleAds = new GoogleAds();
      interstitialAd =  googleAds.loadInterstitialAds(context);
    }

    ArrayList<String> seriesIds = new ArrayList<>();

    public SeriesAdapter(ArrayList<SeriesModel> seriesModels, final Context context, FragmentManager fm, ArrayList<String> seriesIds) {

        this.SeriesModels = seriesModels;
        this.context = context;
        this.fm = fm;
        this.seriesIds = seriesIds;

        GoogleAds googleAds = new GoogleAds();
        interstitialAd =  googleAds.loadInterstitialAds(context);




    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movieitem,parent,false);
        return new MovieHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {

        Glide.with(context)
                .load(SeriesModels.get(position).seriesImage)
                .into(holder.movieImage);

        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(interstitialAd.isLoaded())
                {


                    interstitialAd.show();
                    GotoNext(position);
                    SeriesFragment.setHeader(SeriesModels.get(position).seriesName);

                }
                else
                {
                    GotoNext(position);
                    SeriesFragment.setHeader(SeriesModels.get(position).seriesName);
                }



             /* */

            }
        });
    }

    public void GotoNext(int position)
    {

        if (MainActivity.preFrag.equals(context.getString(R.string.search_frag)))
        {
            MainActivity.preFrag = context.getString(R.string.search_frag);
        }

        else {

            MainActivity.preFrag = MainActivity.currentFrag;

        }
            MainActivity.currentFrag = context.getString(R.string.series_det_frag);
            SeriesDetailFragment detfrag = new SeriesDetailFragment();
            detfrag.model = SeriesModels.get(position);
            detfrag.seriesID = seriesIds.get(position);
            setFragment(detfrag);



    }

    public void setFragment(Fragment detfrag)
    {
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.navContact, detfrag);
        ft.commit();
    }

    @Override
    public int getItemCount() {
        return SeriesModels.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        ImageView movieImage;


        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.image);


        }
    }
}
