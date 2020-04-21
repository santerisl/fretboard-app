package fi.sntr.fretboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import fi.sntr.fretboard.data.InstrumentData;
import fi.sntr.fretboard.data.Instruments;
import fi.sntr.fretboard.dialogs.InstrumentDialog;
import fi.sntr.fretboard.fragments.FretboardFragment;
import fi.sntr.fretboard.fragments.InstrumentFragment;
import fi.sntr.fretboard.music.Instrument;
import fi.sntr.fretboard.fragments.ChordsFragment;
import fi.sntr.fretboard.fragments.HighlightFragment;

public class MainActivity extends AppCompatActivity implements InstrumentDialog.InstrumentDialogListener {

    /** instrument object used for most of the functions of the application */
    public final Instrument instrument = new Instrument();

    /** data for default and custom instruments */
    public final Instruments instruments = new Instruments();

    private ViewPager2 viewPager;

    private final String[] TAB_NAMES = {"Chords", "Highlight", "Instrument"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int fretCount = sharedPref.getInt("fretCount", 12);

        instruments.selectedId = sharedPref.getInt("instrumentId", 0);
        instruments.addCustomInstruments(loadCustomInstruments());

        instrument.setFretCount(fretCount);
        instrument.setRootNotes(instruments.get().getStrings());

        final FretboardFragment fretboard = (FretboardFragment) getSupportFragmentManager().findFragmentById(R.id.fretboard_view);
        if(fretboard != null) {
            fretboard.setInstrument(instrument);
        }

        TabAdapter tabAdapter = new TabAdapter();
        viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(TAB_NAMES[position])).attach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    /**
     * Gets custom instrument data from preferences
     * @return list of custom instruments saved in preferences
     */
    private List<InstrumentData> loadCustomInstruments() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String customInstrumentsJson = sharedPref.getString("customInstruments", null);
        if(customInstrumentsJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(customInstrumentsJson, new TypeToken<List<InstrumentData>>(){}.getType());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Saves the custom instrument data, selected instrument id and instrument fret count
     * to preferences
     */
    private void savePreferences() {
        Gson gson = new Gson();
        List<InstrumentData> customInstruments = new ArrayList<>();
        for(int i = Instruments.DEFAULT_INSTRUMENT_COUNT; i < instruments.count(); i++) {
            customInstruments.add(instruments.get(i));
        }

        String json = gson.toJson(customInstruments);

        SharedPreferences.Editor prefsEditor = getPreferences(Context.MODE_PRIVATE).edit();
        prefsEditor.putInt("fretCount", instrument.getFretCount());
        prefsEditor.putInt("instrumentId", instruments.selectedId);
        prefsEditor.putString("customInstruments", json);
        prefsEditor.apply();
    }


    @Override

    public void onSaveClick(InstrumentData instrumentData) {
        instruments.addCustomInstrument(instrumentData);
        instruments.selectedId = instruments.count() -1;
        instrument.setRootNotes(instrumentData.getStrings());
        viewPager.setCurrentItem(0);
    }

    class TabAdapter extends FragmentStateAdapter {
        TabAdapter() {
            super(getSupportFragmentManager(), getLifecycle());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 2:
                    return new InstrumentFragment();
                case 1:
                    return new HighlightFragment();
                case 0:
                default:
                    return new ChordsFragment();
            }
        }

        @Override
        public int getItemCount() {
            return TAB_NAMES.length;
        }
    }
}
