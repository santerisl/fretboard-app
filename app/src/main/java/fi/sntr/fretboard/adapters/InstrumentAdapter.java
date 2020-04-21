package fi.sntr.fretboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fi.sntr.fretboard.R;
import fi.sntr.fretboard.data.Instruments;

/**
 * Adapter for displaying a list of default and custom instruments and a button for new instrument.
 *
 * Should always one instrument selected
 *
 * InstrumentData with an id lower than {@link Instruments#DEFAULT_INSTRUMENT_COUNT} will not
 * have a remove button next to the instrument listing.
 */
public class InstrumentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Instruments instruments;
    private final OnItemClickListener mListener;
    private int selectedPosition;

    public InstrumentAdapter(Instruments instruments, OnItemClickListener listener) {
        this.instruments = instruments;
        mListener = listener;
    }

    /**
     * @param selected item to set selected to
     */
    public void setSelected(int selected) {
        this.selectedPosition = selected;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.instrument_new_button, parent, false);
            return new AddInstrumentViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.instrument_select_item, parent, false);
            return new InstrumentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 0) {
            ((AddInstrumentViewHolder) holder)
                    .addButton.setOnClickListener((l) -> mListener.onNewClick());
        } else {
            final InstrumentViewHolder vh = (InstrumentViewHolder) holder;
            vh.nameText.setText(instruments.get(position).getName());
            vh.stringsText.setText(instruments.get(position).getStringsText());
            vh.view.setActivated(position == selectedPosition);

            if(position < Instruments.DEFAULT_INSTRUMENT_COUNT) {
                vh.removeButton.setVisibility(View.INVISIBLE);
                vh.editButton.setVisibility(View.INVISIBLE);
            } else {
                vh.removeButton.setVisibility(View.VISIBLE);
                vh.editButton.setVisibility(View.INVISIBLE);
                vh.removeButton.setOnClickListener(l -> {
                    selectedPosition = 0;
                    mListener.onSelectedChange(selectedPosition);
                    instruments.remove(position);
                    notifyDataSetChanged();
                });
            }

            vh.view.setOnClickListener(l -> {
                if(selectedPosition != position) {
                    selectedPosition = position;
                    mListener.onSelectedChange(selectedPosition);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == instruments.count()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return instruments.count() + 1;
    }

    static class InstrumentViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView nameText;
        final TextView stringsText;
        final Button removeButton;
        final Button editButton;

        InstrumentViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            nameText = view.findViewById(R.id.instrument_name_text);
            stringsText = view.findViewById(R.id.instrument_strings_text);
            removeButton = view.findViewById(R.id.instrument_remove_button);
            editButton = view.findViewById(R.id.instrument_edit_button);
        }
    }

    static class AddInstrumentViewHolder extends RecyclerView.ViewHolder {
        final Button addButton;
        AddInstrumentViewHolder(@NonNull View view) {
            super(view);
            addButton = view.findViewById(R.id.add_instrument_button);
        }
    }

    public interface OnItemClickListener {
        /**
         * @param position position of selected item
         */
        void onSelectedChange(int position);
        void onNewClick();
    }
}