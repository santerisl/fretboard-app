package fi.sntr.fretboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;


import java.util.List;

import fi.sntr.fretboard.R;

/**
 * Adapter for a toggleable group of buttons
 */
public class ButtonToggleListAdapter<T> extends RecyclerView.Adapter<ButtonToggleListAdapter.ButtonViewHolder> {

    private static final int PAYLOAD_TOGGLE = 1;

    private final T[] items;
    private final OnItemChangeListener mListener;
    private int selectedPosition = -1;

    /**
     * @param items items to add to the adapter
     * @param listener listener to call on selected item change
     */
    public ButtonToggleListAdapter(T[] items, OnItemChangeListener listener) {
        this.items = items;
        mListener = listener;
    }

    /**
     * Sets the currently selected position
     * @param selected selected position to set
     */
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
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position, List<Object> payloads) {
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
        final MaterialButton button;

        ButtonViewHolder(@NonNull View view) {
            super(view);
            button = view.findViewById(R.id.highlight_button);
        }
    }

    /**
     * Listener for changes of the currently selected item
     */
    public interface OnItemChangeListener {
        /**
         * @param position position of selected item, or -1 if none selected
         */
        void onSelectedChange(int position);
    }
}