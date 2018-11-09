package com.vvc.addpartoapp.presentation.globalutils.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextView;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextViewLight;
import com.vvc.addpartoapp.presentation.globalutils.custom.CustomTextViewSemiBold;

import org.json.JSONArray;
import org.json.JSONException;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

    private JSONArray vendordsData;
    private Context context;


    public MenuListAdapter(Context context, JSONArray vendordsData) {
        this.vendordsData = vendordsData;

        this.context = context;

    }


    @Override
    public MenuListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_items_layout, viewGroup, false);

        return new MenuListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MenuListAdapter.ViewHolder holder, final int i) {

        if (vendordsData != null) {

            try {
                if (GlobalMethods.isNull(vendordsData.getJSONObject(i).getString("category"))) {
                    holder.category_name.setText(vendordsData.getJSONObject(i).getString("category"));
                }
                if (GlobalMethods.isNull(vendordsData.getJSONObject(i).getString("restID"))) {
                    holder.restaurant_id.setText(vendordsData.getJSONObject(i).getString("restID"));
                }
                if (GlobalMethods.isNull(vendordsData.getJSONObject(i).getString("itemName"))) {
                    holder.item_name.setText(vendordsData.getJSONObject(i).getString("itemName"));
                }
                if (GlobalMethods.isNull(vendordsData.getJSONObject(i).getString("maxQty"))) {
                    holder.max_qty.setText("maxQty: "+vendordsData.getJSONObject(i).getString("maxQty"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    @SuppressLint("NewApi")

    @Override
    public int getItemCount() {
        return vendordsData.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private CustomTextViewSemiBold category_name;
        private CustomTextViewLight restaurant_id;
        private CustomTextView item_name, max_qty;

        public ViewHolder(View view) {
            super(view);

            category_name = (CustomTextViewSemiBold) view.findViewById(R.id.category_name);
            restaurant_id = (CustomTextViewLight) view.findViewById(R.id.restaurant_id);
            item_name = (CustomTextView) view.findViewById(R.id.item_name);
            max_qty = (CustomTextView) view.findViewById(R.id.max_qty);
            view.setOnClickListener(this);


        }


        @Override
        public void onClick(final View view) {

        }


    }
}

