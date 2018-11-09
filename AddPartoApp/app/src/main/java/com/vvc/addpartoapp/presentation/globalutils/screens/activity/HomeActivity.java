package com.vvc.addpartoapp.presentation.globalutils.screens.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextView;
import com.vvc.addpartoapp.presentation.globalutils.screens.BaseActivity;
import com.vvc.addpartoapp.presentation.globalutils.screens.HomeFragment;
import com.vvc.addpartoapp.presentation.globalutils.screens.navigationfragments.AddMenuitemsFrg;
import com.vvc.addpartoapp.presentation.globalutils.screens.navigationfragments.AddVendorFrg;
import com.vvc.addpartoapp.presentation.globalutils.screens.navigationfragments.ViewMenuItemsFrg;
import com.vvc.addpartoapp.presentation.globalutils.screens.navigationfragments.ViewVendorFrg;

public class HomeActivity extends BaseActivity implements View.OnClickListener{

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private MaterialMenuDrawable mToolbarMorphDrawable;

    @Override
    public int setLayoutResuourse() {
        return R.layout.activity_home;
    }

    @Override
    public void initGUI() {

        initDrawaerView();


        actionEvents();
    }


    private void actionEvents() {

        ((CustomTextView)findViewById(R.id.add_menu_items)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               replaceFragment(new AddMenuitemsFrg());
            }
        });

        ((CustomTextView)findViewById(R.id.add_vendor)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddVendorFrg());
            }
        });

        ((CustomTextView)findViewById(R.id.view_vendors)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new ViewVendorFrg());
            }
        });

        ((CustomTextView)findViewById(R.id.view_menu_items)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new ViewMenuItemsFrg());
            }
        });

    }

    @Override
    public void initData() {

    }

    private void initDrawaerView() {
        replaceFragment(new AddVendorFrg());

        ((AppCompatImageView) findViewById(R.id.ic_back)).setVisibility(View.GONE);

        //  ((LinearLayout) findViewById(R.id.title_layout)).setPadding(0, 0, GlobalMethods.getPixToDP(getApplicationContext(), 60), 0);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
//        mFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();





//
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                mToolbarMorphDrawable.animateIconState(MaterialMenuDrawable
                        .IconState.BURGER);
                super.onDrawerClosed(drawerView);


            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(false);

        mToolbarMorphDrawable = new MaterialMenuDrawable(this, Color.GRAY,
                MaterialMenuDrawable.Stroke.THIN);

        mToolbarMorphDrawable.setIconState(MaterialMenuDrawable.IconState.BURGER);


        mDrawerToggle.setHomeAsUpIndicator(mToolbarMorphDrawable);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
            }
        });
        mDrawerToggle.syncState();


    }

    private void closeDrawer() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mToolbarMorphDrawable.animateIconState(MaterialMenuDrawable
                    .IconState.BURGER);
            // mDrawerToggle.setHomeAsUpIndicator(drawable);

            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //mDrawerToggle.setHomeAsUpIndicator(drawable_back);
            mToolbarMorphDrawable.animateIconState(MaterialMenuDrawable
                    .IconState.ARROW);
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void replaceFragment(Fragment fragment) {

        Bundle bundle=new Bundle();
        try {
       /*     mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerViews, fragment);
            mFragmentTransaction.addToBackStack(tag);
            mFragmentTransaction.commit();

*/


//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();

            Bundle b = new Bundle();
//        b.putInt("categoryId", categoryId);
//        b.putInt("tabPosition", tabPosition);
//        b.putInt("subcategoryId", subcategoryId);
//        b.putString("categoryName", categoryName);
//        fragment.setArguments(b);
            mFragmentTransaction.replace(R.id.containerViews, fragment);
            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            mFragmentTransaction.commit();

            closeDrawer();


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("excep",e.toString());
        }

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
