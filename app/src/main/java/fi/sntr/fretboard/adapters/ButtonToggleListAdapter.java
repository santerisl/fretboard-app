package fi.sntr.fretboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;


import java.util.List;

import fi.sntr.fretboard.R;

public class ButtonToggleListAdapter<T> extends RecyclerView.Adapter<ButtonToggleListAdapter.ButtonViewHolder> {

    private static final int PAYLOAD_TOGGLE = 1;

    private T[] items;
    private OnItemClickListener mListener;
    private int selectedPosition = -1;

    public ButtonToggleListAdapter(T[] items, OnItemClickListener listener) {
        this.items = items;
        mListener = listener;
    }

    public void setSelected(int selected) {
        this.selectedPosition = selected;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.highlight_group_button, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ButtonViewHolder holder, int position) {
        holder.button.setText(items[position].toString());
        holder.button.setChecked(position == selectedPosition);

        holder.button.setOnClickListener(l -> {
            if(selectedPosition >= 0) {
                notifyItemChanged(selectedPosition, PAYLOAD_TOGGLE);
            }
            if(selectedPosition != position) {
                selectedPosition = position;
                notifyItemChanged(position, PAYLOAD_TOGGLE);
            } else {
                selectedPosition = -1;
            }
            mListener.onSelectedChange(selectedPosition);
        });
    }

    @Override
    public void onBindViewHolder(ButtonViewHolder holder, int position, List<Object> payloads) {
        if (!payloads.isEmpty()) {
            for (final Object payload : payloads) {
                if (payload.equals(PAYLOAD_TOGGLE)) {
                    holder.button.setChecked(position == selectedPosition);
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder {
        MaterialButton button;

        public ButtonViewHolder(@NonNull View view) {
            super(view);
            button = view.findViewById(R.id.highlight_button);
        }
    }

    public interface OnItemClickListener {
        void onSelectedChange(int position);
    }
}