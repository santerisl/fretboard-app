package fi.sntr.fretboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements FretboardFragment.FretListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView fretboard = findViewById(R.id.fretboard_view);

        Instrument instrument = new Instrument(13,4, 9, 2, 7);

        RecyclerView.Adapter mAdapter = new FretboardAdapter(instrument, this);
        fretboard.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Integer item) {
        Log.d("Debug", "i: " + item);
    }
}
