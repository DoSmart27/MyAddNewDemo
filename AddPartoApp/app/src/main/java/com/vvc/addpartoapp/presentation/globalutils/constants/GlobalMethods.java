package com.vvc.addpartoapp.presentation.globalutils.constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;


import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.singleton.SingletonClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 11-06-2018.
 */

public class GlobalMethods {

    public static final int VOLLEY_TIME_OUR_IN_MILLISECONDS =60 * 1000 ;


    public static boolean isOnline(Context context) throws Exception {
        try {
            return SingletonClass.getInstance().isDeviceOnline(context);
        } catch (Exception e) {
            throw e;
        }

    }

    public static boolean isNull(String data) {

        boolean isnull = false;
        if (data != null) {
            if (!data.equals("") && !data.equals("null") && data != null
                    && !data.equals("-1") && !data.equalsIgnoreCase("NULL")) {
                isnull = true;
            }
        } else {
            isnull = false;
        }

        return isnull;
    }
    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static int getCuurentYear() {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        return currentYear;

    }

    public static int  getCuurentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        return month+1;
    }

    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static String getFormattedDate(String dateString) {

//        2018-04-30 20:27:56
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateString);
            System.out.println(date);


            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            //2nd of march 2015
            int day = cal.get(Calendar.DATE);

            if (!((day > 10) && (day < 19)))
                switch (day % 10) {
                    case 1:
                        return new SimpleDateFormat("d'st' MMMM yyyy").format(date);
                    case 2:
                        return new SimpleDateFormat("d'nd' MMMM yyyy").format(date);
                    case 3:
                        return new SimpleDateFormat("d'rd' MMMM yyyy").format(date);
                    default:
                        return new SimpleDateFormat("d'th' MMMM yyyy").format(date);
                }
            return new SimpleDateFormat("d'th' MMMM yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    public static void callForWordActivity(Context context, Class forwardClass, Bundle bundle, boolean isActivityFinish, boolean isForwardAnimation) {
        Intent myIntent = new Intent(context, forwardClass);
        if (bundle != null) {
            myIntent.putExtras(bundle);
        }
        context.startActivity(myIntent);
        if (isForwardAnimation) {
            ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                    R.anim.slide_out_left);
        } else {
            ((Activity) context).overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_right_in);
        }
        if (isActivityFinish)
            ((Activity) context).finish();
    }


    public static void callForWordActivityFromAdapter(Context context, Class forwardClass, Bundle bundle, boolean isActivityFinish, boolean isForwardAnimation) {
        Intent myIntent = new Intent(context, forwardClass);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            myIntent.putExtras(bundle);
        }
        context.startActivity(myIntent);
        if (isForwardAnimation) {
            ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                    R.anim.slide_out_left);
        } else {
            ((Activity) context).overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_right_in);
        }
        if (isActivityFinish)
            ((Activity) context).finish();
    }


    public static void callForWordActivityWithClearTop(Context context, Class forwardClass, Bundle bundle, boolean isActivityFinish, boolean isForwardAnimation) {
        Intent myIntent = new Intent(context, forwardClass);
        if (bundle != null) {
            myIntent.putExtras(bundle);
        }
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(myIntent);
        if (isForwardAnimation) {
            ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                    R.anim.slide_out_left);
        } else {
            ((Activity) context).overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_right_in);
        }
        if (isActivityFinish)
            ((Activity) context).finish();
    }

    public static void callBackWordActivity(Context context, Class forwardClass, Bundle bundle, boolean isActivityFinish, boolean isForwardAnimation) {
        Intent myIntent = new Intent(context, forwardClass);
        if (bundle != null) {
            myIntent.putExtras(bundle);
        }
        context.startActivity(myIntent);
        if (isForwardAnimation) {
            ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                    R.anim.slide_out_left);
        } else {
            ((Activity) context).overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_right_in);
        }
        if (isActivityFinish)
            ((Activity) context).finish();
    }

    public static void callFinishForBackWordActivity(Context context, boolean isForwardAnimation) {
        try {

            ((Activity) context).finish();
            if (isForwardAnimation) {
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_out_left);
            } else {
                ((Activity) context).overridePendingTransition(R.anim.slide_out_right,
                        R.anim.slide_right_in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callForWordActivityForResult(Context context, Class forwardClass, Bundle bundle, int ResultCode, boolean isForwardAnimation) {

        Intent myIntent = new Intent(context, forwardClass);
        if (bundle != null) {
            myIntent.putExtras(bundle);
        }
        ((Activity) context).startActivityForResult(myIntent, ResultCode);
        if (isForwardAnimation) {
            ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                    R.anim.slide_out_left);
        } else {
            ((Activity) context).overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_right_in);
        }

    }

    public static void callBackWordActivityForResult(Context context, Bundle bundle, int ResultCode, boolean isForwardAnimation) {

        Intent myIntent = new Intent();
        if (bundle != null) {
            myIntent.putExtras(bundle);
        }
        ((Activity) context).setResult(Activity.RESULT_OK, myIntent);
        ((Activity) context).finish();
        if (isForwardAnimation) {
            ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                    R.anim.slide_out_left);
        } else {
            ((Activity) context).overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_right_in);
        }


    }
    public static CharSequence setEditextError(Context context, String str, TextView editext) {
        editext.requestFocus();
        editext.setError(str);

        return "";
    }
    public static void showNormalToast(Activity activity, String message, int lengthLong) {
        try {

            try {
                if (lengthLong == 0) {
                    lengthLong = Toast.LENGTH_LONG;
                }
                Toast.makeText(activity, message, lengthLong).show();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {

        }
    }

    public static int getAge(String dateOfBirthString) {
//        year + "-" + (monthOfYear + 1) + "-" + dayOfMonth

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int age = 0;
        try {
            Date dateOfBirth = format.parse(dateOfBirthString);
            Calendar today = Calendar.getInstance();
            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime(dateOfBirth);
            if (birthDate.after(today)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }

            age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

            // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
            if ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                    (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
                age--;

                // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
            } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
                    (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }
        }catch (Exception e){

        }

        return age;
    }
    public static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static String camelText(String action) {
        StringBuilder builder = new StringBuilder(action);
        // Flag to keep track if last visited character is a
        // white space or not
        boolean isLastSpace = true;

        // Iterate String from beginning to end.
        for (int i = 0; i < builder.length(); i++) {
            char ch = builder.charAt(i);

            if (isLastSpace && ch >= 'a' && ch <= 'z') {
                // Character need to be converted to uppercase
                builder.setCharAt(i, (char) (ch + ('A' - 'a')));
                isLastSpace = false;
            } else if (ch != ' ')
                isLastSpace = false;
            else
                isLastSpace = true;
        }

        return builder.toString();
    }

    public static ProgressDialog showProgress(Context context) {

        ProgressDialog progress = ProgressDialog.show(context, null, null);
//        progress.setMessage(message);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progress.setContentView(R.layout.progress_layout);
        progress.setCancelable(false);
        progress.show();
        return progress;


    }

    public static ProgressDialog showProgressWithCancelable(Context context) {

        ProgressDialog progress = ProgressDialog.show(context, null, null);
//        progress.setMessage(message);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progress.setContentView(R.layout.progress_layout);
        progress.setCancelable(true);
        progress.show();
        return progress;


    }

    public static void hideProgress(ProgressDialog dialog) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static boolean isValidJson(String test) {

        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            return false;
        }
        return true;
    }

    public static boolean isValidJsonArray(String test) {

        try {
            new JSONArray(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...

            return false;
        }
        return true;
    }

}
