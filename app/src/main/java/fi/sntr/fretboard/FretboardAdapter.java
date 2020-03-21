package fi.sntr.fretboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FretboardAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final Instrument mInstrument;

    private static int TYPE_NUMBER = 1;
    private static int TYPE_FRET = 2;

    public FretboardAdapter(Instrument instrument) {
        mInstrument = instrument;
        //mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if(viewType == TYPE_NUMBER) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.fragment_fret_number, parent, false);

            return new NumberViewHolder(view);
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.fragment_fret, parent, false);

            return new FretViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NUMBER) {
            ((NumberViewHolder) holder).setNumber(position);
        } else {
            final int value = position; // - mInstrument.getFretCount();
            ((FretViewHolder) holder).setNoteName(value);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mInstrument.getFretCount()) {
            return TYPE_NUMBER;

        } else {
            return TYPE_FRET;
        }
    }

    @Override
    public int getItemCount() {
        return mInstrument.getNoteCount() + mInstrument.getFretCount();
    }

    public class FretViewHolder extends ViewHolder {
        private final Button mFretButton;

        FretViewHolder(View view) {
            super(view);
            mFretButton = view.findViewById(R.id.fret_button);
        }

        void setNoteName(int position) {
            mFretButton.setText(mInstrument.getNote(position));
        }
    }

    public static class NumberViewHolder extends ViewHolder {
        private final TextView mFretNumber;

        NumberViewHolder(View view) {
            super(view);
            mFretNumber = view.findViewById(R.id.fret_number);
        }

        void setNumber(int position) {
            mFretNumber.setText(Integer.toString(position));
        }
    }
}
