package main.employee;

import main.MagnetStripe;
import main.ProfilType;
import main.Status;
import main.baggageScanner.BaggageScanner;

import static main.Type.staff;

public class Supervisor extends Employee {

    private boolean isSenior;
    private boolean isExecutive;

    public Supervisor(int id, String name, String birthDate, boolean isSenior, boolean isExecutive) {
        super(id, name, birthDate);

        this.isExecutive = isExecutive;
        this.isSenior = isSenior;

        getIdCard().setType(staff);
        getIdCard().setMagnetStripe(new MagnetStripe(ProfilType.S, getIdCard().getSecretKey()));
    }


    public void unlock(BaggageScanner baggageScanner) {
        baggageScanner.setStatus(Status.activated);
    }
}
