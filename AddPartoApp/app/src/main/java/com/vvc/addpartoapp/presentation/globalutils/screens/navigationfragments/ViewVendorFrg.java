package com.vvc.addpartoapp.presentation.globalutils.screens.navigationfragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.adapter.VendorListAdapter;
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;
import com.vvc.addpartoapp.presentation.globalutils.screens.login.LoginActivity;
import com.vvc.addpartoapp.presentation.globalutils.singleton.SingletonClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ViewVendorFrg extends Fragment {

    private RecyclerView vendors_list;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_view_vendor_frg, container, false);

        View view = inflater.inflate(R.layout.fragment_view_vendor_frg, container, false);

        initViews(view);
        //actionEvents(view);
        callVendorListApi();
        return view;
    }


    private void initViews(View view) {

        vendors_list=(RecyclerView)view.findViewById(R.id.vendors_list);

    }


    private void callVendorListApi() {

        try {

            if (GlobalMethods.isOnline(getActivity())) {
                progressDialog = GlobalMethods.showProgress(getActivity());

                JSONObject jsonObject = new JSONObject();

                /*Volley.newRequestQueue(getActivity()).add(
                        new JsonRequest<JSONArray>(Request.Method.GET, "http://yourfood.in/services/vendors/getAllVendors", jsonObject.toString(),
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        //  Log.e("JSON ARRAY----",response.toString());
                                        if (response != null && response.length() > 0) {
                                            getVendorList(response);

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                //  params.put("param1", "one");
                                // params.put("param2", "two");
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Content-Type", "application/json; charset=utf-8");
                                // params.put("authorization", "Basic Y2FsbGhlYWx0aDplYzhiYzhlMmIxMjBkMTQzZTcyNzRkZTI1MDhmM2Y2Zg==");
                                return params;

                            }

                            @Override
                            protected Response<JSONArray> parseNetworkResponse(
                                    NetworkResponse response) {
                                try {
                                    String jsonString = new String(response.data,
                                            HttpHeaderParser
                                                    .parseCharset(response.headers));
                                    return Response.success(new JSONArray(jsonString),
                                            HttpHeaderParser
                                                    .parseCacheHeaders(response));
                                } catch (UnsupportedEncodingException e) {
                                    GlobalMethods.hideProgress(progressDialog);
                                    return Response.error(new ParseError(e));
                                } catch (JSONException je) {
                                    GlobalMethods.hideProgress(progressDialog);

                                    return Response.error(new ParseError(je));
                                }
                            }
                        });
            }*/
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://yourfood.in/services/vendors/getAllVendors", jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                GlobalMethods.hideProgress(progressDialog);

                                Log.e("response",jsonObject.toString());
                                try {

                                    if (jsonObject.has("count") && GlobalMethods.isNull(jsonObject.getString("count")) && !jsonObject.getString("count").equalsIgnoreCase("0")) {

                                        if (jsonObject.has("data") && jsonObject.getJSONArray("data")!=null) {
                                            getVendorList(jsonObject.getJSONArray("data"));


                                            //GlobalMethods.showNormalToast((Activity) getContext(), jsonObject.getString("msg"), 0);
                                        }
                                    } else if (jsonObject.has("msg") && GlobalMethods.isNull(jsonObject.getString("msg"))) {

                                        GlobalMethods.showNormalToast((Activity) getContext(), jsonObject.getString("msg"), 0);

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
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 500, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsonObjectRequest);
            }
                else {
                SingletonClass.getAlertdialog(getActivity(), getResources().getString(R.string.error_intenet_unavailable), getResources().getString(R.string.retry), new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                callVendorListApi();
                            }
                        }, getResources().getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }, false).create().show();
            }
        }catch (Exception e){

        }

    }

    private void getVendorList(JSONArray jsonArray) {
        GlobalMethods.hideProgress(progressDialog);

        try {
            /*for (int i = 0; i < jsonArray.length(); i++) {
           Log.e("restID", jsonArray.getJSONObject(i).getString("restID"));
            }*/

            VendorListAdapter vendorListAdapter = new VendorListAdapter(getContext(), jsonArray);

            // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

            vendors_list.setLayoutManager(horizontalLayoutManagaer);
            vendors_list.setAdapter(vendorListAdapter);
            vendors_list.setNestedScrollingEnabled(false);


        }catch (Exception e){

        }
    }
}
