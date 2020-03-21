package fi.sntr.fretboard;

public class Instrument {

    public static String[] noteNamesSharp = {
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };

    private int fretCount;
    private int[] rootNotes;
    private int[] selectedFrets;

    public Instrument(int fretCount, int ...rootNotes) {
        this.rootNotes = rootNotes;
        this.selectedFrets = new int[rootNotes.length];
        this.fretCount = fretCount;
    }

    public int getNoteCount() {
        return fretCount * rootNotes.length;
    }

    public String getNote(int string, int fret) {
        int note = (rootNotes[string] + fret) % noteNamesSharp.length;
        return noteNamesSharp[note];
    }

    public int getFretCount() {
        return fretCount;
    }
}
