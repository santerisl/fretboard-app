package fi.sntr.fretboard;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import fi.sntr.fretboard.FretboardFragment.FretListener;

public class FretboardAdapter extends RecyclerView.Adapter<FretboardAdapter.ViewHolder> {

    private final List<Integer> mValues;
    private final FretListener mListener;

    public FretboardAdapter(List<Integer> items, FretListener listener) {
        mValues = items;
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
        final int value = mValues.get(position);
        holder.mFretButton.setText(mValues.get(position).toString());

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
        return mValues.size();
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
