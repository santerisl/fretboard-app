package fi.sntr.fretboard.music;

import android.util.Log;

import java.util.Map;
import java.util.LinkedHashMap;

public class Music {

    public Music() {
        Log.d("Debug", "Music contructor");
    }

    static final int NOTE_COUNT = 12;

    static final String[] NAMES_SHARP = {
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };

    static final String[] NAMES_FLAT = {
            "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"
    };

    public static String[] INTERVALS = {
            "1", "b2", "2", "b3", "3", "4", "b5", "5", "b6", "6", "b7", "7",
            "8", "b9", "9", "b10", "10", "11", "b12", "12", "b13", "13"
    };

    public static Map<String, int[]> CHORDS = createChordsMap();

    public static Map<String, int[]> SCALES = createScalesMap();

    private static Map<String, int[]> createChordsMap() {
        Map<String, int[]> chords = new LinkedHashMap<>();
        add(chords, "", 0, 4, 7);
        add(chords, "m", 0, 3, 7);
        add(chords, "dim", 0, 3, 6);
        add(chords, "dim7", 0, 3, 6, 9);
        add(chords, "m7b5", 0, 3, 6, 10);
        add(chords, "aug", 0, 4, 8);
        add(chords, "5", 0, 7);
        add(chords, "7", 0, 4, 7, 10);
        add(chords, "m7", 0, 3, 7, 10);
        add(chords, "maj7", 0, 4, 7, 11);
        add(chords, "m/maj7", 0, 3, 7, 11);
        add(chords, "sus4", 0, 5, 7);
        add(chords, "sus2", 0, 2, 7);
        add(chords, "7sus4", 0, 5, 7, 10);
        add(chords, "7sus2", 0, 2, 7, 10);
        add(chords, "add2", 0, 2, 4, 7);
        add(chords, "add9", 0, 4, 7, 14);
        add(chords, "add4", 0, 4, 5, 7);
        add(chords, "6", 0, 4, 7, 9);
        add(chords, "m6", 0, 3, 7, 9);
        add(chords, "6/9", 0, 4, 7, 9, 14);
        add(chords, "9", 0, 4, 7, 10, 14);
        add(chords, "m9", 0, 3, 7, 10, 14);
        add(chords, "maj9", 0, 4, 7, 11, 14);
        add(chords, "11", 0, 4, 7, 10, 14, 17);
        add(chords, "m11", 0, 3, 7, 10, 14, 17);
        add(chords, "maj11", 0, 4, 7, 11, 14, 17);
        add(chords, "13", 0, 4, 7, 10, 14, 17, 21);
        add(chords, "m13", 0, 3, 7, 10, 14, 17, 21);
        add(chords, "maj13", 0, 4, 7, 11, 14, 17, 21);
        add(chords, "7#9", 0, 4, 7, 10, 15);
        add(chords, "7b9", 0, 4, 7, 10, 13);
        add(chords, "7#5", 0, 4, 8, 10);
        add(chords, "7b5", 0, 4, 6, 10);
        return chords;
    }

    private static Map<String, int[]> createScalesMap() {
        Map<String, int[]> scales = new LinkedHashMap<>();
        add(scales, "Chromatic", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        add(scales, "Major", 0, 2, 4, 5, 7, 9, 11);
        add(scales, "Dorian", 0, 2, 3, 5, 7, 9, 10);
        add(scales, "Phrygian", 0, 1, 3, 5, 7, 8, 10);
        add(scales, "Lydian", 0, 2, 4, 6, 7, 9, 11);
        add(scales, "Mixolydian", 0, 2, 4, 5, 7, 9, 10);
        add(scales, "Aeolian", 0, 2, 3, 5, 7, 8, 10);
        add(scales, "Locrian", 0, 1, 3, 5, 6, 8, 10);
        add(scales, "Minor", 0, 2, 3, 5, 7, 8, 10);
        add(scales, "Harmonic minor", 0, 2, 3, 5, 7, 8, 11);
        add(scales, "Melodic minor", 0, 2, 3, 5, 7, 8, 9, 10, 11);
        add(scales, "Pentatonic", 0, 2, 4, 7, 9);
        add(scales, "Minor pentatonic", 0, 3, 5, 7, 10);
        return scales;
    }

    private static void add(Map<String, int[]> map, String name, int... notes) {
        map.put(name, notes);
    }
}
