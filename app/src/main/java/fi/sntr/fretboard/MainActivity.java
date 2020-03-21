package fi.sntr.fretboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FretboardFragment fretboard = (FretboardFragment) getSupportFragmentManager().findFragmentById(R.id.fretboard_view);

        Instrument instrument = new Instrument(13,4, 9, 2, 7);
        fretboard.setInstrument(instrument);
    }
}
