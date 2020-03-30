package fi.sntr.fretboard.tabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import fi.sntr.fretboard.MainActivity;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.Instrument;
import fi.sntr.fretboard.music.Music;

public class HighlightFragment extends Fragment {

    private Instrument mInstrument;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_highlight, container, false);

        RecyclerView chordsRecycler = view.findViewById(R.id.chords_view);
        HighlightViewAdapter mChordsAdapter = new HighlightViewAdapter(Music.CHORDS);
        chordsRecycler.setAdapter(mChordsAdapter);

        LinearLayoutManager chordsLayoutManager = new LinearLayoutManager(getContext());
        chordsRecycler.setLayoutManager(chordsLayoutManager);

        RecyclerView scalesRecycler = view.findViewById(R.id.scales_view);
        HighlightViewAdapter mScalesAdapter = new HighlightViewAdapter(Music.SCALES);
        scalesRecycler.setAdapter(mScalesAdapter);

        LinearLayoutManager scalesLayoutManager = new LinearLayoutManager(getContext());
        scalesRecycler.setLayoutManager(scalesLayoutManager);
        Log.d("Debug", "Music.SCALES " + Music.SCALES.size());
        Log.d("Debug", "Music.CHORDS " + Music.CHORDS.size());

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mInstrument = ((MainActivity) context).instrument;
        }
    }

    class HighlightViewAdapter extends Adapter<HighlightViewHolder> {

        private Map<String, int[]> mHighlights;

        public HighlightViewAdapter(Map<String, int[]> highlights) {
            mHighlights = highlights;
            Log.d("mHighlights", "mHighlights " + mHighlights.size());
        }

        @NonNull
        @Override
        public HighlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_highlight_item, parent, false);
            return new HighlightViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HighlightViewHolder holder, int position) {
            holder.highlightNameText.setText(mHighlights.keySet().toArray()[position].toString());
            Log.d("Deubg", mHighlights.keySet().toArray()[position].toString());
        }

        @Override
        public int getItemCount() {
            return mHighlights.size();
        }
    }

    private static class HighlightViewHolder extends ViewHolder {
        TextView highlightNameText;

        public HighlightViewHolder(@NonNull View view) {
            super(view);
            Log.d("Deubg","Create hl view");
            highlightNameText = view.findViewById(R.id.highlight_name_text);
        }
    }
}
