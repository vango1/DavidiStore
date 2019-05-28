package com.example.hodael.davidimarket;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.remote.InteractionResponse;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Until;

import java.util.Calendar;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ui {

UiDevice x ;
    @Rule
    public ActivityTestRule<firstScreen> first =  new ActivityTestRule(firstScreen.class);

    @Before
    public void setUpCOMP(){


        System.out.println("yeah");
    }



    @Test
    public void testBackKey(){
       x.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void testUI ()throws RemoteException{
        UiDevice U = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        if (U.isScreenOn()){
            U.setOrientationRight();


            U.openNotification();

            U.openQuickSettings();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            U.freezeRotation();

            U.pressHome();

        }

    }




}

