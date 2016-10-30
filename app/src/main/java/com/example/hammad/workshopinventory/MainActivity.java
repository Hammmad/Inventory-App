package com.example.hammad.workshopinventory;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import Adapter.DescAdapter;
import Fragments.DetailFragment;
import Fragments.MainFragment;
import Fragments.SalesRecordFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.CallBackMainFragment, DetailFragment.CallbackDetailFragment{

    private String mPartNo;
    DescAdapter descAdapter;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportFragmentManager().beginTransaction().
//                replace(R.id.main_activity_container,new MainFragment()).commit();


        Toolbar maintoolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(maintoolbar);


        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setVisibility(View.VISIBLE);
        adapter = new Adapter.PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_TabLayout);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItemCompat.OnActionExpandListener actionExpandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };

        MenuItem searchitem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchitem,actionExpandListener);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchitem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        MenuItemCompat.setOnActionExpandListener(searchitem,actionExpandListener);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

//                descAdapter.getFilter().filter();

                return true;
            }
        });




        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add:{
                Intent intent = new Intent(this, AddItems.class);
                intent.putExtra("Isupdate", false);
                startActivity(intent);
            }
//            case R.id.action_salesrecords:{
//                callBackMainFragment.displaySalesRecordFragment();
//            }
        }
        return false;
    }

    @Override
    public void displayDetailFragment() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setVisibility(View.INVISIBLE);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_TabLayout);
        tabLayout.setVisibility(View.INVISIBLE);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.full_frame);
        frameLayout.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().
                replace(R.id.full_frame, new DetailFragment());
        fragmentTransaction.addToBackStack(null).commit();


    }

    @Override
    public void onBackPressed() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.full_frame);
        frameLayout.setVisibility(View.INVISIBLE);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setVisibility(View.VISIBLE);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_TabLayout);
        tabLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void displaySalesRecordFragment() {

        getSupportFragmentManager().beginTransaction().replace
                (R.id.main_activity_container, new SalesRecordFragment()).addToBackStack(null).commit();
    }

    @Override
    public void sendpartNo(String partNo) {
        mPartNo= partNo;
    }

    @Override
    public String getPartNo() {

        return mPartNo;
    }
}
