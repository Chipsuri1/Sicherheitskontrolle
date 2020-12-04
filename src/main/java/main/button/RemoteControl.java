package main.button;

import main.FederalPoliceOffice.FederalPoliceOffice;
import main.FederalPoliceOffice.Robot;
import main.configuration.Configuration;
import main.employee.Employee;
import main.employee.FederalPoliceOfficer;

public class RemoteControl extends Button {
    private FederalPoliceOffice federalPoliceOffice;

    public RemoteControl(FederalPoliceOffice federalPoliceOffice) {
        this.federalPoliceOffice = federalPoliceOffice;
    }

    public boolean buttonAction(Employee employee) {
        if (employee instanceof FederalPoliceOfficer) {
            Robot robot = federalPoliceOffice.getRobots().get(Configuration.instance.mersenneTwister.nextInt(2));
            robot.defuse();
            return true;
        }else {
            return false;
        }
    }
}
