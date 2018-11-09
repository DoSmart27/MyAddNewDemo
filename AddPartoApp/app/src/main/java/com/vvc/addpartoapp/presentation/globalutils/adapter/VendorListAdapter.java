package com.vvc.addpartoapp.presentation.globalutils.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.constants.Constants;
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomEditText;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextView;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextViewLight;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextViewSemiBold;
import com.vvc.addpartoapp.presentation.globalutils.singleton.SingletonClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VendorListAdapter extends RecyclerView.Adapter<VendorListAdapter.ViewHolder> {

    private JSONArray vendordsData;
    private Context context;


    CustomTextView text_address,text_lattitude,text_longitude,text_submit;
    CustomEditText text_restaurant_id,restaurant_name,text_mobile,text_email,text_password,text_locality,text_sub_locality,text_admin_area,text_sub_admin_area,
            text_country,text_pincode,text_cuisine,text_status;
    Spinner spinner_status;
    private String[] status_list = {"Active", "Inactive"};
    ProgressDialog progressDialog;

    public VendorListAdapter(Context context,JSONArray vendordsData) {
        this.vendordsData = vendordsData;

        this.context = context;

    }


    @Override
    public VendorListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vendor_item_details_layout, viewGroup, false);

        return new VendorListAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final VendorListAdapter.ViewHolder holder, final int i) {

        if (vendordsData!=null){

            try {
                if (GlobalMethods.isNull(vendordsData.getJSONObject(i).getString("restName"))){
                    holder.restaurant_name.setText(vendordsData.getJSONObject(i).getString("restName"));
                }
                if (GlobalMethods.isNull(vendordsData.getJSONObject(i).getString("restID"))){
                    holder.restaurant_id.setText(vendordsData.getJSONObject(i).getString("restID"));
                }
                if (GlobalMethods.isNull(vendordsData.getJSONObject(i).getString("address"))){
                    holder.restaurant_address.setText(vendordsData.getJSONObject(i).getString("address"));
                }
                if (GlobalMethods.isNull(vendordsData.getJSONObject(i).getString("mobileNo"))){
                    holder.contact.setText(vendordsData.getJSONObject(i).getString("mobileNo"));
                }
                /*if (GlobalMethods.isNull(vendordsData.getJSONObject(i).getString("rating"))) {
                    if (vendordsData.getJSONObject(i).getInt("rating") != 0) {
                        holder.rating.setRating(Float.parseFloat(vendordsData.getJSONObject(i).getString("rating")));

                    } else {
                        holder.rating.setVisibility(View.GONE);
                    }
                }*/

                holder.img_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            editVendor(vendordsData.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                holder.img_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            deleteVendor(vendordsData.getJSONObject(i),vendordsData.getJSONObject(i).getString("restID"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    private void deleteVendor(JSONObject i, String restID) {

        Log.e("i",i+"");
        Log.e("rest",restID);
        try {

            if (GlobalMethods.isOnline(context)) {

                progressDialog = GlobalMethods.showProgress(context);

                JSONObject jsonBody = new JSONObject();
                //jsonBody.put("restID",restID);

                String actual_url = "http://yourfood.in/services/vendors/deleteVendor";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, actual_url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                GlobalMethods.hideProgress(progressDialog);

                                //Log.e("response",jsonObject.toString());

                              //  updateVendorList();
                                try {
                                    if (jsonObject.has("status") && GlobalMethods.isNull(jsonObject.getString("status")) && jsonObject.getString("status").equalsIgnoreCase("1")) {

                                        if (jsonObject.has("msg") && GlobalMethods.isNull(jsonObject.getString("msg"))) {

                                            GlobalMethods.showNormalToast((Activity) context, jsonObject.getString("msg"), 0);
                                        }
                                    } else if (jsonObject.has("msg") && GlobalMethods.isNull(jsonObject.getString("msg"))) {

                                        GlobalMethods.showNormalToast((Activity) context, jsonObject.getString("msg"), 0);

                                    }
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
                RequestQueue requestQueue = Volley.newRequestQueue(context);

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 500, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsonObjectRequest);

            } else {
                SingletonClass.getAlertdialog(context, context.getString(R.string.error_intenet_unavailable), context.getString(R.string.retry), new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateVendor();
                            }
                        }, context.getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }, false).create().show();
            }



        }catch (Exception e){

        }
    }

    private void editVendor(JSONObject jsonObject) {

        try {
//            Log.e("restName", jsonObject.getString("restName"));
//            Log.e("restID", jsonObject.getString("restID"));

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.edit_vendor_layout);
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.setCanceledOnTouchOutside(false);

            text_restaurant_id=(CustomEditText)dialog.findViewById(R.id.text_restaurant_id);
            restaurant_name=(CustomEditText)dialog.findViewById(R.id.restaurant_name);
            text_mobile=(CustomEditText)dialog.findViewById(R.id.text_mobile);
            text_email=(CustomEditText)dialog.findViewById(R.id.text_email);
            text_password=(CustomEditText)dialog.findViewById(R.id.text_password);
            text_cuisine=(CustomEditText) dialog.findViewById(R.id.text_cuisine);
            text_address=(CustomTextView)dialog.findViewById(R.id.text_address);
            text_longitude=(CustomTextView)dialog.findViewById(R.id.text_longitude);
            text_lattitude=(CustomTextView)dialog.findViewById(R.id.text_lattitude);
            text_locality=(CustomEditText)dialog.findViewById(R.id.text_locality);
            text_sub_locality=(CustomEditText)dialog.findViewById(R.id.text_sub_locality);
            text_admin_area=(CustomEditText)dialog.findViewById(R.id.text_admin_area);
            text_sub_admin_area=(CustomEditText)dialog.findViewById(R.id.text_sub_admin_area);
            text_country=(CustomEditText)dialog.findViewById(R.id.text_country);
            text_pincode=(CustomEditText)dialog.findViewById(R.id.text_pincode);
           // spinner_status=(Spinner)dialog.findViewById(R.id.spinner_status);
            text_status=(CustomEditText) dialog.findViewById(R.id.text_status);

            text_restaurant_id.setText(jsonObject.getString("restID"));
            restaurant_name.setText(jsonObject.getString("restName"));
            text_mobile.setText(jsonObject.getString("mobileNo"));
            text_email.setText(jsonObject.getString("email"));
            text_cuisine.setText(jsonObject.getString("cuisines"));
            text_address.setText(jsonObject.getString("address"));
            text_longitude.setText(jsonObject.getString("lang"));
            text_lattitude.setText(jsonObject.getString("lat"));
            text_locality.setText(jsonObject.getString("locality"));
            text_sub_locality.setText(jsonObject.getString("subLocality"));
            text_admin_area.setText(jsonObject.getString("adminArea"));
            text_sub_admin_area.setText(jsonObject.getString("subAdminArea"));
            text_country.setText(jsonObject.getString("country"));
            text_pincode.setText(jsonObject.getString("pincode"));

            if (jsonObject.getString("status").equalsIgnoreCase("1")){
                text_status.setText("Active");
            }else {
                text_status.setText("Inactive");
            }

            dialog.show();

            ((ImageView)dialog.findViewById(R.id.img_cancel_alert)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            ((CustomTextView)dialog.findViewById(R.id.update_vendor)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkMandatoryFields()) {
                        //updateVendor();
                    }
                }
            });

        }catch (Exception e){

            Log.e("",e.toString());
        }
    }


    @SuppressLint("NewApi")

    @Override
    public int getItemCount() {
        return vendordsData.length();
    }

    private void showVendorInfoData(JSONArray vendordsData) {
        if (vendordsData != null  && vendordsData.length() > 0) {
            this.showVendorInfoData(vendordsData);
        }

        notifyDataSetChanged();
    }

    /*public void setBroadcastData(JSONArray sample_types, ArrayList<String> sampleTypeResults) {
        this.vendordsData = sample_types;
        this.vendordsData = sampleTypeResults;
        notifyDataSetChanged();
    }*/


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private CustomTextViewSemiBold restaurant_name;
        private CustomTextViewLight restaurant_id;
        private CustomTextView restaurant_address,contact;
        private AppCompatRatingBar rating;
        private LinearLayout parent_layout;
        private ImageView img_edit,img_delete;
        public ViewHolder(View view) {
            super(view);

            restaurant_name = (CustomTextViewSemiBold) view.findViewById(R.id.restaurant_name);
            restaurant_id = (CustomTextViewLight) view.findViewById(R.id.restaurant_id);
            restaurant_address=(CustomTextView)view.findViewById(R.id.restaurant_address);
            contact=(CustomTextView)view.findViewById(R.id.contact);
            rating=(AppCompatRatingBar)view.findViewById(R.id.rating);
            img_edit=(ImageView)view.findViewById(R.id.img_edit);
            img_delete=(ImageView)view.findViewById(R.id.img_delete);
            view.setOnClickListener(this);


        }


        @Override
        public void onClick(final View view) {

        }


    }

    private boolean checkMandatoryFields() {
        if (!GlobalMethods.isNull(text_restaurant_id.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_restaurant_id);

            return false;
        } else if (!GlobalMethods.isNull(restaurant_name.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), restaurant_name);

            return false;
        }

        else if (!GlobalMethods.isNull(text_mobile.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_mobile);
            return false;
        }
        else if (text_mobile.getText().toString().length()!=10) {
            GlobalMethods.setEditextError(context, context.getString(R.string.please_enter_valid_phone_number), text_mobile);
            return false;
        }

        else if (!GlobalMethods.isNull(text_email.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_email);

            return false;
        } else if (!text_email.getText().toString().matches(Constants.EMAIL_PATTERN)) {
            GlobalMethods.setEditextError(context, context.getString(R.string.invalidate_email), text_email);

            return false;
        }/*
        else if (!GlobalMethods.isNull(text_password.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_password);
            return false;
        }*/

        else if (!GlobalMethods.isNull(text_cuisine.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_cuisine);
            return false;
        }

        else if (!GlobalMethods.isNull(text_address.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_address);
            return false;
        } else if (!GlobalMethods.isNull(text_lattitude.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_lattitude);
            return false;
        } else if (!GlobalMethods.isNull(text_longitude.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required),text_longitude);
            return false;
        } else if (!GlobalMethods.isNull(text_locality.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required),text_locality);
            return false;
        } else if (!GlobalMethods.isNull(text_sub_locality.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_sub_locality);
            return false;
        }
        else if (!GlobalMethods.isNull(text_admin_area.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_admin_area);
            return false;
        }
        else if (!GlobalMethods.isNull(text_sub_admin_area.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_sub_admin_area);
            return false;
        }

        else if (!GlobalMethods.isNull(text_country.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_country);
            return false;
        }
        else if (!GlobalMethods.isNull(text_pincode.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_pincode);
            return false;
        }

        else if (!GlobalMethods.isNull(text_status.getText().toString())) {
            GlobalMethods.setEditextError(context, context.getString(R.string.this_field_required), text_pincode);
            return false;
        }

        /*else if (spinner_status.getSelectedItemPosition()==0) {
            GlobalMethods.showNormalToast(getActivity(), getString(R.string.please_select_status), 0);
            return false;
        }*/
        else {
            return true;
        }

    }


    private void updateVendor() {

        try {

            if (GlobalMethods.isOnline(context)) {

                progressDialog = GlobalMethods.showProgress(context);

                JSONObject jsonBody = new JSONObject();

                jsonBody.put("restID",text_restaurant_id.getText().toString());
                jsonBody.put("restName",restaurant_name.getText().toString());
                jsonBody.put("mobileNo",text_mobile.getText().toString());
                jsonBody.put("email",text_email.getText().toString());
                jsonBody.put("password",text_password.getText().toString());

                if (spinner_status.getSelectedItem().toString().equalsIgnoreCase("Active")){
                    jsonBody.put("status",1);
                }else {
                    jsonBody.put("status",0);
                }
                jsonBody.put("address",text_address.getText().toString());
                jsonBody.put("lat",text_lattitude.getText().toString());
                jsonBody.put("lang",text_longitude.getText().toString());
                jsonBody.put("pincode",text_pincode.getText().toString());
                jsonBody.put("city",text_locality.getText().toString());
                jsonBody.put("locality",text_locality.getText().toString());
                jsonBody.put("subLocality",text_sub_locality.getText().toString());
                jsonBody.put("adminArea",text_admin_area.getText().toString());
                jsonBody.put("subAdminArea",text_sub_admin_area.getText().toString());
                jsonBody.put("country",text_country.getText().toString());
                jsonBody.put("cuisines",text_cuisine.getText().toString());
                jsonBody.put("offers","");
                jsonBody.put("imageUrl","");
                jsonBody.put("deliveryTime","");
                jsonBody.put("rating","5");
                jsonBody.put("review","");



                Log.e("generate  req", jsonBody.toString());
               String actual_url = " http://yourfood.in/services/vendors/updateVendor/";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, actual_url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                GlobalMethods.hideProgress(progressDialog);

                                //Log.e("response",jsonObject.toString());

                                try {
                                    if (jsonObject.has("status") && GlobalMethods.isNull(jsonObject.getString("status")) && jsonObject.getString("status").equalsIgnoreCase("1")) {

                                        if (jsonObject.has("msg") && GlobalMethods.isNull(jsonObject.getString("msg"))) {

                                            GlobalMethods.showNormalToast((Activity) context, jsonObject.getString("msg"), 0);
                                        }
                                    } else if (jsonObject.has("msg") && GlobalMethods.isNull(jsonObject.getString("msg"))) {

                                        GlobalMethods.showNormalToast((Activity) context, jsonObject.getString("msg"), 0);

                                    }
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
                RequestQueue requestQueue = Volley.newRequestQueue(context);

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 500, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsonObjectRequest);
            } else {
                SingletonClass.getAlertdialog(context, context.getString(R.string.error_intenet_unavailable), context.getString(R.string.retry), new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateVendor();
                            }
                        }, context.getString(R.string.dismiss), new DialogInterface.OnClickListener() {
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
}


