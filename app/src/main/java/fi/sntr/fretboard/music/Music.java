package fi.sntr.fretboard.music;

/**
 * Holder class for Music related static data
 */
public class Music {

    /**
     * Total count of notes
     */
    static final int NOTE_COUNT = 12;

    /** Note names with the sharp (#) accidental */
    public static final String[] NAMES_FLAT = {
            "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"
    };

    /** Note names with the flat (b) accidental */
    public static final String[] NAMES_SHARP = {
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };

    /** Interval names with the flat (b) accidental */
    public static final String[] INTERVALS = {
            "1", "b2", "2", "b3", "3", "4", "b5", "5", "b6", "6", "b7", "7",
            "8", "b9", "9", "b10", "10", "11", "b12", "12", "b13", "13"
    };

    /** Array of chord names and chord note intervals */
    public static final NoteGroup[] CHORDS = {
            new NoteGroup("", 0, 4, 7),
            new NoteGroup("m", 0, 3, 7),
            new NoteGroup("dim", 0, 3, 6),
            new NoteGroup("dim7", 0, 3, 6, 9),
            new NoteGroup("m7b5", 0, 3, 6, 10),
            new NoteGroup("aug", 0, 4, 8),
            new NoteGroup("5", 0, 7),
            new NoteGroup("7", 0, 4, 7, 10),
            new NoteGroup("m7", 0, 3, 7, 10),
            new NoteGroup("maj7", 0, 4, 7, 11),
            new NoteGroup("m/maj7", 0, 3, 7, 11),
            new NoteGroup("sus4", 0, 5, 7),
            new NoteGroup("sus2", 0, 2, 7),
            new NoteGroup("7sus4", 0, 5, 7, 10),
            new NoteGroup("7sus2", 0, 2, 7, 10),
            new NoteGroup("add2", 0, 2, 4, 7),
            new NoteGroup("add9", 0, 4, 7, 14),
            new NoteGroup("add4", 0, 4, 5, 7),
            new NoteGroup("6", 0, 4, 7, 9),
            new NoteGroup("m6", 0, 3, 7, 9),
            new NoteGroup("6/9", 0, 4, 7, 9, 14),
            new NoteGroup("9", 0, 4, 7, 10, 14),
            new NoteGroup("m9", 0, 3, 7, 10, 14),
            new NoteGroup("maj9", 0, 4, 7, 11, 14),
            new NoteGroup("11", 0, 4, 7, 10, 14, 17),
            new NoteGroup("m11", 0, 3, 7, 10, 14, 17),
            new NoteGroup("maj11", 0, 4, 7, 11, 14, 17),
            new NoteGroup("13", 0, 4, 7, 10, 14, 17, 21),
            new NoteGroup("m13", 0, 3, 7, 10, 14, 17, 21),
            new NoteGroup("maj13", 0, 4, 7, 11, 14, 17, 21),
            new NoteGroup("7#9", 0, 4, 7, 10, 15),
            new NoteGroup("7b9", 0, 4, 7, 10, 13),
            new NoteGroup("7#5", 0, 4, 8, 10),
            new NoteGroup("7b5", 0, 4, 6, 10)
    };

    /** Array of scale names and scale note intervals */
    public static final NoteGroup[] SCALES = {
            new NoteGroup("Chromatic", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
            new NoteGroup("Major", 0, 2, 4, 5, 7, 9, 11),
            new NoteGroup("Dorian", 0, 2, 3, 5, 7, 9, 10),
            new NoteGroup("Phrygian", 0, 1, 3, 5, 7, 8, 10),
            new NoteGroup("Lydian", 0, 2, 4, 6, 7, 9, 11),
            new NoteGroup("Mixolydian", 0, 2, 4, 5, 7, 9, 10),
            new NoteGroup("Aeolian", 0, 2, 3, 5, 7, 8, 10),
            new NoteGroup("Locrian", 0, 1, 3, 5, 6, 8, 10),
            new NoteGroup("Minor", 0, 2, 3, 5, 7, 8, 10),
            new NoteGroup("Harmonic minor", 0, 2, 3, 5, 7, 8, 11),
            new NoteGroup("Melodic minor", 0, 2, 3, 5, 7, 8, 9, 10, 11),
            new NoteGroup("Pentatonic", 0, 2, 4, 7, 9),
            new NoteGroup("Minor pentatonic", 0, 3, 5, 7, 10)
    };

    /**
     * @param n note which number to get
     * @return the note number in range of 0 to {@link Music#NOTE_COUNT}
     */
    public static int getNoteNumber(int n) {
        while(n < 0){
            n += NOTE_COUNT;
        }
        return n % NOTE_COUNT;
    }

    /**
     * @param note note name to get
     * @return return the sharp (#) name of the given note
     */
    public static String getNoteName(int note) {
        return Music.NAMES_SHARP[getNoteNumber(note)];
    }
}
