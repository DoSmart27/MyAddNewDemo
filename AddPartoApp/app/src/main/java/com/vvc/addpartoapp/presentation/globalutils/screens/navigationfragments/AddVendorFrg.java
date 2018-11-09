package com.vvc.addpartoapp.presentation.globalutils.screens.navigationfragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.constants.Constants;
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomEditText;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextView;
import com.vvc.addpartoapp.presentation.globalutils.singleton.SingletonClass;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class AddVendorFrg extends Fragment {

    CustomTextView text_address,text_lattitude,text_longitude,text_submit,text_cuisine;
    CustomEditText text_restaurant_id,restaurant_name,text_mobile,text_email,text_password,text_locality,text_sub_locality,text_admin_area,text_sub_admin_area,
    text_country,text_pincode;
    Spinner spinner_status;
    private String[] status_list = {"Select status","Active", "Inactive"};
    private String [] cuisine_List={"Ice Cream", "Sweets", "North Indian", "Chinese", "Seafood", "Fast Food", "Andhra", "Biryani", "South Indian", "American", "Cafe",
            "Desserts", "Punjabi", "Pizzas", "Arabian", "Chaat", "Indian","Juices", "Bakery", "Kebabs", "Haleem", "Pasta", "Thalis", "Burger","Rolls", "Mandi","Combo", "Sandwiches"};

    ProgressDialog progressDialog;
    ListView cuisine_listview;

    String valueHolder = "" ;
    SparseBooleanArray sparseBooleanArray ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_vendor_frg, container, false);

        initViews(view);
        actionEvents(view);
        return view;
    }


    private void initViews(View view) {
        text_restaurant_id=(CustomEditText)view.findViewById(R.id.text_restaurant_id);
        text_address=(CustomTextView)view.findViewById(R.id.text_address);
        restaurant_name=(CustomEditText)view.findViewById(R.id.restaurant_name);
        text_mobile=(CustomEditText)view.findViewById(R.id.text_mobile);
        text_email=(CustomEditText)view.findViewById(R.id.text_email);
        text_password=(CustomEditText)view.findViewById(R.id.text_password);
        text_cuisine=(CustomTextView) view.findViewById(R.id.text_cuisine);
        text_longitude=(CustomTextView)view.findViewById(R.id.text_longitude);
        text_lattitude=(CustomTextView)view.findViewById(R.id.text_lattitude);
        text_locality=(CustomEditText)view.findViewById(R.id.text_locality);
        text_sub_locality=(CustomEditText)view.findViewById(R.id.text_sub_locality);
        text_admin_area=(CustomEditText)view.findViewById(R.id.text_admin_area);
        text_sub_admin_area=(CustomEditText)view.findViewById(R.id.text_sub_admin_area);
        text_country=(CustomEditText)view.findViewById(R.id.text_country);
        text_pincode=(CustomEditText)view.findViewById(R.id.text_pincode);
        spinner_status=(Spinner)view.findViewById(R.id.spinner_status);
        text_submit=(CustomTextView)view.findViewById(R.id.text_submit);


        ArrayAdapter<String> status_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, status_list);
        status_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(status_adapter);
       // spinner_status.setSelection(0);
    }



    private void actionEvents(View view) {
        text_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    getActivity().startActivityForResult(builder.build(getActivity()), 100);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        text_cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCuisineList();
            }
        });

        text_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkMandatoryFields()) {
                    addVendor();
                }
                }
        });
    }

    private void showCuisineList() {


        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.cuisine_items_layout);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);

        cuisine_listview=(ListView)dialog.findViewById(R.id.cuisine_list);

        ((ImageView)dialog.findViewById(R.id.img_cancel_alert)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (getActivity(),
//                        android.R.layout.select_dialog_multichoice,
//                        android.R.id.text1, cuisine_List );

        cuisine_listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        cuisine_listview.setItemsCanFocus(false);

        cuisine_listview.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.mytextview, cuisine_List));


        cuisine_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                sparseBooleanArray = cuisine_listview.getCheckedItemPositions();


                int i = 0 ;

                while (i < sparseBooleanArray.size()) {

                    if (sparseBooleanArray.valueAt(i)) {

                        valueHolder += cuisine_List [ sparseBooleanArray.keyAt(i) ] + ",";
                    }

                    i++ ;
                }

                valueHolder = valueHolder.replaceAll("(,)*$", "");

            }
        });

        ((CustomTextView)dialog.findViewById(R.id.add_cuisine)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (valueHolder.equalsIgnoreCase("")){

                    GlobalMethods.showNormalToast((Activity)getContext(),"Please select cuisine",0);
                return;
                }else {
                    text_cuisine.setText(valueHolder.toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();

    }


    private boolean checkMandatoryFields() {
        if (!GlobalMethods.isNull(text_restaurant_id.getText().toString())) {
            GlobalMethods.setEditextError((Activity)getContext(), getString(R.string.this_field_required), text_restaurant_id);

            return false;
        } else if (!GlobalMethods.isNull(restaurant_name.getText().toString())) {
            GlobalMethods.setEditextError((Activity)getContext(), getString(R.string.this_field_required), restaurant_name);

            return false;
        }

        else if (!GlobalMethods.isNull(text_mobile.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_mobile);
            return false;
        }
        else if (text_mobile.getText().toString().length()!=10) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.please_enter_valid_phone_number), text_mobile);
            return false;
        }

        else if (!GlobalMethods.isNull(text_email.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_email);

            return false;
        } else if (!text_email.getText().toString().matches(Constants.EMAIL_PATTERN)) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.invalidate_email), text_email);

            return false;
        }
        else if (!GlobalMethods.isNull(text_password.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_password);
            return false;
        }

        else if (!GlobalMethods.isNull(text_cuisine.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_cuisine);
            return false;
        }

        else if (!GlobalMethods.isNull(text_address.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_address);
            return false;
        } else if (!GlobalMethods.isNull(text_lattitude.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_lattitude);
            return false;
        } else if (!GlobalMethods.isNull(text_longitude.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required),text_longitude);
            return false;
        } else if (!GlobalMethods.isNull(text_locality.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required),text_locality);
            return false;
        } else if (!GlobalMethods.isNull(text_sub_locality.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_sub_locality);
            return false;
        }
        else if (!GlobalMethods.isNull(text_admin_area.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_admin_area);
            return false;
        }
        else if (!GlobalMethods.isNull(text_sub_admin_area.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_sub_admin_area);
            return false;
        }

        else if (!GlobalMethods.isNull(text_country.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_country);
            return false;
        }
        else if (!GlobalMethods.isNull(text_pincode.getText().toString())) {
            GlobalMethods.setEditextError(getActivity(), getString(R.string.this_field_required), text_pincode);
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

    private void addVendor() {

     //   GlobalMethods.showNormalToast(getActivity(),"Sucss",0);


        final Gson gson = new Gson();


        try {

            if (GlobalMethods.isOnline(getActivity())) {

                progressDialog = GlobalMethods.showProgress(getActivity());

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
                String actual_url = "http://yourfood.in/services/vendors/addNewVendor";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, actual_url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                            GlobalMethods.hideProgress(progressDialog);

                            //Log.e("response",jsonObject.toString());

                            try {
                                if (jsonObject.has("status") && GlobalMethods.isNull(jsonObject.getString("status")) && jsonObject.getString("status").equalsIgnoreCase("1")) {

                                    if (jsonObject.has("msg") && GlobalMethods.isNull(jsonObject.getString("msg"))) {

                                        GlobalMethods.showNormalToast((Activity) getContext(), jsonObject.getString("msg"), 0);
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
            } else {
                SingletonClass.getAlertdialog(getActivity(), getResources().getString(R.string.error_intenet_unavailable), getResources().getString(R.string.retry), new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               addVendor();
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



    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == 100) {
            if (resultCode == RESULT_OK) {
                Place selectedPlace = PlacePicker.getPlace(data, getActivity());
                // Do something with the place
                Place place = PlacePicker.getPlace(getActivity(), data);

                // Log.e("address",place.getAddress()+"");
             //   Log.e("latitude", place.getLatLng().latitude + "");
               // Log.e("latitude", place.getLatLng().longitude + "");

                text_lattitude.setText(place.getLatLng().latitude + "");
                text_longitude.setText(place.getLatLng().longitude + "");
             String  searchandselect = getCompleteAddressString(place.getLatLng().latitude, place.getLatLng().longitude);
                text_address.setText(searchandselect);
                //address_lane2.setText(searchandselect);
                getAddressDetails(place);

            }
        }

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                if (GlobalMethods.isNull(returnedAddress.getAdminArea())){
                    text_admin_area.setText(returnedAddress.getAdminArea());
                }


                if (GlobalMethods.isNull(returnedAddress.getSubAdminArea())){
                    text_sub_admin_area.setText(returnedAddress.getSubAdminArea());
                }

                if (GlobalMethods.isNull(returnedAddress.getLocality())){
                    text_locality.setText(returnedAddress.getLocality());
                }

                if (GlobalMethods.isNull(returnedAddress.getSubLocality())){
                    text_sub_locality.setText(returnedAddress.getSubLocality());
                }

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("dsf", strReturnedAddress.toString());
            } else {
                Log.w("1232", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("sfds", "Canont get Address!");
        }
        return strAdd;
    }


    public void getAddressDetails(Place place) {
        String state = "";
        String city = "";
        String stAddress1 = "";
        String stAddress2 = "";

        if (place.getAddress() != null) {
            String[] addressSlice = place.getAddress().toString().split(", ");
            String country = addressSlice[addressSlice.length - 1];
            //  Log.e("country",country);
            if (GlobalMethods.isNull(country)) {
               text_country.setText(country);
            }
           // countryVal = addressSlice[addressSlice.length - 1];
            if (addressSlice.length > 1) {
                String[] stateAndPostalCode = addressSlice[addressSlice.length - 2].split(" ");
                if (stateAndPostalCode.length > 1) {
                    String postalCode = stateAndPostalCode[stateAndPostalCode.length - 1];
                    // Log.e("postalCode",postalCode);

                  //  zipcodeVal = stateAndPostalCode[stateAndPostalCode.length - 1];
                    if (GlobalMethods.isNull(postalCode)) {
                       text_pincode.setText(postalCode);
                    }
                    for (int i = 0; i < stateAndPostalCode.length - 1; i++) {
                        state += (i == 0 ? "" : " ") + stateAndPostalCode[i];
                       // stateVal += (i == 0 ? "" : " ") + stateAndPostalCode[i];
                    }
                } else {
                    state = stateAndPostalCode[stateAndPostalCode.length - 1];
                   // stateVal = stateAndPostalCode[stateAndPostalCode.length - 1];

                }

//                Log.e("state",state);
               // Log.e("stateVal", stateVal);
            }
            if (addressSlice.length > 2) {
                city = addressSlice[addressSlice.length - 3];

            }


            // Log.e("city",city);
           // district = addressSlice[addressSlice.length - 3];
            //cityVal = addressSlice[addressSlice.length - 3];
            if (addressSlice.length == 4)
                stAddress1 = addressSlice[0];
            else if (addressSlice.length > 3) {
                stAddress2 = addressSlice[addressSlice.length - 4];
              String  areaVal = addressSlice[addressSlice.length - 4];
                if (GlobalMethods.isNull(areaVal)) {
                   // text_admin_area.setText(areaVal);
                }

             //   landmark = addressSlice[addressSlice.length - 4];
                //if (GlobalMethods.isNull(landmark)) {
                //     address_lane2.setText(landmark);
                // }
                stAddress1 = "";
                for (int i = 0; i < addressSlice.length - 4; i++) {
                    stAddress1 += (i == 0 ? "" : ", ") + addressSlice[i];
                }
            }
        }
//
//        Log.e("stAddress1",stAddress1);
//        Log.e("stAddress2",stAddress2);

    }
}
