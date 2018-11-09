package com.vvc.addpartoapp.presentation.globalutils.screens.splashscreen;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;
import com.vvc.addpartoapp.presentation.globalutils.screens.BaseActivity;
import com.vvc.addpartoapp.presentation.globalutils.screens.activity.HomeActivity;
import com.vvc.addpartoapp.presentation.globalutils.screens.login.LoginActivity;

public class SplashScreenAct extends BaseActivity {
    public static final int MULTIPLE_PERMISSIONS = 1245;
    private final int SPLASH_TIME_OUT = 5000;

    @Override
    public int setLayoutResuourse() {
        return R.layout.activity_splash_screen;
    }

    @Override
    public void initGUI() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void initData() {
        callNextActivity();
    }



    private void callNextActivity() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                GlobalMethods.callForWordActivity(SplashScreenAct.this,HomeActivity.class,null,true,true);
            }
        }, SPLASH_TIME_OUT);
    }
}
