package com.antika.berk.ggeasy.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.fragment.ChallangeFragment;
import com.antika.berk.ggeasy.fragment.ChampionTabFragment;
import com.antika.berk.ggeasy.fragment.CurrentMatchFragment;
import com.antika.berk.ggeasy.fragment.FourSkillTabHost;
import com.antika.berk.ggeasy.fragment.LoginFragment;
import com.antika.berk.ggeasy.fragment.LotteriesFragment;
import com.antika.berk.ggeasy.fragment.MatchHistoryFragment;
import com.antika.berk.ggeasy.fragment.RateFragment;
import com.antika.berk.ggeasy.fragment.SumonnerFragment;
import com.antika.berk.ggeasy.helper.DBHelper;

public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        dbHelper = new DBHelper(getApplicationContext());



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //YÖNLENDİRME
        CurrentMatchFragment cmf = new CurrentMatchFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(
                R.id.content_main_page,
                cmf,"0").commit();
        navigationView.setCheckedItem(R.id.nav_camera);


        if (dbHelper.getMatch("Gorev99").length() == 0){
            dbHelper.insertMatch("0","Gorev99");

        }
        else {
            if((Integer.parseInt(dbHelper.getMatch("Gorev99")) % 150 == 10)) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                RateFragment alertDialog = new RateFragment();
                alertDialog.show(fragmentManager,"fragment_alert");
            }
            int i = Integer.parseInt(dbHelper.getMatch("Gorev99"));
            i++;
            dbHelper.insertMatch(""+i,"Gorev99");
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();




        if (id == R.id.nav_camera) {
            CurrentMatchFragment cmf = new CurrentMatchFragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(
                    R.id.content_main_page,
                    cmf,"0").commit();

        } else if (id == R.id.nav_gallery) {
            SumonnerFragment cmf = new SumonnerFragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(
                    R.id.content_main_page,
                    cmf,"0").commit();
        }  else if (id == R.id.nav_history) {
            MatchHistoryFragment cmf = new MatchHistoryFragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(
                    R.id.content_main_page,
                    cmf,"0").commit();
        }
        else if (id == R.id.nav_challenge) {
            if (dbHelper.getUser().getEmail().length() > 0){
                ChallangeFragment cmf = new ChallangeFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(
                        R.id.content_main_page,
                        cmf,"0").commit();
            }
            else {
                FragmentManager fm = getSupportFragmentManager();
                LoginFragment alertDialog = new LoginFragment();
                alertDialog.show(fm,"fragment_alert");
            }

        }
        else if (id == R.id.nav_skill) {
            FourSkillTabHost cmf = new FourSkillTabHost();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(
                    R.id.content_main_page,
                    cmf,"0").commit();
        }else if (id == R.id.nav_slideshow) {
            ChampionTabFragment cmf = new ChampionTabFragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(
                    R.id.content_main_page,
                    cmf,"0").commit();
        }else if (id == R.id.nav_lottery) {
            LotteriesFragment cmf = new LotteriesFragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(
                    R.id.content_main_page,
                    cmf,"0").commit();
        } else if (id == R.id.nav_share)  {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "GGEasy-LoL");
            String sAux = "\nBest League of Legends app\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.antika.berk.ggeasylol \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share"));
        } else if (id == R.id.nav_send)   {
            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id==R.id.facebook) {
            Intent intent;
            try {
                getApplicationContext().getPackageManager()
                        .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("fb://page/225183367912890")); //Trys to make intent with FB's URI
            } catch (Exception e) {
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/GGEasyTR/")); //catches and opens a url to the desired page
            }
            startActivity(intent);
        } 

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }


}
