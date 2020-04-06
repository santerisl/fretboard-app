package fi.sntr.fretboard.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChordFinder {

    public static List<CompareResult> findChords(List<Integer> selected){
        List<CompareResult> returnChords = new ArrayList<>();

        for(int i = 0; i < selected.size(); i++){
            int baseNote = selected.get(i);
            List<Integer> notes = new ArrayList<>();
            for(int n = 0; n < selected.size(); n++){
                notes.add(getNoteNumber(selected.get(n) - baseNote));
            }
            Collections.sort(notes);

            for(int j = 0; j < Music.CHORDS.length; j++){
                if(Music.CHORDS[j].intervals.length >= notes.size()) {
                    CompareResult r = compareChord(notes, j, baseNote);
                    if(r.extra.size() <= 0) {
                        returnChords.add(r);
                    }
                }
            }
        }
        Collections.sort(returnChords);

        return returnChords;
    }

    private static CompareResult compareChord(List<Integer> notes, int chordIndex, int baseNote){
        CompareResult result = new CompareResult();
        result.root = baseNote;
        result.chordId = chordIndex;
        result.missing = new ArrayList<>();
        result.found = new ArrayList<>();
        result.extra = new ArrayList<>();

        for(int i = 0; i < Music.CHORDS[chordIndex].intervals.length; i++) {
            int interval = Music.CHORDS[chordIndex].intervals[i];
            result.missing.add(interval);
        }

        for(int n = 0; n < notes.size(); n++) {
            boolean found = false;

            for(int i = 0; i < result.missing.size() && !found; i++) {
                if(notes.get(n) == getNoteNumber(result.missing.get(i))) {
                    found = true;
                    result.found.add(result.missing.get(i));
                    result.missing.remove(i);
                }
            }
            if(!found) {
                result.extra.add(n);
            }
        }

        return result;
    }

    private static int getNoteNumber(int n){
        while(n < 0){
            n += Music.NOTE_COUNT;
        }
        return n % Music.NOTE_COUNT;
    }
}
