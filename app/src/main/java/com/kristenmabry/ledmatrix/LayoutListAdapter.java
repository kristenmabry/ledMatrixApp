package com.kristenmabry.ledmatrix;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        LayoutListAdapter.ViewHolder vh = new LayoutListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setId(position);
        holder.textView.setText(dataset[position].getDisplayName());
        holder.textView.setOnClickListener(this);
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
        public TextView textView;
        public ViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
}
