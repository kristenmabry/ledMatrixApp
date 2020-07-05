package com.kristenmabry.ledmatrix;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.getColor;
import static androidx.core.content.ContextCompat.startActivity;

public class LayoutListAdapter extends RecyclerView.Adapter<LayoutListAdapter.ViewHolder> implements View.OnClickListener {
    private MatrixFileLayout[] dataset;
    private ViewGroup viewGroup;

    public LayoutListAdapter(MatrixFileLayout[] newData) {
        dataset = newData;
    }

    @NonNull
    @Override
    public LayoutListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroup = parent;
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new LayoutListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.view.setId(position);
        holder.view.setBackground(ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.list_item_ripple_effect));
        holder.view.setOnClickListener(this);
        holder.titleView.setText(dataset[position].getLayout().getName());
        holder.subtitleView.setText(dataset[position].getSubtitle());
        holder.subtitleView.setTextColor(getColor(viewGroup.getContext(), android.R.color.darker_gray));
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        MatrixFileLayout layout = dataset[view.getId()];
        if (layout.getType() == LayoutTypes.Text) {
            intent = new Intent(viewGroup.getContext(), SendTextActivity.class);
            intent.putExtra(SendTextActivity.KEY_IS_NEW, false);
            intent.putExtra(SaveLayoutActivity.KEY_MATRIX_LAYOUT, layout.getLayout());
            startActivity(viewGroup.getContext(), intent, null);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView titleView;
        public TextView subtitleView;
        public ViewHolder(View v) {
            super(v);
            view = v;
            titleView = v.findViewById(android.R.id.text1);
            subtitleView = v.findViewById(android.R.id.text2);
        }
    }
}
