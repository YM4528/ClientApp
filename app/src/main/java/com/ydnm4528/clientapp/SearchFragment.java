package com.ydnm4528.clientapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    View myview;
    EditText search;
    static Context context;
    static TextView txtallmovie, txtallseries, txtallcategory;
    static RecyclerView allmovies, allseries, allcategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         myview =  inflater.inflate(R.layout.fragment_search, container, false);

         GoogleAds googleAds = new GoogleAds();
         googleAds.loadAdVerticalAds(myview, getContext(), getActivity());

         initUI();
         final FirebaseConnect firebaseConnect = new FirebaseConnect(context, getFragmentManager());
         firebaseConnect.getAllMoviesForSearchFragment();
         search.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {


             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

                 if (search.getText().toString().trim().equals(""))
                 {
                     firebaseConnect.getAllMoviesForSearchFragment();
                 }
                 else
                 {
                     firebaseConnect.getAllMoviesForSearchFragmentByQuery(search.getText().toString().trim());
                 }

             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

         return myview;
    }

    public void initUI()
    {
        search = myview.findViewById(R.id.search);
        context = getContext();
        txtallmovie = myview.findViewById(R.id.txtmovie);
        txtallseries = myview.findViewById(R.id.txtseries);
        allmovies = myview.findViewById(R.id.allmovies);
        allseries = myview.findViewById(R.id.allseries);

    }
}
