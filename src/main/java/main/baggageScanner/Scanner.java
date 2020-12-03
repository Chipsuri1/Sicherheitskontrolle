package main.baggageScanner;

import main.Record;
import main.Result;
import main.ScanResult;
import main.Status;
import main.configuration.Configuration;
import main.passenger.Layer;

import java.util.*;

public class Scanner {

    private Queue<Tray> trays = new LinkedList<>();
    private Queue<Record> records = new LinkedList<>();
    private BaggageScanner baggageScanner;

    public Scanner(BaggageScanner baggageScanner){
        this.baggageScanner = baggageScanner;
    }

    public void startScanning(){
        baggageScanner.setStatus(Status.inUse);

        for(Tray tray : trays){

            StringBuilder stringBuilder = new StringBuilder();

            for(Layer layer : tray.getHandBaggage().getLayers()){
                for(int j = 0; j < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; j++){
                    stringBuilder.append(layer.getContent()[j]);
                }
            }

            List<String> patterns = new ArrayList<>(Arrays.asList("K", "W", "E"));

            String position = null;
            Record record = null;

            for(String pattern : patterns) {
                switch (Configuration.instance.searchType) {
                    case BoyerMoore:
                        position = Configuration.instance.boyerMoore.search(stringBuilder.toString(), pattern);
                        break;
                    case KnuthMorrisPratt:
                        position = Configuration.instance.knuthMorrisPratt.search(stringBuilder.toString(), pattern);
                        break;
                }


                if(position.equals("clean")){
                    record = new Record(new Result(ScanResult.clean, position));
                }else{
                    ScanResult scanResult = null;
                    String result = null;

                    switch (pattern){
                        case "K":
                            scanResult = ScanResult.knife;
                            result = "knife";
                            break;
                        case "W":
                            scanResult = ScanResult.weapon;
                            result = "weapon - glock7";
                            break;
                        case "E":
                            scanResult = ScanResult.explosive;
                            result = "explosive";
                    }
                    String string = "prohibited item | " + result + " detected at position " + position;
                    System.out.println(string);
                    record = new Record(new Result(scanResult, string));
                    break;
                }


            }

            records.add(record);
            tray.setRecord(record);
        }

        baggageScanner.setStatus(Status.activated);
    }

    public Queue<Record> getRecords() {
        return records;
    }

    public Queue<Tray> getTrays() {
        return trays;
    }
}
