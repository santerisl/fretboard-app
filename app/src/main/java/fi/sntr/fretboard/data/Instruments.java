package fi.sntr.fretboard.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing instrument data
 */
public class Instruments {
    private final List<InstrumentData> instrumentDataList;
    /** Count of default instruments in the instrument list */
    public static final int DEFAULT_INSTRUMENT_COUNT = 2;
    /** Id of currently selected instrument */
    public int selectedId = 0;

    public Instruments() {
        instrumentDataList = new ArrayList<>();
        instrumentDataList.add(new InstrumentData("Guitar", 4, 11, 7, 2, 9, 4));
        instrumentDataList.add(new InstrumentData("Mandolin", 4, 9, 2, 7));

    }

    /**
     * Add the given list of custom instrument data to the list of instruments
     * @param customInstruments instrument data to add
     */
    public void addCustomInstruments(List<InstrumentData> customInstruments) {
        this.instrumentDataList.addAll(customInstruments);
    }

    /**
     * Add the given custom instrument data to the list of instruments
     * @param instrumentData instrument data to add
     */
    public void addCustomInstrument(InstrumentData instrumentData) {
        this.instrumentDataList.add(instrumentData);
    }

    /**
     * @param instrumentId id of instrument to get
     * @return instrument with the given id
     */
    public InstrumentData get(int instrumentId) {
        return instrumentDataList.get(instrumentId);
    }

    /**
     * @return the currently selected instrument
     */
    public InstrumentData get() {
        return get(selectedId);
    }

    /**
     * Remove the instrument with the given id
     *
     * Does nothing if the given id is lower than the default instrument count
     *
     * @param id id of instrument to remove
     */
    public void remove(int id) {
        if(id >= DEFAULT_INSTRUMENT_COUNT) {
            instrumentDataList.remove(id);
        }
    }

    /**
     * @return count of instruments saved in this class
     */
    public int count() {
        return instrumentDataList.size();
    }
}
