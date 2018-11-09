package com.vvc.addpartoapp.presentation.globalutils.screens;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 15-06-2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for device font size changes but app size not reflacts
        adjustFontScale(getResources().getConfiguration());
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(setLayoutResuourse());

        initGUI();
        initData();
    }

    public void adjustFontScale(Configuration configuration) {
        if (configuration.fontScale > 1.00) {
            configuration.fontScale = (float) 1.00;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
        }
    }



    public abstract int setLayoutResuourse();

    public abstract void initGUI();

    public abstract void initData();
}
