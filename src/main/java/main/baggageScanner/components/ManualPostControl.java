package main.baggageScanner.components;

import main.FederalPoliceOffice.ExplosiveTraceDetector;
import main.FederalPoliceOffice.Result;
import main.FederalPoliceOffice.ScanResult;
import main.FederalPoliceOffice.TestStripe;
import main.baggageScanner.Record;
import main.baggageScanner.Tray;
import main.baggageScanner.components.BaggageScanner;
import main.employee.FederalPoliceOfficer;
import main.employee.Inspector;
import main.employee.Supervisor;
import main.passenger.HandBaggage;
import main.passenger.Passenger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ManualPostControl {

    private Inspector inspectorI3 = new Inspector(3, "Bruce Willis", "19.03.1955", true);
    private ExplosiveTraceDetector explosiveTraceDetector = new ExplosiveTraceDetector();

    private Passenger passengerInPresence;
    private Supervisor supervisorInPresence;
    private Tray tray;


    public Inspector getInspectorI3() {
        return inspectorI3;
    }

    public ExplosiveTraceDetector getExplosiveTraceDetector() {
        return explosiveTraceDetector;
    }

    public void makeManuelPostControll(BaggageScanner baggageScanner) {
        getInspectorI3().putOnTrack1(baggageScanner, tray);
        baggageScanner.getTrack1().getTrays().remove(tray);
        doManualPostControl(baggageScanner);
    }

    public void doManualPostControl(BaggageScanner baggageScanner) {
        Record record = tray.getRecord();
        //Knife
        if (record.getResult().getScanResult().equals(ScanResult.knife)) {
            System.out.println("knife");
            baggageScanner.getOperatingStation().getInspectorI2().tellOtherInspector(getInspectorI3(), record);
            passengerInPresence = tray.getHandBaggage().getPassenger();
            getInspectorI3().openBaggageGetKnifeAndThrowAway(tray.getHandBaggage());

            getInspectorI3().putTrayToBelt(tray, baggageScanner);

            baggageScanner.scanHandBaggage();
            passengerInPresence = null;

        }
        //Weapon or Explosive
        else if (record.getResult().getScanResult().equals(ScanResult.weapon) || record.getResult().getScanResult().equals(ScanResult.explosive)) {
            System.out.println("weapon or eplosive");
            baggageScanner.getOperatingStation().getInspectorI2().setAlarm(baggageScanner);
            baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1().arrest(tray.getHandBaggage().getPassenger());
            tray.getHandBaggage().getPassenger().setArrested(true);
            FederalPoliceOfficer officer1InPresence = baggageScanner.getFederalPoliceOffice().reqestOfficer1(baggageScanner);
            FederalPoliceOfficer officer2InPresence = baggageScanner.getFederalPoliceOffice().reqestOfficer2(baggageScanner);
            FederalPoliceOfficer officer3InPresence = baggageScanner.getFederalPoliceOffice().reqestOfficer3(baggageScanner);

            if (record.getResult().getScanResult().equals(ScanResult.explosive)) {
                System.out.println("explosive");
                TestStripe testStripe = new TestStripe();
                inspectorI3.swipeTestStripe(testStripe);
                inspectorI3.putTestStripeIntoExplosiveTraceDetector(baggageScanner.getManualPostControl().getExplosiveTraceDetector(), testStripe);

                officer2InPresence.push(officer2InPresence.getRemoteControl());
//
//                officer2InPresence.workWithRobot(tray.getHandBaggage());
            } else if (record.getResult().getScanResult().equals(ScanResult.weapon)) {
                //weapon
                System.out.println("weapon");
                passengerInPresence = tray.getHandBaggage().getPassenger();
                supervisorInPresence = baggageScanner.getSupervision().getSupervisor();
                officer1InPresence.openHandBaggageGetWeaponAndGiveToOfficer03(tray);


                ArrayList<HandBaggage> handBaggageOfPassenger = new ArrayList<>();
                handBaggageOfPassenger.add(tray.getHandBaggage());
                for (Tray tray : baggageScanner.getTrack2().getTrays()) {
                    if (tray.getHandBaggage().getPassenger().equals(passengerInPresence)) {
                        handBaggageOfPassenger.add(tray.getHandBaggage());
                        baggageScanner.getTrack2().getTrays().remove(tray);
//                        break;
                    }
                }
                for (Tray tray : baggageScanner.getScanner().getTrays()){
                    if(tray.getHandBaggage().getPassenger().equals(passengerInPresence)){

//                        tray.setRecord(new Record(new Result(ScanResult.explosive, "0"), tray.getHandBaggage()));
                        getInspectorI3().putTrayToBelt(tray, baggageScanner);
                        baggageScanner.scanHandBaggage();
                        baggageScanner.getScanner().getTrays().remove(tray);
                    }
                }
                officer1InPresence.getFederalPoliceOffice().getFederalPoliceOfficerO3().getBaggagesOfArrested().addAll(handBaggageOfPassenger);

                officer1InPresence.getFederalPoliceOffice().getFederalPoliceOfficerO3().setPassenger(passengerInPresence);
                officer2InPresence = null;
                officer3InPresence = null;
                passengerInPresence = null;

            }
            supervisorInPresence.unlock(baggageScanner);
        } else {
            //NO ILLEGAL ITEM
        }
    }

    public void setTray(Tray tray) {
        this.tray = tray;
    }

    public Tray getTray() {
        return tray;
    }

}
