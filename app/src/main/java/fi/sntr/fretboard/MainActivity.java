package fi.sntr.fretboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FretboardFragment fretboard = (FretboardFragment) getSupportFragmentManager().findFragmentById(R.id.fretboard_view);

        final Instrument instrument = new Instrument(13,4, 9, 2, 7);
        fretboard.setInstrument(instrument);

        final TextView selectedText = findViewById(R.id.selected_text);

        instrument.setSelectedChangeListener(new Instrument.SelectedChangeListener() {
            @Override
            public void onSelectedChange(int string, int oldFret, int newFret) {
                String text = "";
                for(int i = 0; i < instrument.getStringCount(); i++) {
                    text += instrument.getNote(i, instrument.getSelected(i));
                    text += i < instrument.getStringCount() - 1 ? "  |  ": "";
                }
                selectedText.setText(text);
            }
        });
    }
}
