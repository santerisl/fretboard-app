package fi.sntr.fretboard;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fi.sntr.fretboard.FretboardFragment.FretListener;

public class FretboardAdapter extends RecyclerView.Adapter<FretboardAdapter.ViewHolder> {

    private final Instrument mInstrument;
    private final FretListener mListener;

    public FretboardAdapter(Instrument instrument, FretListener listener) {
        mInstrument = instrument;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_fret, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int value = position;
        String text = mInstrument.getNote(position);
        holder.mFretButton.setText(text);

        holder.mFretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onClick(value);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInstrument.getNoteCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final Button mFretButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFretButton = view.findViewById(R.id.fret_button);
        }
    }
}
