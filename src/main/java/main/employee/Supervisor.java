package main.employee;

import main.baggageScanner.components.BaggageScanner;

import static main.employee.Type.staff;

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
        baggageScanner.unlock(this);
        baggageScanner.getOperatingStation().getCardReader().setAmountOfTries(0);
    }
}
