package main.button;

import main.FederalPoliceOffice.FederalPoliceOffice;
import main.FederalPoliceOffice.Robot;
import main.configuration.Configuration;

public class RemoteControl extends Button {
    private FederalPoliceOffice federalPoliceOffice;

    public RemoteControl(FederalPoliceOffice federalPoliceOffice){
        this.federalPoliceOffice = federalPoliceOffice;
    }

    public void buttonAction(){
        Robot robot = federalPoliceOffice.getRobots().get(Configuration.instance.mersenneTwister.nextInt(2));
        robot.defuse();
    }
}
