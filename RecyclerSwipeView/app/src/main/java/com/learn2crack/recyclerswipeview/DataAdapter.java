package com.learn2crack.recyclerswipeview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<String> countries;
    private List<String> removedCountries;

    public DataAdapter(List<String> countries) {
        this.countries = countries;
        this.removedCountries = new ArrayList<>();
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        String country = countries.get(i);
        viewHolder.tvCountry.setText(country);

        if (removedCountries.contains(country)) {
            viewHolder.vgBackground.setBackgroundColor(Color.parseColor("#D32F2F"));
            viewHolder.tvCountry.setVisibility(View.GONE);
            viewHolder.btnDel.setVisibility(View.VISIBLE);
            viewHolder.btnDel.setTag(country);
            viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object object = v.getTag();
                    if (object instanceof String) {
                        String country = (String)object;
                        int position = countries.indexOf(country);
                        removedCountries.remove(country);
                        countries.remove(country);
                        notifyItemRemoved(position);
                    }
                }
            });

            viewHolder.btnCancel.setVisibility(View.VISIBLE);
            viewHolder.btnCancel.setTag(i);
            viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object object = v.getTag();
                    if (object instanceof Integer) {
                        clearItem((int)object);
                    }
                }
            });
        } else {
            viewHolder.vgBackground.setBackgroundColor(Color.WHITE);
            viewHolder.tvCountry.setVisibility(View.VISIBLE);
            viewHolder.btnDel.setVisibility(View.GONE);
            viewHolder.btnCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void addItem(String country) {
        countries.add(country);
        notifyItemInserted(countries.size());
    }

    public void removeItem(int position) {
        String country = countries.get(position);
        if (removedCountries.contains(country)) {
            return;
        }

        removedCountries.add(country);
        notifyItemChanged(position);
    }

    public boolean isRemovedItem(int position) {
        return removedCountries.contains(countries.get(position));
    }

    public void clearItem(int position) {
        String country = countries.get(position);
        if (removedCountries.contains(country)) {
            removedCountries.remove(country);
            notifyItemChanged(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ViewGroup vgBackground;
        TextView tvCountry;
        Button btnDel;
        Button btnCancel;

        public ViewHolder(View view) {
            super(view);

            vgBackground = (ViewGroup)view.findViewById(R.id.rl_background);
            tvCountry = (TextView)view.findViewById(R.id.tv_country);
            btnDel = (Button) view.findViewById(R.id.btn_del);
            btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        }
    }
}