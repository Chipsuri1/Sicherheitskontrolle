package main.baggageScanner;

import main.Record;
import main.passenger.HandBaggage;

public class Tray {
    private HandBaggage handBaggage;
    private Record record;

    public Tray(HandBaggage handBaggage){
        this.handBaggage = handBaggage;
    }

    public HandBaggage getHandBaggage() {
        return handBaggage;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public void setHandBaggage(HandBaggage handBaggage) {
        this.handBaggage = handBaggage;
    }
}
