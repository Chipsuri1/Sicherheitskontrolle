package main.employee;

import main.MagnetStripe;
import main.ProfilType;
import main.baggageScanner.BaggageScanner;

import static main.Type.external;

public class Technician extends Employee {

    private BaggageScanner baggageScanner;

    public Technician(int id, String name, String birthDate, BaggageScanner baggageScanner) {
        super(id, name, birthDate);

        getIdCard().setType(external);
        getIdCard().setMagnetStripe(new MagnetStripe(ProfilType.T, getIdCard().getSecretKey()));
        this.baggageScanner = baggageScanner;
    }

    public void maintenance(){
        baggageScanner.maintenance(this);
    }

}
