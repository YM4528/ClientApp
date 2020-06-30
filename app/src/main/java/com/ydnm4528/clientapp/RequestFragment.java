package com.ydnm4528.clientapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    public RequestFragment() {
        // Required empty public constructor
    }

    FirebaseFirestore db;
    CollectionReference ref;

    View myview;
    Button btnsave, btncancel;
    EditText edtname, edtimage;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview =  inflater.inflate(R.layout.fragment_request, container, false);

        GoogleAds googleAds = new GoogleAds();
        rewardedVideoAd = googleAds.loadRewardedVideoAds(getContext());
        googleAds.loadAdVerticalAds(myview, getContext(), getActivity());

       handler.postDelayed(runnable, 5000);

        edtname = myview.findViewById(R.id.edtname);
        edtimage = myview.findViewById(R.id.edtimage);
        btnsave = myview.findViewById(R.id.btnsave);
        btncancel = myview.findViewById(R.id.btncancel);

        db = FirebaseFirestore.getInstance();
        final CollectionReference ref = db.collection(getString(R.string.request_frag));


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rewardedVideoAd.isLoaded())
                {
                    rewardedVideoAd.show();
                }

               if (!edtimage.getText().toString().trim().equals("")
               || !edtname.getText().toString().trim().equals(""))
               {
                   RequestModel model = new RequestModel();
                   model.namee = edtname.getText().toString().trim();
                   model.imagelink = edtimage.getText().toString().trim();
                   java.text.SimpleDateFormat formate = new SimpleDateFormat("ddmmyyyyhhMMss");
                   model.createdAt= formate.format(new Date());
                   ref.add(model);
                   edtname.setText("");
                   edtimage.setText("");
                   Toasty.success(getContext(), "Request Sent", Toasty.LENGTH_LONG).show();
               }
               else
               {
                   Toasty.error(getContext(), "Please fill Data", Toasty.LENGTH_LONG).show();

               }


            }
        });



        return myview;
    }


}
