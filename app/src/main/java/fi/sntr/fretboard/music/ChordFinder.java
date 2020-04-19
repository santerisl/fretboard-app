package fi.sntr.fretboard.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChordFinder {

    public static List<CompareResult> findChords(Instrument instrument){
        List<CompareResult> returnChords = new ArrayList<>();
        Set<Integer> baseNotes = new HashSet<>();
        for(int i = 0; i < instrument.getStringCount(); i++){
            int baseNote = instrument.getSelectedNoteNumber(i);
            if(baseNote >= 0 && baseNotes.add(baseNote)) {
                int bits = getBits(instrument, baseNote);
                for(int j = 0; j < Music.CHORDS.length; j++){
                    CompareResult cR = compareChord(bits, j, baseNote);
                    if(cR.missingCount <= 12 && !cR.hasExtraNotes()) {
                        returnChords.add(cR);
                    }
                }
            }
        }

        Collections.sort(returnChords);
        return returnChords;
    }

    private static CompareResult compareChord(int bits, int chordIndex, int baseNote){
        int chordBits = Music.CHORDS[chordIndex].getBits();
        int found = bits & chordBits;
        int missing = chordBits ^ found;
        int extra = bits ^ found;
        return new CompareResult(baseNote, chordIndex, found, missing, extra);
    }

    private static int getBits(Instrument instrument, int baseNote) {
        int bits = 0;
        for(int i = 0; i < instrument.getStringCount(); i++) {
            int note = instrument.getSelectedNoteNumber(i);
            if(note >= 0) {
                bits = bits | 1 << Music.getNoteNumber(note - baseNote);
            }
        }
        return bits;
    }
}
