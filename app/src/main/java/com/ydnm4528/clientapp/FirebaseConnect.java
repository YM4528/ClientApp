package com.ydnm4528.clientapp;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class FirebaseConnect {

    FirebaseFirestore db;
    CollectionReference movieRef, seriesRef, categoryRef, episodeRef, settingRef;

    Context context;
    FragmentManager fm;


    public FirebaseConnect(Context context) {

        this.context = context;
        db = FirebaseFirestore.getInstance();
        movieRef = db.collection("movies");
        seriesRef = db.collection("Series");
        categoryRef = db.collection("categories");
        episodeRef = db.collection("episodes");
        settingRef = db.collection("setting");


    }

    public FirebaseConnect(Context context, FragmentManager fm) {

        this.context = context;
        this.fm = fm;
        db = FirebaseFirestore.getInstance();
        movieRef = db.collection("movies");
        seriesRef = db.collection("Series");
        categoryRef = db.collection("categories");
        episodeRef = db.collection("episodes");
        settingRef = db.collection("setting");


    }

    public void showSlide()
    {
        settingRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                SettingModel settingModel = new SettingModel();
                HomeFragment.sampleImages.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots)
                {
                    settingModel = snapshot.toObject(SettingModel.class);
                }

                if (settingModel.useSlideSHow.equals("Yes"))
                {
                    HomeFragment.sampleImages.add(settingModel.slide1);
                    HomeFragment.sampleImages.add(settingModel.slide2);
                    HomeFragment.sampleImages.add(settingModel.slide3);
                    HomeFragment.sampleImages.add(settingModel.slide4);
                    HomeFragment.sampleImages.add(settingModel.slide5);

                    HomeFragment.carouselView.setPageCount(HomeFragment.sampleImages.size());
                    HomeFragment.carouselView.setImageListener(HomeFragment.imageListener);

                }
                else
                {
                    HomeFragment.carouselView.setVisibility(View.GONE);
                }

            }
        });


    }


    public void getAllMovies()
    {
        movieRef.orderBy("createdAt", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ArrayList<MovieModel> movieModels = new ArrayList<>();

                for (DocumentSnapshot s : queryDocumentSnapshots)
                {
                    movieModels.add(s.toObject(MovieModel.class));
                }

                MovieAdapter adapter = new MovieAdapter(movieModels, context, fm);

                HomeFragment.allmovies.setAdapter(adapter);
                LinearLayoutManager lm = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                HomeFragment.allmovies.setLayoutManager(lm);
                HomeFragment.txtallmovie.setText("All Movie ("+ movieModels.size()+") ");
            }
        });
    }

    public void getAllMoviesByCategory(final String category )
    {
        movieRef.whereEqualTo("movieCategory", category)

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                   ArrayList<MovieModel> movieModels = new ArrayList<>();

                   for (DocumentSnapshot s : queryDocumentSnapshots)
                   {
                       movieModels.add(s.toObject(MovieModel.class));
                   }

                   MovieAdapter adapter = new MovieAdapter(movieModels, context, fm);

                   HomeFragment.allmovies.setAdapter(adapter);
                   LinearLayoutManager lm = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                   HomeFragment.allmovies.setLayoutManager(lm);
                   HomeFragment.txtallmovie.setText( category + " Movie ("+ movieModels.size()+") ");


            }
        });

    }


    public void getAllSeriesByCategory(final String category) {

        seriesRef.whereEqualTo("seriesCategory", category)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        ArrayList<SeriesModel> seriesModels = new ArrayList<>();
                        ArrayList<String> seriesIds = new ArrayList();

                        for (DocumentSnapshot s : queryDocumentSnapshots) {
                            seriesModels.add(s.toObject(SeriesModel.class));
                            seriesIds.add(s.getId());
                        }

                        SeriesAdapter adapter = new SeriesAdapter(seriesModels, context, fm, seriesIds);

                        HomeFragment.allseries.setAdapter(adapter);
                        LinearLayoutManager lm = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                        HomeFragment.allseries.setLayoutManager(lm);
                        HomeFragment.txtallseries.setText(category + "Series (" + seriesModels.size() + ") ");
                    }
                });
    }

    public void getAllMoviesByMovieFragment()
    {
        movieRef.orderBy("createdAt", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ArrayList<MovieModel> movieModels = new ArrayList<>();

                for (DocumentSnapshot s : queryDocumentSnapshots)
                {
                    movieModels.add(s.toObject(MovieModel.class));
                }

                MovieAdapter adapter = new MovieAdapter(movieModels, context, fm);
                MovieFragment.allmovies.setAdapter(adapter);
                GridLayoutManager gm = new GridLayoutManager(context, 3);
                MovieFragment.allmovies.setLayoutManager(gm);
                MovieFragment.txtallmovie.setText("All Movie ("+ movieModels.size()+") ");
            }
        });


    }


    public void getAllSeries()
    {
        seriesRef.orderBy("createdAt", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ArrayList<SeriesModel> seriesModels = new ArrayList<>();
                ArrayList<String> seriesIds = new ArrayList();

                for (DocumentSnapshot s : queryDocumentSnapshots)
                {
                    seriesModels.add(s.toObject(SeriesModel.class));
                    seriesIds.add(s.getId());
                }

                SeriesAdapter adapter = new SeriesAdapter(seriesModels, context, fm, seriesIds);

                HomeFragment.allseries.setAdapter(adapter);
                LinearLayoutManager lm = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                HomeFragment.allseries.setLayoutManager(lm);
                HomeFragment.txtallseries.setText("All Series ("+ seriesModels.size()+") ");
            }
        });
    }

    public void getAllSeriesBySeriesFragment()
    {
        seriesRef.orderBy("createdAt", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ArrayList<SeriesModel> seriesModels = new ArrayList<>();
                ArrayList<String> seriesIds = new ArrayList();

                for (DocumentSnapshot s : queryDocumentSnapshots)
                {
                    seriesIds.add(s.getId());
                    seriesModels.add(s.toObject(SeriesModel.class));
                }

                SeriesAdapter adapter = new SeriesAdapter(seriesModels, context, fm, seriesIds);

                SeriesFragment.allseries.setAdapter(adapter);
                GridLayoutManager gm = new GridLayoutManager(context, 3);
                SeriesFragment.allseries.setLayoutManager(gm);
                SeriesFragment.txtallseries.setText("All Series ("+ seriesModels.size()+") ");
            }
        });
    }


    public void getAllCategory() {

        categoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ArrayList<CategoryModel> categoryModels = new ArrayList<>();

                CategoryModel model = new CategoryModel();
                model.categoryName = context.getString(R.string.all_str);
                categoryModels.add(model);


                for (DocumentSnapshot snapshot : queryDocumentSnapshots)
                {
                    categoryModels.add(snapshot.toObject(CategoryModel.class));
                }

                LinearLayoutManager lm  = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                CategoryAdapter adapter = new CategoryAdapter(categoryModels, context, fm);
                HomeFragment.allcategory.setAdapter(adapter);
                HomeFragment.allcategory.setLayoutManager(lm);
                HomeFragment.txtallcategory.setText("All Category" +  categoryModels.size() + ")");


            }
        });
    }

    public void getTopTenMovies()
    {
        movieRef.orderBy("movieCount", Query.Direction.DESCENDING).limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<MovieModel> movieModels = new ArrayList<>();

                        for (DocumentSnapshot s : queryDocumentSnapshots)
                        {
                            movieModels.add(s.toObject(MovieModel.class));
                        }

                        MovieAdapter adapter = new MovieAdapter(movieModels, context, fm);
                        MovieFragment.list.setAdapter(adapter);
                        MovieFragment.list.scrollToPosition(movieModels.size()/2);
                    }
                });
    }

    public void getTopTenSeries() {
        seriesRef.orderBy("seriesCount", Query.Direction.DESCENDING).limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<SeriesModel> seriesModels = new ArrayList<>();
                        ArrayList<String> seriesIds = new ArrayList();

                        for (DocumentSnapshot s : queryDocumentSnapshots)
                        {
                            seriesModels.add(s.toObject(SeriesModel.class));
                            seriesIds.add(s.getId());
                        }

                        SeriesAdapter adapter = new SeriesAdapter(seriesModels, context, fm, seriesIds);
                        SeriesFragment.list.setAdapter(adapter);
                        SeriesFragment.list.scrollToPosition(seriesModels.size()/2);
                    }
                });
    }

    public void updateSeriesCount(SeriesModel model, String id)
    {
        model.seriesCount+=1; // model.seriesCount+1;
        seriesRef.document(id).set(model);
    }

    public void getEpBySeriesName (final String seriesName)
    {
        episodeRef.whereEqualTo("episodeSeries", seriesName)

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<EpisodeModel> models = new ArrayList<>();
                        for (DocumentSnapshot s : queryDocumentSnapshots)
                        {
                            models.add(s.toObject(EpisodeModel.class));

                        }

                        EpisodeAdapter adapter = new EpisodeAdapter(models, context, fm);
                        SeriesDetailFragment.epCount.setImageBitmap(SeriesDetailFragment.textAsBitmap(models.size()+"", 10, Color.WHITE));
                        LinearLayoutManager lm = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                        SeriesDetailFragment.list.setAdapter(adapter);
                        SeriesDetailFragment.list.setLayoutManager(lm);


                    }
                });
    }

    public void getAllMoviesForSearchFragment() {

        movieRef.orderBy("createdAt", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ArrayList<MovieModel> movieModels = new ArrayList<>();

                for (DocumentSnapshot s : queryDocumentSnapshots)
                {
                    movieModels.add(s.toObject(MovieModel.class));
                }

                MovieAdapter adapter = new MovieAdapter(movieModels, context, fm);

                SearchFragment.allmovies.setAdapter(adapter);
                LinearLayoutManager lm = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                SearchFragment.allmovies.setLayoutManager(lm);
                SearchFragment.txtallmovie.setText("All Movie ("+ movieModels.size()+") ");
            }
        });

        seriesRef.orderBy("createdAt", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ArrayList<SeriesModel> seriesModels = new ArrayList<>();
                ArrayList<String> seriesIds = new ArrayList();

                for (DocumentSnapshot s : queryDocumentSnapshots)
                {
                    seriesModels.add(s.toObject(SeriesModel.class));
                    seriesIds.add(s.getId());
                }

                SeriesAdapter adapter = new SeriesAdapter(seriesModels, context, fm, seriesIds);

                SearchFragment.allseries.setAdapter(adapter);
                LinearLayoutManager lm = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                SearchFragment.allseries.setLayoutManager(lm);
                SearchFragment.txtallseries.setText("All Series ("+ seriesModels.size()+") ");
            }
        });




    }

    public void getAllMoviesForSearchFragmentByQuery(final String query) {

        movieRef.orderBy("movieName")
                .startAt(query)
                .endAt(query+"\uf8ff").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ArrayList<MovieModel> movieModels = new ArrayList<>();

                for (DocumentSnapshot s : queryDocumentSnapshots)
                {
                    movieModels.add(s.toObject(MovieModel.class));
                }

                MovieAdapter adapter = new MovieAdapter(movieModels, context, fm);

                SearchFragment.allmovies.setAdapter(adapter);
                LinearLayoutManager lm = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                SearchFragment.allmovies.setLayoutManager(lm);
                SearchFragment.txtallmovie.setText("Search Move : "+query+" ("+ movieModels.size()+") ");
            }
        });

        seriesRef.orderBy("seriesName").
                startAt(query)
                .endAt(query + "\uf8ff").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ArrayList<SeriesModel> seriesModels = new ArrayList<>();
                ArrayList<String> seriesIds = new ArrayList();

                for (DocumentSnapshot s : queryDocumentSnapshots)
                {
                    seriesModels.add(s.toObject(SeriesModel.class));
                    seriesIds.add(s.getId());
                }

                SeriesAdapter adapter = new SeriesAdapter(seriesModels, context, fm, seriesIds);

                SearchFragment.allseries.setAdapter(adapter);
                LinearLayoutManager lm = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                SearchFragment.allseries.setLayoutManager(lm);
                SearchFragment.txtallseries.setText("Search Series : "+query+" ("+ seriesModels.size()+") ");
            }
        });




    }
}
