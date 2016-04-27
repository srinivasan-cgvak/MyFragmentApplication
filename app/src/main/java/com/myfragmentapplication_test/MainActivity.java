package com.myfragmentapplication_test;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myfragmentapplication_test.dummy.DummyContent;

import adapter.PagerAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ItemFragment.OnListFragmentInteractionListener,
        LoaderSampleFragment.OnFragmentInteractionListener,DetailFragment.OnFragmentInteractionListener,ViewPagerFragment.OnFragmentInteractionListener{

    @Bind(R.id.linearLayoutContent) LinearLayout linearLayoutContent;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.nav_view) NavigationView navigationView;
    int cursorPosition = 0;
    int FrgamentSelected = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        if(savedInstanceState != null){
            cursorPosition = savedInstanceState.getInt("Position");
        }

       // getSupportFragmentManager().beginTransaction().add(new ViewPagerFragment(), ViewPagerFragment.TAG).commit();
        ShowHomePage();
        tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab2"));
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(getResources().getBoolean(R.bool.dual_pane)){
            if(getSupportFragmentManager().findFragmentByTag(ItemFragment.TAG)!= null && getSupportFragmentManager().findFragmentByTag(DetailFragment.TAG)!= null) {
                showDetails(cursorPosition);
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Debug.stopMethodTracing();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position",cursorPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getSupportFragmentManager().getBackStackEntryCount() >0) {
            getSupportFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (FrgamentSelected){
            case 0:
                menu.findItem(R.id.action_settings).setVisible(true);
                break;
            case 1:
                menu.findItem(R.id.action_settings).setVisible(true);
                menu.findItem(R.id.item_add).setVisible(true);
                menu.findItem(R.id.item_search_action).setVisible(true);
                break;
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        android.support.v4.app.Fragment selectedFragment = null;
        Class selectedFragmentClass = null;
        int id = item.getItemId();
        FrgamentSelected = 2;

        if (id == R.id.nav_camera) {
            // Handle the camera action
           // selectedFragmentClass = ViewPagerFragment.class;
            ShowHomePage();
        } else if (id == R.id.nav_gallery) {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            selectedFragmentClass = ItemFragment.class;
        } else if (id == R.id.nav_slideshow) {
            FrgamentSelected = 1;
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            selectedFragmentClass = LoaderSampleFragment.class;
        } else if (id == R.id.nav_manage) {
            FrgamentSelected = 0;
            ShowHomePage();
          //  selectedFragmentClass = ViewPagerFragment.class;
        } else if (id == R.id.nav_share) {

            startActivity(new Intent(MainActivity.this,ShareActivity.class));

        } else if (id == R.id.nav_send) {

            navigationView.getMenu().add("Dynamic Menu");

            SubMenu subMenu = navigationView.getMenu().addSubMenu("Submenu");
            subMenu.add("SubMenu Item1");
            subMenu.add("SubMenu Item2");
            subMenu.add("SubMenu Item3");

        }

        if(selectedFragmentClass != null) {
            try {
                selectedFragment = (android.support.v4.app.Fragment) selectedFragmentClass.newInstance();
            }catch (ClassCastException e){

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            getSupportFragmentManager().beginTransaction().replace(linearLayoutContent.getId(), selectedFragment,ItemFragment.TAG).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void ShowHomePage() {
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(linearLayoutContent.getId(),new EmptyFragment()).commit();
    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(this,"Item Clicked"+item.content+"---"+item.id+"--------->"+item.details,Toast.LENGTH_SHORT).show();
        cursorPosition = Integer.parseInt(item.id);
        showDetails(cursorPosition);
    }

    private void showDetails(int cursorPosition) {
        if(getResources().getBoolean(R.bool.dual_pane)) {

            DetailFragment detailFragment = new DetailFragment();
            //To check the back stack is already having this fragment
            if (getSupportFragmentManager().findFragmentByTag(DetailFragment.TAG) == null) {
                //There is no fragment in the back stack
                Toast.makeText(this, "Fragment is not in Stack", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Fragment in Stack", Toast.LENGTH_SHORT).show();
            }
            if(getSupportFragmentManager().findFragmentByTag(DetailFragment.TAG) == null){
                getSupportFragmentManager().beginTransaction().add(linearLayoutContent.getId(), detailFragment).addToBackStack(DetailFragment.TAG).commit();
            }

            //To set the width based on the DisplaySize in the Landscape mode
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            ItemFragment itemFragment = (ItemFragment) getSupportFragmentManager().findFragmentByTag(ItemFragment.TAG);
            if(itemFragment!= null){
                LinearLayout.LayoutParams drLayoutParams =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                drLayoutParams.width = size.x /2;
                itemFragment.getView().setLayoutParams(drLayoutParams);
            }
            DetailFragment detailFragment1 = (DetailFragment) getSupportFragmentManager().findFragmentByTag(DetailFragment.TAG);
            if(detailFragment1 != null){
                FrameLayout.LayoutParams frLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                frLayoutParams.width = size.x /2;
                detailFragment1.getView().setLayoutParams(frLayoutParams);
            }

        }else {
            Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
            intent.putExtra("index",cursorPosition);
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class EmptyFragment extends Fragment{

    }
}
