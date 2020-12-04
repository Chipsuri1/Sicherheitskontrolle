package main.baggageScanner.components;

import main.baggageScanner.Status;
import main.baggageScanner.components.BaggageScanner;
import main.employee.Supervisor;

public class Supervision {

    private Supervisor supervisor = new Supervisor(4, "Jodie Foster","19.11.1962", false, false);

    public void unlockBaggageScanner(BaggageScanner baggageScanner){
        baggageScanner.setStatus(Status.activated);
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }
}
