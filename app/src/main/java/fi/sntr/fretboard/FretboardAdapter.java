package fi.sntr.fretboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fi.sntr.fretboard.music.Instrument;

public class FretboardAdapter extends RecyclerView.Adapter<ViewHolder> implements Instrument.InstrumentChangeListener {

    private final Instrument mInstrument;

    public static int TYPE_NUMBER = 1;
    public static int TYPE_FRET = 2;

    private final int[] accidentalPositions = {1, 3, 6, 8, 10};

    FretboardAdapter(Instrument instrument) {
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
            ((NumberViewHolder) holder).setNumber(getFret(position));
        } else {
            final FretViewHolder fH = (FretViewHolder) holder;
            fH.setNoteName(position);
            fH.mFretButton.setOnClickListener(v -> {
                mInstrument.setSelected(fH.string, fH.fret);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % (mInstrument.getStringCount() + 1) == 0) {
            return TYPE_NUMBER;
        } else {
            return TYPE_FRET;
        }
    }

    @Override
    public int getItemCount() {
        return (mInstrument.getStringCount() + 1) * mInstrument.getFretCount();
    }

    @Override
    public void onInstrumentChange() {
        notifyDataSetChanged();
    }

    @Override
    public void onSelectedChange(int string, int oldFret, int newFret) {
        if(oldFret >= 0) {
            notifyItemChanged(getPosition(string, oldFret));
        }
        if(newFret >= 0) {
            notifyItemChanged(getPosition(string, newFret));
        }
    }

    @Override
    public void onFretCountChange(int oldFretCount, int newFretCount) {
        int change = (newFretCount - oldFretCount) * (mInstrument.getStringCount() + 1);
        int start = getPosition(mInstrument.getStringCount(), mInstrument.getFretCount());
        if(change > 0) {
            notifyItemRangeInserted(start, change);
        }else if(change < 0) {
            change = Math.abs(change);
            notifyItemRangeRemoved(start - change, change);
        }
    }

    @Override
    public void onIsSharpChange(boolean isSharp) {
        for(int s = 0; s < mInstrument.getStringCount(); s++) {
            for(int f = 0; f < mInstrument.getFretCount(); f++) {
                int note = mInstrument.getNoteNumber(s, f);
                for (int accidentalPosition : accidentalPositions) {
                    if (accidentalPosition == note) {
                        notifyItemChanged(getPosition(s, f));
                    }
                }
            }
        }
    }

    @Override
    public void onHighlightChange() {
        notifyDataSetChanged();
    }

    private int getString(int position) {
        return position % (mInstrument.getStringCount() + 1) - 1;
    }

    private int getFret(int position) {
        return position / (mInstrument.getStringCount() + 1);
    }

    private int getPosition(int string, int fret) {
        return (string + 1) + (mInstrument.getStringCount() + 1) * fret;
    }

    class FretViewHolder extends ViewHolder {
        final FretButton mFretButton;
        int string;
        int fret;

        FretViewHolder(View view) {
            super(view);
            mFretButton = view.findViewById(R.id.fret_button);
        }

        void setNoteName(int position) {
            string = getString(position);
            fret = getFret(position);
            String text = mInstrument.getNote(string, fret);

            mFretButton.setFretSelected(mInstrument.isSelected(string, fret));
            mFretButton.setFretHighlightedChord(mInstrument.isHighlightedChord(string, fret));
            mFretButton.setFretHighlightedScale(mInstrument.isHighlightedScale(string, fret));
            mFretButton.setText(text);
        }
    }

    static class NumberViewHolder extends ViewHolder {
        private final TextView mFretNumber;
        NumberViewHolder(View view) {
            super(view);
            mFretNumber = view.findViewById(R.id.fret_number);
        }

        void setNumber(int fretNumber) {
            mFretNumber.setText(Integer.toString(fretNumber));
        }
    }
}
