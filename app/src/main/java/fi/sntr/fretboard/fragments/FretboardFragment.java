package fi.sntr.fretboard.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.sntr.fretboard.adapters.FretboardAdapter;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.Instrument;


public class FretboardFragment extends Fragment implements Instrument.InstrumentChangeListener {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private Instrument mInstrument;
    public FretboardFragment() {}

    @SuppressWarnings("unused")
    public static FretboardFragment newInstance() {
        return new FretboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fretboard, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void setInstrument(Instrument instrument) {
        mInstrument = instrument;
        if(mAdapter == null) {
            mAdapter = new FretboardAdapter(instrument);
            mRecyclerView.setAdapter(mAdapter);
            mGridLayoutManager = new GridLayoutManager(
                    getContext(),
                    instrument.getStringCount() * 2 + 1,
                    GridLayoutManager.HORIZONTAL,
                    false);

            mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(mAdapter.getItemViewType(position) == FretboardAdapter.TYPE_NUMBER) {
                        return 1;
                    } else {
                        return 2;
                    }
                }
            });
            mRecyclerView.setLayoutManager(mGridLayoutManager);
        }
        mInstrument.addChangeListener(this);
    }

    @Override
    public void onInstrumentChange() {
        mGridLayoutManager.setSpanCount(mInstrument.getStringCount() * 2 + 1);
    }

    @Override
    public void onSelectedChange(int string, int oldFret, int newFret) {

    }

    @Override
    public void onFretCountChange(int oldFretCount, int newFretCount) {

    }

    @Override
    public void onIsSharpChange() {

    }

    @Override
    public void onHighlightChange() {

    }
}
