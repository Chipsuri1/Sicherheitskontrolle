package main.baggageScanner;

import main.*;
import main.Record;
import main.employee.FederalPoliceOfficer;
import main.employee.Inspector;
import main.employee.Supervisor;
import main.passenger.HandBaggage;
import main.passenger.Passenger;

public class ManualPostControl {

    private Inspector inspectorI3 = new Inspector(3, "Bruce Willis", "19.03.1955", true);
    private ExplosiveTraceDetector explosiveTraceDetector = new ExplosiveTraceDetector();

    private Passenger passengerInPresence;
    private Supervisor supervisorInPresence;


    public Inspector getInspectorI3() {
        return inspectorI3;
    }

    public ExplosiveTraceDetector getExplosiveTraceDetector() {
        return explosiveTraceDetector;
    }

    public void makeManuelPostControll(BaggageScanner baggageScanner, Tray tray) {
        getInspectorI3().putOnTrack1(baggageScanner, tray);
        baggageScanner.getTrack1().getTrays().remove(tray);
        doManualPostControl(baggageScanner, tray);
    }

    public void doManualPostControl(BaggageScanner baggageScanner, Tray tray) {
        Record record = tray.getRecord();
        //Knife
        if (record.getResult().getScanResult().equals(ScanResult.knife)) {
            baggageScanner.getOperatingStation().getInspectorI2().tellOtherInspector(getInspectorI3(), record);
            passengerInPresence = tray.getHandBaggage().getPassenger();
            getInspectorI3().openBaggageGetKnifeAndThrowAway(tray.getHandBaggage());

            getInspectorI3().putTrayToBelt(tray, baggageScanner);

            baggageScanner.scanHandBaggage();
            passengerInPresence = null;

        }
        //Weapon or Explosive
        else if (record.getResult().getScanResult().equals(ScanResult.weapon) || record.getResult().getScanResult().equals(ScanResult.explosive)) {
            baggageScanner.getOperatingStation().getInspectorI2().setAlarm(baggageScanner);
            baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1().arrest(tray.getHandBaggage().getPassenger());
            FederalPoliceOfficer officer1InPresence = baggageScanner.getFederalPoliceOffice().reqestOfficer1(baggageScanner);
            FederalPoliceOfficer officer2InPresence = baggageScanner.getFederalPoliceOffice().reqestOfficer2(baggageScanner);
            FederalPoliceOfficer officer3InPresence = baggageScanner.getFederalPoliceOffice().reqestOfficer3(baggageScanner);

            if (record.getResult().getScanResult().equals(ScanResult.explosive)) {

                TestStripe testStripe = new TestStripe();
                inspectorI3.swipeTestStripe(testStripe);
                inspectorI3.putTestStripeIntoExplosiveTraceDetector(baggageScanner.getManualPostControl().getExplosiveTraceDetector(), testStripe);

                officer2InPresence.push(officer2InPresence.getRemoteControl(), tray);

                officer2InPresence.workWithRobot(tray.getHandBaggage());
            } else    if (record.getResult().getScanResult().equals(ScanResult.weapon)){
                //weapon
                passengerInPresence = tray.getHandBaggage().getPassenger();
                supervisorInPresence = baggageScanner.getSupervision().getSupervisor();
                officer1InPresence.openHandBaggageGetWeaponAndGiveToOfficer03(tray);
                //TODO ich glaube hier gibt er dem officer nicht alle Handbaggages
                //TODO i dont think so
                HandBaggage handBaggage = tray.getHandBaggage();
//                    for (HandBaggage handBaggage : tray.getHandBaggage()) {
                officer1InPresence.getFederalPoliceOffice().getFederalPoliceOfficerO3().getBaggagesOfArrested().add(handBaggage);
//                    }
                officer1InPresence.getFederalPoliceOffice().getFederalPoliceOfficerO3().setPassenger(passengerInPresence);
                officer2InPresence = null;
                officer3InPresence = null;
                passengerInPresence = null;
                supervisorInPresence.unlock(baggageScanner);

            }
        } else {
            //NO ILLEGAL ITEM
        }
    }
}
