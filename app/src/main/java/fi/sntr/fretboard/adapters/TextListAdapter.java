package fi.sntr.fretboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fi.sntr.fretboard.R;

public class TextListAdapter<T extends Object> extends RecyclerView.Adapter<TextListAdapter.TextViewHolder> {

    private T[] mTextViews;

    public TextListAdapter(T[] textViews) {
        mTextViews = textViews;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_view, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.textView.setText(mTextViews[position].toString());
    }

    @Override
    public int getItemCount() {
        return mTextViews.length;
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TextViewHolder(@NonNull View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
        }
    }
}