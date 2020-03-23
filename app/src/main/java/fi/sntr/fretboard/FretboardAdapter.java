package fi.sntr.fretboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FretboardAdapter extends RecyclerView.Adapter<ViewHolder> implements Instrument.InstrumentChangeListener {

    private final Instrument mInstrument;

    private static int TYPE_NUMBER = 1;
    private static int TYPE_FRET = 2;

    private final int[] accidentalPositions = {1, 3, 6, 8, 10};

    public FretboardAdapter(Instrument instrument) {
        mInstrument = instrument;
        mInstrument.setChangeListener(this);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NUMBER) {
            ((NumberViewHolder) holder).setNumber(position);
        } else {
            final FretViewHolder fH = (FretViewHolder) holder;
            fH.setNoteName(position);

            fH.mFretButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInstrument.setSelected(fH.string, fH.fret);
                }
            });
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

    private int getFretPosition(int string, int fret) {
        int frets = mInstrument.getFretCount();
        return frets * (string + 1) + fret;
    }

    @Override
    public void onSelectedChange(int string, int oldFret, int newFret) {
        int frets = mInstrument.getFretCount();
        int stringPosition = frets + frets * string;

        notifyItemChanged(stringPosition + oldFret);
        notifyItemChanged(stringPosition + newFret);
    }

    @Override
    public void onFretCountChange(int oldFretCount, int newFretCount) {
        for(int i = 0; i < mInstrument.getStringCount() + 1; i++) {
            int change = newFretCount - oldFretCount;
            int start = i * oldFretCount + oldFretCount + change * i;
            if(change > 0) {
                notifyItemRangeInserted(start, change);
            }else if(change < 0) {
                change = Math.abs(change);
                notifyItemRangeRemoved(start - change, change);
            }
        }
    }

    @Override
    public void onIsSharpChange(boolean isSharp) {
        for(int s = 0; s < mInstrument.getStringCount(); s++) {
            for(int f = 0; f < mInstrument.getFretCount(); f++) {
                int note = mInstrument.getNoteNumber(s, f);
                for (int accidentalPosition : accidentalPositions) {
                    if (accidentalPosition == note) {
                        notifyItemChanged(getFretPosition(s, f));
                    }
                }
            }
        }
    }

    public class FretViewHolder extends ViewHolder {
        final Button mFretButton;
        int string;
        int fret;

        FretViewHolder(View view) {
            super(view);
            mFretButton = view.findViewById(R.id.fret_button);
        }

        void setNoteName(int position) {
            string = (position - mInstrument.getFretCount()) / mInstrument.getFretCount();
            fret = (position - mInstrument.getFretCount()) % mInstrument.getFretCount();
            String text = mInstrument.getNote(string, fret);
            if(mInstrument.isSelected(string, fret)) {
                text += "!";
            }
            mFretButton.setText(text);
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
