package com.example.vishalhalani.expandablenavigationdrawermenu;

import java.util.ArrayList;

public class Const {
    // ***************** Menu item Constants *********************

    public static final String MENU_HOME = "Home";

    public static final String MENU_SETTINGS = "Settings";

    public static final String MENU_ABOUT_US = "About Us";
    public static final String MENU_DONATE = "Donate";
    public static final String MENU_CONTACT_US = "Contact Us";
    public static final String MENU_TERMS_CONDITION = "Terms & Conditions";
    public static final String MENU_PRIVACY_POLICY = "Privacy Policy";
    public static final String MENU_SHARE_APP = "Share This App";
    public static final String MENU_FOLLOW_US = "Follow Us On";
    public static final String MENU_RATE_APP = "Rate This App";
    public static final String MENU_LOGOUT = "Logout";


    public static ArrayList<String> getHeaderMenuTags() {
        ArrayList<String> tagList = new ArrayList<>();
        tagList.add(MENU_HOME);

        tagList.add(MENU_SETTINGS);
        tagList.add(MENU_LOGOUT);

        return tagList;
    }

    public static ArrayList<String> getChildMenuTags() {
        ArrayList<String> tagList = new ArrayList<>();
        tagList.add(MENU_ABOUT_US);
        tagList.add(MENU_DONATE);
        tagList.add(MENU_CONTACT_US);
        tagList.add(MENU_TERMS_CONDITION);
        tagList.add(MENU_PRIVACY_POLICY);
        tagList.add(MENU_SHARE_APP);
        tagList.add(MENU_FOLLOW_US);
        tagList.add(MENU_RATE_APP);


        return tagList;
    }
}
