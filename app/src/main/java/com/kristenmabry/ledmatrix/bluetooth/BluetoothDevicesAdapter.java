package com.kristenmabry.ledmatrix.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kristenmabry.ledmatrix.R;

import java.util.ArrayList;
import java.util.Collections;

import static androidx.core.content.ContextCompat.getColor;

public class BluetoothDevicesAdapter extends RecyclerView.Adapter<BluetoothDevicesAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<BluetoothDevice> dataset;
    private ViewGroup viewGroup;
    private Activity activity;
    private ArrayList<ViewHolder> viewHolders;

    public BluetoothDevicesAdapter(Activity a, BluetoothDevice[] newData) {
        dataset = new ArrayList<>();
        this.viewHolders = new ArrayList<>();
        Collections.addAll(dataset, newData);
        this.activity = a;
    }

    @NonNull
    @Override
    public BluetoothDevicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroup = parent;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.two_line_list_item, parent, false);
        ViewHolder vh = new BluetoothDevicesAdapter.ViewHolder(v);
        this.viewHolders.add(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.view.setId(position);
        holder.view.setBackground(ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.list_item_ripple_effect));
        holder.view.setOnClickListener(this);
        holder.titleView.setText(dataset.get(position).getName());
        holder.subtitleView.setText(dataset.get(position).getAddress());
        holder.subtitleView.setTextColor(getColor(viewGroup.getContext(), android.R.color.darker_gray));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public void onClick(View view) {
        this.viewHolders.forEach(vh ->
            vh.selectedBox.setBackgroundColor(getColor(viewGroup.getContext(), android.R.color.darker_gray))
        );
        BluetoothDevice device = dataset.get(view.getId());
        this.viewHolders.get(view.getId()).selectedBox.setBackgroundColor(getColor(viewGroup.getContext(), R.color.green));
        SharedPreferences sharedPref = this.activity.getSharedPreferences(view.getContext().getString(R.string.shared_preferences_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(view.getContext().getString(R.string.save_selected_device_key), device.getAddress());
        editor.apply();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView titleView;
        public TextView subtitleView;
        public View selectedBox;
        public ViewHolder(View v) {
            super(v);
            view = v;
            titleView = v.findViewById(R.id.text1);
            subtitleView = v.findViewById(R.id.text2);
            selectedBox = v.findViewById(R.id.selected);
        }

    }
}
