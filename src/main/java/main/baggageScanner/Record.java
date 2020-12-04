package main.baggageScanner;

import main.FederalPoliceOffice.Result;

import main.passenger.HandBaggage;

import java.sql.Timestamp;
import java.util.UUID;

public class Record {

    private UUID id;
    private Timestamp timeStamp;
    private Result result;
    private HandBaggage handBaggage;

    public Record(Result result, HandBaggage handBaggage){
        this.result = result;
        this.id = UUID.randomUUID();
        this.timeStamp = new Timestamp(System.currentTimeMillis());
        this.handBaggage = handBaggage;
    }

       public Result getResult() {
        return result;
    }

    public UUID getId() {
        return id;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public HandBaggage getHandBaggage() {
        return handBaggage;
    }
}
