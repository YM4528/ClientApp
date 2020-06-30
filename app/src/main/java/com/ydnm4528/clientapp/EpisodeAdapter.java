package com.ydnm4528.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder> {

    ArrayList<EpisodeModel> episodeModels = new ArrayList<>();
    Context context;
    FragmentManager fm;

    private RewardedVideoAd mRewardedVideoAd;



    @NonNull
    @Override
    public EpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.episodeitem,parent, false);
        EpisodeHolder holder = new EpisodeHolder(myview);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeHolder holder, final int position) {

        holder.epName.setText(episodeModels.get(position).episodeName);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedVideoAd.isLoaded())
                {
                    mRewardedVideoAd.show();
                }

                EpisodeModel model = episodeModels.get(position);
                MovieModel movieModel = new MovieModel();
                movieModel.movieName = model.episodeName;
                movieModel.movieVideo = model.episodeVideo;
                GotoNext(position, movieModel);

            }
        });

    }


    public void GotoNext(int position, MovieModel movieModel)

    {

        if(MainActivity.preFrag.equals(context.getString(R.string.search_frag)))
        {
            MainActivity.preFrag = context.getString(R.string.search_frag);
        }

        else {

            MainActivity.preFrag = MainActivity.currentFrag;

        }
        MainActivity.currentFrag = context.getString(R.string.movie_det_frag);
        VideoDetailFragment detfrag = new VideoDetailFragment();
        detfrag.movieModel = movieModel;
        setFragment(detfrag);

        MovieFragment.setHeader(movieModel.movieName);
    }


    public void setFragment(Fragment f)
    {

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navContact, f);
        ft.commit();


    }

    @Override
    public int getItemCount() {
        return episodeModels.size();
    }

    public EpisodeAdapter(ArrayList<EpisodeModel> episodeModels, Context context, FragmentManager fm) {
        this.episodeModels = episodeModels;
        this.context = context;
        this.fm = fm;
        GoogleAds googleAds = new GoogleAds();
        this.mRewardedVideoAd = googleAds.loadRewardedVideoAds(context);

    }



    public class EpisodeHolder extends RecyclerView.ViewHolder {

        TextView epName;
        ImageView play, download;

        public EpisodeHolder(@NonNull View itemView) {
            super(itemView);
            epName = itemView.findViewById(R.id.epName);
            play = itemView.findViewById(R.id.playep);
            download = itemView.findViewById(R.id.downloadep);


        }
    }

}
