package fi.sntr.fretboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fi.sntr.fretboard.R;

public class TextListAdapter<T extends Object> extends RecyclerView.Adapter<TextListAdapter.TextViewHolder> {

    private T[] mTextViews;
    private OnItemClickListener mListener;

    public TextListAdapter(T[] textViews, OnItemClickListener listener) {
        mTextViews = textViews;
        mListener = listener;
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
        holder.view.setOnClickListener(l -> mListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return mTextViews.length;
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View view;

        public TextViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            textView = view.findViewById(R.id.text_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}