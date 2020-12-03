package main.configuration;

import main.baggageScanner.BaggageScanner;
import main.baggageScanner.Tray;
import main.employee.HouseKeeper;
import main.employee.Technician;
import main.passenger.HandBaggage;
import main.passenger.Passenger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


public class SecurityControl {

    private Queue<Passenger> passengerList = new LinkedList<>();
    private BaggageScanner baggageScanner = new BaggageScanner();

    private Technician technician = new Technician(6, "Jason Statham", "26.07.1967");
    private HouseKeeper houseKeeper = new HouseKeeper(7, "Jason Clarke", "17.07.1969");

    public SecurityControl(){
        initPassengers();
    }

    public void checkPassengers(){
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "5000");
        for(int i = 0; i < Configuration.instance.NUMBER_OF_PASSENGERS; i++){
            Passenger passenger = passengerList.poll();
            System.out.println("It´s "+ passenger.getName()+" turn, he puts his handbaggage in a tray!");
            for(int j = 0; j < passenger.getHandBaggage().length; j++){
                Tray tray = new Tray((passenger.getHandBaggage()[j]));
                passenger.getHandBaggage()[j].setTray(tray);
                baggageScanner.getRollerConveyor().getTrays().add(tray);
                passenger.getHandBaggage()[j] = null;
            }
            System.out.println("Now his Handbaggages get scanned!");
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

}
