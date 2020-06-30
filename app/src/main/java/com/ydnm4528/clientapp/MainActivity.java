package com.ydnm4528.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import es.dmoral.toasty.Toasty;
import io.grpc.android.BuildConfig;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    static String currentFrag = "";
    static String preFrag = "";
  static   Toolbar toolbar;
  LinearLayout numberpin, maindata;
  EditText edtnumber;
  Button sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tbar);
        setSupportActionBar(toolbar);

        numberpin = findViewById(R.id.numberpin);
        maindata = findViewById(R.id.maindata);
        edtnumber = findViewById(R.id.number);
        sure = findViewById(R.id.sure);


        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtnumber.getText().toString().trim().equals(getString(R.string.number_pin)))
                {
                    setFragment(new HomeFragment());
                        numberpin.setVisibility(View.GONE);
                        maindata.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toasty.error(getApplicationContext(),"Your number is wrong", Toasty.LENGTH_LONG).show();
                }

            }
        });



        setTitle(getString(R.string.home_frag));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer);
        drawer.addDrawerListener(toggle);
        setSupportActionBar(toolbar);
        toggle.syncState();
        setFragment(new HomeFragment());



        NavigationView navigationView = findViewById(R.id.navView);

        View headerview = navigationView.getHeaderView(0);
        TextView txtVersion = headerview.findViewById(R.id.versionname);
        txtVersion.setText("Version" + BuildConfig.VERSION_NAME);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home_menu)
                {
                    setTitle(getString(R.string.home_frag));
                    setFragment(new HomeFragment());
                    currentFrag = getString(R.string.home_frag);

                }
                else if (item.getItemId() == R.id.series_menu)
                {
                    setTitle(getString(R.string.series_frag));
                    setFragment(new SeriesFragment());
                    currentFrag = getString(R.string.series_frag);



                }
                else if (item.getItemId() == R.id.movie_menu)
                {
                    setTitle(getString(R.string.movies_frag));
                    setFragment(new MovieFragment());

                    currentFrag = getString(R.string.movies_frag);
                }

                else if ( item.getItemId() == R.id.share_menu)
                {
                    Intent shareintent = new Intent();
                    shareintent.setAction(Intent.ACTION_SEND);

                    currentFrag = getString(R.string.share_frag) ;

                   shareintent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID);

                    // shareintent.putExtra(Intent.EXTRA_TEXT, "https://www.mediafire.com/file/xobozpgo7keamen/IMG_4928.MOV/file");
                    shareintent.setType("text/plain");
                    startActivity(shareintent);
                }
                else if (item.getItemId() == R.id.request_menu)
                {
                    setFragment(new RequestFragment());
                    setTitle(getString(R.string.request_frag));
                    currentFrag = getString(R.string.request_frag);
                }

                if (VideoDetailFragment.player!=null)
                {
                    VideoDetailFragment.player.stop();
                }

                else if (item.getItemId() == R.id.about_menu)
                {
                    setFragment(new AboutFragment());
                    setTitle(getString(R.string.about_frag));
                    currentFrag = getString(R.string.about_frag);
                }

                else if (item.getItemId() == R.id.serarch_menu)
                {
                    setFragment(new SearchFragment());
                    setTitle(getString(R.string.search_frag));
                    currentFrag = getString(R.string.search_frag);
                }
                drawer.closeDrawer(Gravity.LEFT);

                return false;
            }
        });
    }

    public void setFragment(Fragment f)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navContact, f);
        ft.commit();
    }


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onBackPressed() {

        if (currentFrag.equals(getString(R.string.home_frag)))
        {
            if (drawer.isDrawerOpen(Gravity.LEFT))
            {
                drawer.closeDrawer(Gravity.LEFT);
            }
            else
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Confirmation");
                alert.setMessage("Are you sure to exit");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        finishAffinity();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

            }
        }

        else if (currentFrag.equals(getString(R.string.movies_frag))
        || currentFrag.equals(getString(R.string.request_frag)) ||
                currentFrag.equals(getString(R.string.about_frag)) ||
        currentFrag.equals(getString(R.string.series_frag)) || currentFrag.equals(getString(R.string.search_frag))
     )
        {
            setFragment(new HomeFragment());
            currentFrag = getString(R.string.home_frag);
            setTitle(currentFrag);
        }

        else if (currentFrag.equals(getString(R.string.series_det_frag)))
        {
            if (preFrag.equals(getString(R.string.home_frag)))
            {
                setFragment(new HomeFragment());
                currentFrag = getString(R.string.home_frag);
                setTitle(currentFrag);
            }
            else if (preFrag.equals(getString(R.string.series_frag)))
            {
                setFragment(new SeriesFragment());
                currentFrag = getString(R.string.series_frag);
                setTitle(currentFrag);
            }

            else if (preFrag.equals(getString(R.string.movie_det_frag)))
            {
                setFragment(new SeriesFragment());
                currentFrag = getString(R.string.series_frag);
                setTitle(currentFrag);
                preFrag = getString(R.string.series_det_frag);
                VideoDetailFragment.player.stop();
            }
            else if (preFrag.equals(getString(R.string.search_frag)))
            {
                setFragment(new SearchFragment());
                currentFrag = getString(R.string.search_frag);
                setTitle(currentFrag);
            }

        }

        else if (currentFrag.equals(getString(R.string.movie_det_frag)))
        {
            if (preFrag.equals(getString(R.string.home_frag)))
            {
                setFragment(new HomeFragment());
                currentFrag = getString(R.string.home_frag);
                setTitle(currentFrag);

            }
            else if (preFrag.equals(getString(R.string.movies_frag)))
            {
                setFragment(new MovieFragment());
                currentFrag = getString(R.string.movies_frag);
                setTitle(currentFrag);
            }
            else if (preFrag.equals(getString(R.string.series_det_frag)))
            {

                SeriesDetailFragment detfrag = new SeriesDetailFragment();
                setFragment(detfrag);
                currentFrag = getString(R.string.series_det_frag);
                preFrag = getString(R.string.movie_det_frag);
                setTitle(SeriesDetailFragment.model.seriesName);
            }

            else if (preFrag.equals(getString(R.string.search_frag)))
            {
                setFragment(new SearchFragment());
                currentFrag = getString(R.string.search_frag);
                setTitle(currentFrag);
                preFrag = getString(R.string.movie_det_frag);
            }


            else
            {
                setFragment(new SeriesFragment());
                currentFrag = getString(R.string.series_frag);
                setTitle(currentFrag);

            }

            VideoDetailFragment.player.stop();

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            MainActivity.toolbar.setVisibility(View.VISIBLE);

        }

        else
        {
            super.onBackPressed();
        }

        if (VideoDetailFragment.player!=null)
        {
            VideoDetailFragment.player.stop();
        }
    }
}
