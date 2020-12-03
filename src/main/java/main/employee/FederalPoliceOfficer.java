package main.employee;

import main.FederalPoliceOffice;
import main.MagnetStripe;
import main.ProfilType;
import main.Robot;
import main.baggageScanner.Tray;
import main.configuration.Configuration;
import main.passenger.HandBaggage;
import main.passenger.Layer;
import main.passenger.Passenger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static main.Type.external;

public class FederalPoliceOfficer extends Employee {

    private FederalPoliceOffice federalPoliceOffice;
    private char thing;
    private List<HandBaggage> baggagesOfArrested;
    private Passenger passenger;


    public FederalPoliceOfficer(int id, String name, String birthDate, FederalPoliceOffice federalPoliceOffice) {
        super(id, name, birthDate);

        this.federalPoliceOffice = federalPoliceOffice;
        getIdCard().setType(external);
        getIdCard().setMagnetStripe(new MagnetStripe(ProfilType.O, getIdCard().getSecretKey()));
        baggagesOfArrested = new ArrayList<>();
    }


    public void arrest(Passenger passenger) {

    }

    public void workWithRobot() {
        Robot robot = federalPoliceOffice.getRobots().get(Configuration.instance.mersenneTwister.nextInt(2));
        robot.defuse();
    }

    public void openHandBaggageGetWeaponAndGiveToOfficer03(Tray tray) {
        for (Layer layer : tray.getHandBaggage().getLayers()) {
            for (int i = 0; i < layer.getContent().length; i++) {
                if (layer.getContent()[i] == 'W'){
                    federalPoliceOffice.getFederalPoliceOfficerO3().setThing(layer.getContent()[i]);
                    layer.getContent()[i] = 'A';
                }
            }
        }

    }

    public void setThing(char thing) {
        this.thing = thing;
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
}
