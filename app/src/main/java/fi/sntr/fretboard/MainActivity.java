package fi.sntr.fretboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fi.sntr.fretboard.Instrument.InstrumentChangeListener;

public class MainActivity extends AppCompatActivity {

    private Instrument instrument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FretboardFragment fretboard = (FretboardFragment) getSupportFragmentManager().findFragmentById(R.id.fretboard_view);
        final TextView selectedText = findViewById(R.id.selected_text);

        instrument = new Instrument(13,4, 9, 2, 7);
        fretboard.setInstrument(instrument);

        instrument.setChangeListener(new InstrumentChangeListener() {
            @Override
            public void onSelectedChange(int string, int oldFret, int newFret) {
                String text = "";
                for(int i = 0; i < instrument.getStringCount(); i++) {
                    text += instrument.getNote(i, instrument.getSelected(i));
                    text += i < instrument.getStringCount() - 1 ? "  |  ": "";
                }
                selectedText.setText(text);
            }

            @Override
            public void onFretCountChange(int oldFretCount, int newFretCount) {
                fretboard.getLayoutManager().setSpanCount(newFretCount);
            }

            @Override
            public void onIsSharpChange(boolean isSharp) {

            }
        });
    }

    public void addFrets(View view) {
        if(instrument.getFretCount() < 19) {
            instrument.setFretCount(instrument.getFretCount() + 1);
        }
    }

    public void removeFrets(View view) {
        if(instrument.getFretCount() > 6) {
            instrument.setFretCount(instrument.getFretCount() - 1);
        }
    }

    public void toggleSharpFlat(View view) {
        instrument.toggleSharp();
    }
}
