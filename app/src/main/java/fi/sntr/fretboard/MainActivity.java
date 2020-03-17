package fi.sntr.fretboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FretboardFragment.FretListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.fretboard_fragment);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 13);
        recyclerView.setLayoutManager(layoutManager);

        List<Integer> frets = new ArrayList<>();
        for(int i = 0; i < 13*5; i++) {
            frets.add(i%13);
        }

        RecyclerView.Adapter mAdapter = new FretboardAdapter(frets, this);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(Integer item) {
        Log.d("Debug", "i: " + item);
    }
}
