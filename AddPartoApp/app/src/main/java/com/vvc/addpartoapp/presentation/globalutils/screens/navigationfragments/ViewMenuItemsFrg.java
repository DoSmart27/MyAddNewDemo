package com.vvc.addpartoapp.presentation.globalutils.screens.navigationfragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.adapter.MenuListAdapter;
import com.vvc.addpartoapp.presentation.globalutils.adapter.VendorListAdapter;
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;
import com.vvc.addpartoapp.presentation.globalutils.singleton.SingletonClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ViewMenuItemsFrg extends Fragment {

    RecyclerView menu_list;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_menu_items_frg, container, false);

        initGUI(view);

        callMenuItemApi();
        return view;
    }


    private void initGUI(View view) {

        menu_list=(RecyclerView)view.findViewById(R.id.menu_list);
    }



    private void callMenuItemApi() {
//http://yourfood.in/services/vendors/getItemsByVendorID/NEWR0022
        try {

            if (GlobalMethods.isOnline(getActivity())) {
                progressDialog = GlobalMethods.showProgress(getActivity());

                JSONObject jsonObject = new JSONObject();

                Volley.newRequestQueue(getActivity()).add(
                        new JsonRequest<JSONArray>(Request.Method.GET, "http://yourfood.in/services/vendors/getItemsByVendorID/NEWR0022", jsonObject.toString(),
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        //  Log.e("JSON ARRAY----",response.toString());
                                        if (response != null && response.length() > 0) {
                                            getMenuList(response);

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
            } else {
                SingletonClass.getAlertdialog(getActivity(), getResources().getString(R.string.error_intenet_unavailable), getResources().getString(R.string.retry), new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                callMenuItemApi();
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

    private void getMenuList(JSONArray jsonArray) {

        GlobalMethods.hideProgress(progressDialog);

        try {

            MenuListAdapter menuListAdapter = new MenuListAdapter(getContext(), jsonArray);

            // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

            menu_list.setLayoutManager(horizontalLayoutManagaer);
            menu_list.setAdapter(menuListAdapter);
            menu_list.setNestedScrollingEnabled(false);


        }catch (Exception e){

        }
    }

}
