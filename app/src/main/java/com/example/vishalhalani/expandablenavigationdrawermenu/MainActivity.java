package com.example.vishalhalani.expandablenavigationdrawermenu;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebViewFragment;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_ABOUT_US;
import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_CONTACT_US;
import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_DONATE;
import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_FOLLOW_US;
import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_HOME;
import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_LOGOUT;

import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_PRIVACY_POLICY;
import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_RATE_APP;
import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_SHARE_APP;
import static com.example.vishalhalani.expandablenavigationdrawermenu.Const.MENU_TERMS_CONDITION;

public class MainActivity extends AppCompatActivity {

    // index to identify current nav menu item
    public static int navItemIndex = 0;
    // tags used to attach the fragments
    private static final String TAG_HOME = "home";

    public static String CURRENT_TAG = TAG_HOME;
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private List<MenuModel> headerList = new ArrayList<>();
    private HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private Handler mHandler;
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawer;
    private String[] actionbarTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStatusBarGradiant(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableListView = findViewById(R.id.expandableListView);
        actionbarTitles = getResources().getStringArray(R.array.actionbartitle);

        setSupportActionBar(toolbar);

        mHandler = new Handler();


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;

            loadFragments(getHomeFragment(true, CURRENT_TAG, MENU_HOME, 0)); // load home fragment
        }

        prepareMenuData();
        populateExpandableList();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /**
     * This method create navigation menu items
     */

    private void prepareMenuData() {


        String[] menulist = getResources().getStringArray(R.array.navigationMenus);
//        menuIconlist = getResources().getStringArray(R.array.nav_menu_header_icons_names);
//        settingIconlist = getResources().getStringArray(R.array.nav_menu_setting_icon_names);
        ArrayList<String> headerTagList = Const.getHeaderMenuTags();
        ArrayList<String> childTagList = Const.getChildMenuTags();

        String[] settingMenu = getResources().getStringArray(R.array.settingsMenu);

        for (int i = 0; i < menulist.length; i++) {
            MenuModel menuModel;


            if (!headerTagList.get(i).equals(Const.MENU_SETTINGS)) {
                menuModel = new MenuModel(menulist[i], "", true, false, true, headerTagList.get(i)); //Menu of Android Tutorial. No sub menus

            } else {
                menuModel = new MenuModel(menulist[i], "", true, true, true, headerTagList.get(i)); //Menu of Android Tutorial. No sub menus


            }

//            }

            headerList.add(menuModel);
            if (!menuModel.hasChildren) {
                childList.put(menuModel, null);
            } else {

                List<MenuModel> childModelsList = new ArrayList<>();
                for (int j = 0; j < settingMenu.length; j++) {
                    MenuModel childModel = new MenuModel(settingMenu[j], "", false, false, true, childTagList.get(j));
                    childModelsList.add(childModel);
                }


                if (menuModel.hasChildren) {

                    childList.put(menuModel, childModelsList);
                }
            }

        }


    }


    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {

                        loadFragments(getHomeFragment(true, headerList.get(groupPosition).menuName, headerList.get(groupPosition).getMenuTag(), 0));
                        onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    loadFragments(getHomeFragment(false, model.menuName, model.getMenuTag(), 0));
                    onBackPressed();

                }

                return false;
            }
        });
    }

    private void loadFragments(final Fragment mfragment) {

        // set toolbar title
        setToolbarTitle();

        if (mfragment == null) {
            return;
        }
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();


            return;
        }
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = mfragment;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.main_container, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * This method  return approprite fragment instance as per user clicked in navigation menu
     *
     * @param isGroup     if user click on group item its true else false
     * @param menuName    To pass selected menu name to fragment
     * @param pregnancyId
     * @return selected navigation menu item fragment
     */
    private Fragment getHomeFragment(boolean isGroup, String menuName, String MenuTag, int pregnancyId) {

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString("title", menuName);
        bundle.putInt("pregnancyId", pregnancyId);

        if (isGroup) {
            switch (MenuTag) {

                case MENU_HOME:
                    fragment = new LoadFragment();
//                    setToolbarTransparent(false);
                    navItemIndex = 0;
                    CURRENT_TAG = menuName;
                    break;


                case MENU_LOGOUT:

                    logOut();
                    break;


            }

        } else {
            switch (MenuTag) {


                case MENU_ABOUT_US:

                    navItemIndex = 1;


                    break;
                case MENU_DONATE:

//                    setToolbarTransparent(false);
                    navItemIndex = 2;


                    break;
                case MENU_CONTACT_US:
                    navItemIndex = 3;

                    break;
                case MENU_TERMS_CONDITION:

                    navItemIndex = 4;



                    break;
                case MENU_PRIVACY_POLICY:


                    navItemIndex = 5;


                    break;
                case MENU_SHARE_APP:
                    navItemIndex = 6;
                    shareApplication();


                    break;
                case MENU_FOLLOW_US:
                    navItemIndex = 7;
                    //showBottomSheetFollowUsDialog();

                    break;
                case MENU_RATE_APP:
                    navItemIndex = 8;
                    rateApplication();

                    break;


            }


        }
        if (fragment != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    private void logOut() {

        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout from application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
//            super.onBackPressed();
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


    }
    /*
     * Share application using installed application
     */

    private void shareApplication() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    /**
     * Rate application on play store
     */
    private void rateApplication() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(actionbarTitles[navItemIndex]);
    }
}
