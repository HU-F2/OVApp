package com.mobiliteitsfabriek.ovapp;

import com.mobiliteitsfabriek.infrastructure.DotenvConfig;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;

public class OvAppApplication {

    public static void main(String[] args){
        @SuppressWarnings("unused")
        DotenvConfig config = new DotenvConfig();
        OVAppUI.launch(OVAppUI.class, args);
    }

}
