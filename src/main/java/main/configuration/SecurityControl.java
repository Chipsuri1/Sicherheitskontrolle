package main.configuration;

import main.baggageScanner.BaggageScanner;
import main.baggageScanner.Tray;
import main.employee.HouseKeeper;
import main.employee.Technician;
import main.passenger.HandBaggage;
import main.passenger.Passenger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class SecurityControl {

    private Queue<Passenger> passengerList = new LinkedList<>();
    private List<HandBaggage> handBaggages = new LinkedList<>();
    private BaggageScanner baggageScanner = new BaggageScanner();


    public SecurityControl(){
        initPassengers();
    }

    public void checkPassengers(){
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "5000");
        for(int i = 0; i < Configuration.instance.NUMBER_OF_PASSENGERS; i++){
            Passenger passenger = passengerList.poll();
//            System.out.println("It´s "+ passenger.getName()+" turn, handbaggages are put into trays!");
            for(int j = 0; j < passenger.getHandBaggage().length; j++){
                handBaggages.add(passenger.getHandBaggage()[j]);
                Tray tray = new Tray((passenger.getHandBaggage()[j]));
                passenger.getHandBaggage()[j].setTray(tray);
                baggageScanner.getRollerConveyor().getTrays().add(tray);
                passenger.getHandBaggage()[j] = null;
            }
//            System.out.println("Now Handbaggages of "+ passenger.getName()+" get scanned!");
            baggageScanner.scanHandBaggage();

        }

    }


    private void initPassengers(){
        for(int i = 0; i < Configuration.instance.NUMBER_OF_PASSENGERS; i++){
            String[] content = Configuration.instance.fileReader.readContent(i, Configuration.instance.DATA_FILEPATH);

            String name = content[0];
            HandBaggage[] handBaggage = Configuration.instance.dataGenerator.generateBaggage(Integer.valueOf(content[1]), content[2]);
            Passenger passenger = new Passenger(name, handBaggage);
            Arrays.stream(handBaggage).forEach(handBaggage1 -> handBaggage1.setPassenger(passenger));
            passengerList.offer(passenger);
        }
        System.out.println();
    }

    public Queue<Passenger> getPassengerList() {
        return passengerList;
    }

    public BaggageScanner getBaggageScanner() {
        return baggageScanner;
    }

    public List<HandBaggage> getHandBaggages() {
        return handBaggages;
    }
}
