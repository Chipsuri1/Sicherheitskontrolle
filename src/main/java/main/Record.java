package main;

import java.sql.Timestamp;
import java.util.UUID;

public class Record {

    private UUID id;
    private Timestamp timeStamp;
    private Result result;

    public Record(Result result){
        this.result = result;
        this.id = UUID.randomUUID();
        this.timeStamp = new Timestamp(System.currentTimeMillis());
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
}
