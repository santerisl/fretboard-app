package fi.sntr.fretboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FretboardFragment extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    public FretboardFragment() {}

    @SuppressWarnings("unused")
    public static FretboardFragment newInstance() {
        FretboardFragment fragment = new FretboardFragment();
        return fragment;
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
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 13);
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setInstrument(Instrument instrument) {
        if(mAdapter == null) {
            mAdapter = new FretboardAdapter(instrument);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}