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

    public int getRootNote(int position) {
        return rootNotes[(position - fretCount) / fretCount];
    }

    public int getFret(int position) {
        return (position - fretCount) % fretCount;
    }

    public int getNoteCount() {
        return fretCount * rootNotes.length;
    }

    public String getNote(int position) {
        int note = getRootNote(position) + getFret(position);
        return noteNamesSharp[note % noteNamesSharp.length];
    }

    public int getFretCount() {
        return fretCount;
    }
}
