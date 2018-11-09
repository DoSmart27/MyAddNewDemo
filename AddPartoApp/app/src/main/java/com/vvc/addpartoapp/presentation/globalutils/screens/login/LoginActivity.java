package com.vvc.addpartoapp.presentation.globalutils.screens.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomEditText;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextView;
import com.vvc.addpartoapp.presentation.globalutils.screens.BaseActivity;
import com.vvc.addpartoapp.presentation.globalutils.screens.HomeFragment;
import com.vvc.addpartoapp.presentation.globalutils.screens.activity.HomeActivity;
import com.vvc.addpartoapp.presentation.globalutils.singleton.SingletonClass;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {


    CustomEditText user_id,edit_password;
    ProgressDialog progressDialog;
    @Override
    public int setLayoutResuourse() {
        return R.layout.activity_login;
    }

    @Override
    public void initGUI() {

        initViews();
        actionEvents();
    }

    private void initViews() {
        user_id=(CustomEditText)findViewById(R.id.user_id);
        edit_password=(CustomEditText)findViewById(R.id.edit_password);
    }

    private void actionEvents() {


        ((ImageView)findViewById(R.id.show_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView) findViewById(R.id.show_password)).setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.hide_password)).setVisibility(View.GONE);

                edit_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            }
        });


        ((ImageView)findViewById(R.id.hide_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ImageView) findViewById(R.id.hide_password)).setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.show_password)).setVisibility(View.GONE);
                edit_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);


            }
        });

        ((CustomTextView)findViewById(R.id.text_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMandatoryFields()) {
                    //vendorLogin();

                    callHomeActivity();
                }
            }
        });
    }



    @Override
    public void initData() {

    }



    private boolean checkMandatoryFields() {
        if (!GlobalMethods.isNull(user_id.getText().toString())) {
            GlobalMethods.setEditextError(LoginActivity.this, getString(R.string.this_field_required), user_id);

            return false;
        } else if (!GlobalMethods.isNull(edit_password.getText().toString())) {
            GlobalMethods.setEditextError(LoginActivity.this, getString(R.string.this_field_required), edit_password);

            return false;
        }
        else {
            return true;
        }
    }



    private void vendorLogin() {
        try {

            if (GlobalMethods.isOnline(LoginActivity.this)) {

                progressDialog = GlobalMethods.showProgress(LoginActivity.this);

                JSONObject jsonBody = new JSONObject();

                jsonBody.put("userName",user_id.getText().toString());
                jsonBody.put("password",edit_password.getText().toString());




                Log.e("generate  req", jsonBody.toString());
                String actual_url = " http://yourfood.in/services/vendors/vendorLogin\n";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, actual_url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                GlobalMethods.hideProgress(progressDialog);

                                Log.e("response",jsonObject.toString());
                                try {



                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(" exception is", "" + e);
                                    GlobalMethods.hideProgress(progressDialog);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(" resp", "" + error.toString());
                        GlobalMethods.hideProgress(progressDialog);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        // Log.e("Headers", "" + headers);
                        return headers;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 500, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsonObjectRequest);
            } else {
                SingletonClass.getAlertdialog(LoginActivity.this, getResources().getString(R.string.error_intenet_unavailable), getResources().getString(R.string.retry), new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                vendorLogin();
                            }
                        }, getResources().getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }, false).create().show();
            }
        } catch (Exception e) {
            Log.e("add cart exceptio", "" + e);
            e.printStackTrace();
        }
    }
    private void callHomeActivity() {

        GlobalMethods.callForWordActivity(LoginActivity.this, HomeActivity.class, null, false, true);

    }
}
