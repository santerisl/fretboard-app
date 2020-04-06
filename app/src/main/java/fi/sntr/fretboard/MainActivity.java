package fi.sntr.fretboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import fi.sntr.fretboard.music.Instrument;
import fi.sntr.fretboard.tabs.DefaultFragment;
import fi.sntr.fretboard.tabs.HighlightFragment;
import fi.sntr.fretboard.tabs.InstrumentFragment;

public class MainActivity extends AppCompatActivity {

    public final Instrument instrument = new Instrument();

    private final String[] TAB_NAMES = {"Default", "Highlight", "Instrument"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instrument.setFretCount(13);
        instrument.setRootNotes(4, 9, 2, 7);

        final FretboardFragment fretboard = (FretboardFragment) getSupportFragmentManager().findFragmentById(R.id.fretboard_view);
        if(fretboard != null) {
            fretboard.setInstrument(instrument);
        }

        TabAdapter tabAdapter = new TabAdapter();
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(TAB_NAMES[position]);
        }).attach();

        instrument.setChangeListener(new Instrument.InstrumentChangeListener() {
            @Override
            public void onInstrumentChange() {
                fretboard.updateSpanCount();
            }

            @Override
            public void onSelectedChange(int string, int oldFret, int newFret) {}

            @Override
            public void onFretCountChange(int oldFretCount, int newFretCount) {}

            @Override
            public void onIsSharpChange(boolean isSharp) {}

            @Override
            public void onHighlightChange() {}
        });
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
                    return new DefaultFragment();
            }
        }

        @Override
        public int getItemCount() {
            return TAB_NAMES.length;
        }
    }
}
