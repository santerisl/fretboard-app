package fi.sntr.fretboard.data;

import fi.sntr.fretboard.music.Music;

/**
 * Class for storing instrument data
 */
public class InstrumentData {
    private final String name;
    private final int[] strings;

    public InstrumentData(String name, int... strings) {
        this.name = name;
        this.strings = strings;
    }

    /**
     * @return the root notes of the instruments string
     */
    public int[] getStrings() {
        return strings;
    }

    /**
     * @return the name of the instrument
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the names of all the root notes of the instruments strings in a dash separated string
     * @return note names of the instruments strings root notes
     */
    public String getStringsText() {
        StringBuilder b = new StringBuilder();
        for (int i = strings.length - 1; i >= 0; i--) {
            if (i < strings.length - 1) {
                b.append("-");
            }
            b.append(Music.getNoteName(strings[i]));
        }
        return b.toString();
    }
}
