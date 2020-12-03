package main.baggageScanner;

import main.Status;
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
