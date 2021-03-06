package main.employee;

import main.FederalPoliceOffice.FederalPoliceOffice;
import main.baggageScanner.Tray;
import main.button.RemoteControl;
import main.passenger.HandBaggage;
import main.passenger.Layer;
import main.passenger.Passenger;

import java.util.ArrayList;
import java.util.List;

import static main.employee.Type.external;

public class FederalPoliceOfficer extends Employee {

    private FederalPoliceOffice federalPoliceOffice;
    private ArrayList<Character> things;
    private List<HandBaggage> baggagesOfArrested;
    private Passenger passenger;
    private RemoteControl remoteControl;


    public FederalPoliceOfficer(int id, String name, String birthDate, FederalPoliceOffice federalPoliceOffice) {
        super(id, name, birthDate);

        this.federalPoliceOffice = federalPoliceOffice;
        this.remoteControl = new RemoteControl(federalPoliceOffice);
        getIdCard().setType(Type.external);
        getIdCard().setMagnetStripe(new MagnetStripe(ProfilType.O, getIdCard().getSecretKey()));
        baggagesOfArrested = new ArrayList<>();
        things = new ArrayList<>();
    }


    public void arrest(Passenger passenger) {
        if(!federalPoliceOffice.getPrison().contains(passenger)) {
            federalPoliceOffice.getPrison().add(passenger);
        }
    }

    public void openHandBaggageGetWeaponAndGiveToOfficer03(Tray tray) {
        for (Layer layer : tray.getHandBaggage().getLayers()) {
            for (int i = 0; i < layer.getContent().length; i++) {
                if (layer.getContent()[i] == 'W'){
                    federalPoliceOffice.getFederalPoliceOfficerO3().addThing(layer.getContent()[i]);
                    layer.getContent()[i] = 'A';
                }
            }
        }

    }

    public void addThing(char thing) {
        this.things.add(thing);
    }

    public List<HandBaggage> getBaggagesOfArrested() {
        return baggagesOfArrested;
    }

    public FederalPoliceOffice getFederalPoliceOffice() {
        return federalPoliceOffice;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public RemoteControl getRemoteControl() {
        return remoteControl;
    }
}
