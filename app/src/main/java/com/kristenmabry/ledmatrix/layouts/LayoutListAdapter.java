package com.kristenmabry.ledmatrix.layouts;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kristenmabry.ledmatrix.R;
import com.kristenmabry.ledmatrix.activities.SaveLayoutActivity;
import com.kristenmabry.ledmatrix.activities.SendTextActivity;
import com.kristenmabry.ledmatrix.FileUtils;

import java.util.ArrayList;
import java.util.Collections;

import static androidx.core.content.ContextCompat.getColor;
import static androidx.core.content.ContextCompat.startActivity;

public class LayoutListAdapter extends RecyclerView.Adapter<LayoutListAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private ArrayList<MatrixFileLayout> dataset;
    private ViewGroup viewGroup;

    public LayoutListAdapter(MatrixFileLayout[] newData) {
        dataset = new ArrayList<>();
        Collections.addAll(dataset, newData);
    }

    @NonNull
    @Override
    public LayoutListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroup = parent;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.two_line_list_item, parent, false);
        return new LayoutListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.view.setId(position);
        holder.view.setBackground(ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.list_item_ripple_effect));
        holder.view.setOnClickListener(this);
        holder.view.setOnLongClickListener(this);
        holder.titleView.setText(dataset.get(position).getLayout().getName());
        holder.subtitleView.setText(dataset.get(position).getSubtitle());
        holder.subtitleView.setTextColor(getColor(viewGroup.getContext(), android.R.color.darker_gray));
        holder.selected.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void removeAt(int position) {
        dataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataset.size());
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        MatrixFileLayout layout = dataset.get(view.getId());
        if (layout.getType() == LayoutTypes.Text) {
            intent = new Intent(viewGroup.getContext(), SendTextActivity.class);
            intent.putExtra(SendTextActivity.KEY_IS_NEW, false);
            intent.putExtra(SaveLayoutActivity.KEY_MATRIX_LAYOUT, layout.getLayout());
            startActivity(viewGroup.getContext(), intent, null);
        }
    }

    @Override
    public boolean onLongClick(final View view) {
        final int position = view.getId();

        View popupView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_options_popup, viewGroup, false);
        final PopupWindow popup = new PopupWindow(viewGroup, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popup.setContentView(popupView);
        popup.setAnimationStyle(R.style.popInOutAnimation);
        popup.showAtLocation(viewGroup, Gravity.CENTER, 0, 0);

        popupView.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLayout(position);
                popup.dismiss();
            }
        });

        popupView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLayout(position);
                popup.dismiss();
            }
        });

        return true;
    }

    private void editLayout(int position) {
        MatrixFileLayout selectedLayout = dataset.get(position);
        Intent intent = new Intent(viewGroup.getContext(), SaveLayoutActivity.class);
        intent.putExtra(SaveLayoutActivity.KEY_MATRIX_LAYOUT, selectedLayout.getLayout());
        startActivity(viewGroup.getContext(), intent, null);
    }

    private void deleteLayout(int position) {
        MatrixFileLayout selectedLayout = dataset.get(position);
        if (FileUtils.deleteFile(viewGroup.getContext(), selectedLayout.getLayout().getFileName())) {
            removeAt(position);
            String name = selectedLayout.getLayout().getName();
            Toast.makeText(viewGroup.getContext(), viewGroup.getResources().getString(R.string.layout_deleted, name), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(viewGroup.getContext(), viewGroup.getResources().getString(R.string.error_deleting), Toast.LENGTH_SHORT).show();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public View selected;
        public TextView titleView;
        public TextView subtitleView;
        public ViewHolder(View v) {
            super(v);
            view = v;
            selected = v.findViewById(R.id.selected);
            titleView = v.findViewById(R.id.text1);
            subtitleView = v.findViewById(R.id.text2);
        }

    }
}
