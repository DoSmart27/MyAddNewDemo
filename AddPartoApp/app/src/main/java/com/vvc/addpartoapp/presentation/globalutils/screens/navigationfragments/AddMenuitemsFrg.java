package com.vvc.addpartoapp.presentation.globalutils.screens.navigationfragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomEditText;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextView;
import com.vvc.addpartoapp.presentation.globalutils.singleton.SingletonClass;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddMenuitemsFrg extends Fragment {

    private CustomEditText item_code,item_name,category,price,max_qty;
    private String[] status_list = {"Select status","Active", "Inactive"};
    private String[] type_list = {"Select Type","Veg", "Non-Veg"};

    private Spinner spinner_status,spinner_item_type;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_add_menuitems_frg, container, false);

        View view = inflater.inflate(R.layout.fragment_add_menuitems_frg, container, false);

        initViews(view);
        actionEvents(view);
        return view;
    }


    private void initViews(View view) {
        item_code=(CustomEditText)view.findViewById(R.id.item_code);
        item_name=(CustomEditText)view.findViewById(R.id.item_name);
        category=(CustomEditText)view.findViewById(R.id.category);
        price=(CustomEditText)view.findViewById(R.id.price);
        max_qty=(CustomEditText)view.findViewById(R.id.max_qty);
        spinner_item_type=(Spinner)view.findViewById(R.id.spinner_item_type);
        spinner_status=(Spinner)view.findViewById(R.id.spinner_status);

        ArrayAdapter<String> status_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, status_list);
        status_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(status_adapter);

        ArrayAdapter<String> type_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, type_list);
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item_type.setAdapter(type_adapter);
    }



    private void actionEvents(View view) {

        ((CustomTextView)view.findViewById(R.id.text_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMandatoryFields()) {
                    addMenuItems();
                }
            }
        });
    }

    private boolean checkMandatoryFields() {
        if (!GlobalMethods.isNull(item_code.getText().toString())) {
            GlobalMethods.setEditextError((Activity) getContext(), getString(R.string.this_field_required), item_code);

            return false;
        } else if (!GlobalMethods.isNull(item_name.getText().toString())) {
            GlobalMethods.setEditextError((Activity) getContext(), getString(R.string.this_field_required), item_name);
            return false;
        }
        else if (spinner_item_type.getSelectedItemPosition()==0) {
            GlobalMethods.showNormalToast(getActivity(), getString(R.string.please_select_type), 0);
            return false;
        }
        else if (!GlobalMethods.isNull(category.getText().toString())) {
            GlobalMethods.setEditextError((Activity) getContext(), getString(R.string.this_field_required), category);
            return false;
        }
        else if (!GlobalMethods.isNull(price.getText().toString())) {
            GlobalMethods.setEditextError((Activity) getContext(), getString(R.string.this_field_required), price);
            return false;
        }
        else if (!GlobalMethods.isNull(max_qty.getText().toString())) {
            GlobalMethods.setEditextError((Activity) getContext(), getString(R.string.this_field_required), max_qty);
            return false;
        }




        else if (spinner_status.getSelectedItemPosition()==0) {
            GlobalMethods.showNormalToast(getActivity(), getString(R.string.please_select_status), 0);
            return false;
        }

        else {
            return true;
        }
    }


    private void addMenuItems() {


        try {

            if (GlobalMethods.isOnline(getActivity())) {

                progressDialog = GlobalMethods.showProgress(getActivity());

                JSONObject jsonBody = new JSONObject();

               // jsonBody.put("restID",);
                jsonBody.put("itemCode",item_code.getText().toString());
                jsonBody.put("itemName",item_name.getText().toString());
                jsonBody.put("itemType",spinner_item_type.getSelectedItem().toString());
                jsonBody.put("category",category.getText().toString());
                jsonBody.put("cuisine",category.getText().toString());
                jsonBody.put("price",price.getText().toString());
                jsonBody.put("maxQty",max_qty.getText().toString());

                if (spinner_status.getSelectedItem().toString().equalsIgnoreCase("Active")){
                    jsonBody.put("status",1);
                }else {
                    jsonBody.put("status",0);
                }




                Log.e("generate  req", jsonBody.toString());
                String actual_url = "http://yourfood.in/services/vendors/addNewItem";

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
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 500, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsonObjectRequest);
            } else {
                SingletonClass.getAlertdialog(getActivity(), getResources().getString(R.string.error_intenet_unavailable), getResources().getString(R.string.retry), new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                addMenuItems();
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
}
