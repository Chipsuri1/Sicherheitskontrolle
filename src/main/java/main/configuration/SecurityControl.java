package main.configuration;

import main.baggageScanner.components.BaggageScanner;
import main.baggageScanner.Tray;
import main.passenger.HandBaggage;
import main.passenger.Passenger;

import java.util.*;


public class SecurityControl {

    private Queue<Passenger> passengerList = new LinkedList<>();
    private List<HandBaggage> handBaggage = new LinkedList<>();
    private BaggageScanner baggageScanner = new BaggageScanner();


    public SecurityControl() {
        initPassengers();
    }

    public void checkPassengers() {
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "5000");
        for (int i = 0; i < Configuration.instance.NUMBER_OF_PASSENGERS; i++) {
            Passenger passenger = passengerList.poll();

            for (int j = 0; j < passenger.getHandBaggage().length; j++) {
                Tray tray = new Tray((passenger.getHandBaggage()[j]));
                passenger.getHandBaggage()[j].setTray(tray);
                baggageScanner.getRollerConveyor().getTrays().add(tray);
                passenger.getHandBaggage()[j] = null;
            }
            baggageScanner.scanHandBaggage();

        }
        System.out.println("Size " + baggageScanner.getTrack2().getTrays().size());
        baggageScanner.getTechnician().maintenance();

    }


    private void initPassengers() {
        for (int i = 0; i < Configuration.instance.NUMBER_OF_PASSENGERS; i++) {
            String[] content = Configuration.instance.fileReader.readContent(i, Configuration.instance.DATA_FILEPATH);
            if (i == 149) {
                System.out.println();
            }

            if (content.length == 4) {
                content[2] += ";" + content[3];
            }

            String name = content[0];
            HandBaggage[] handBaggage = Configuration.instance.dataGenerator.generateBaggage(Integer.valueOf(content[1]), content[2]);
            Passenger passenger = new Passenger(name, handBaggage);
            List<HandBaggage> handBaggageList = Arrays.asList(passenger.getHandBaggage());
            this.handBaggage.addAll(handBaggageList);
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

    public List<HandBaggage> getHandBaggage() {
        return handBaggage;
    }
}
