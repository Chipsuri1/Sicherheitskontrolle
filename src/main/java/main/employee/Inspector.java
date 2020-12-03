package main.employee;

import main.Record;
import main.*;
import main.baggageScanner.BaggageScanner;
import main.baggageScanner.Tray;
import main.button.Button;
import main.passenger.HandBaggage;
import main.passenger.Layer;
import main.passenger.Passenger;

import java.util.Queue;

import static main.Type.staff;

public class Inspector extends Employee {

    private boolean isSenior;

    private Record record;
    private FederalPoliceOfficer officer1;
    private FederalPoliceOfficer officer2;
    private Passenger passengerInPresence;
    private Supervisor supervisorInPresence;

    public Inspector(int id, String name, String birthDate, boolean isSenior) {
        super(id, name, birthDate);

        this.isSenior = isSenior;

        getIdCard().setType(staff);
        getIdCard().setMagnetStripe(new MagnetStripe(ProfilType.I, getIdCard().getSecretKey()));
    }

    public IDCard swipeCard(){
        return getIdCard();
    }

    public void pushHandBaggage(Queue<Tray> rollerConveyor , Queue<Tray> belt){
        int sizeOfRollerConveyor = rollerConveyor.size();
        for(int i = 0; i < sizeOfRollerConveyor; i++){
            Tray tray = rollerConveyor.poll();
            belt.offer(tray);
        }
    }

    public void push(Button button){
        button.buttonAction();
    }

    public void doManualPostControl(BaggageScanner baggageScanner, Tray tray) {
        record = tray.getRecord();
        //Knife
        if (record.getResult().getScanResult().equals(ScanResult.knife)) {
            baggageScanner.getOperatingStation().getInspectorI2().tellOtherInspector(baggageScanner.getManualPostControl().getInspectorI3(), record);
            passengerInPresence = tray.getHandBaggage().getPassenger();
            baggageScanner.getManualPostControl().getInspectorI3().openBaggageGetKnifeAndThrowAway(tray.getHandBaggage());

            baggageScanner.getManualPostControl().getInspectorI3().putTrayToBelt(tray, baggageScanner);

            baggageScanner.scanHandBaggage();
            passengerInPresence = null;

        }
        //Weapon or Explosive
        else if (record.getResult().getScanResult().equals(ScanResult.weapon) || record.getResult().getScanResult().equals(ScanResult.explosive)) {
            baggageScanner.getOperatingStation().getInspectorI2().setAlarm(baggageScanner);
            baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1().arrest(tray.getHandBaggage().getPassenger());
            officer1 = baggageScanner.getFederalPoliceOffice().reqestOfficer1(baggageScanner);
            officer2 = baggageScanner.getFederalPoliceOffice().reqestOfficer2(baggageScanner);
            if (record.getResult().getScanResult().equals(ScanResult.explosive)) {
                getOfficer2().workWithRobot();
            } else {
                //weapon
                if (record.getResult().getScanResult().equals(ScanResult.weapon)){

                    passengerInPresence = tray.getHandBaggage().getPassenger();
                    supervisorInPresence = baggageScanner.getSupervision().getSupervisor();
                    setOfficer1(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1());
                    getOfficer1().openHandBaggageGetWeaponAndGiveToOfficer03(tray);
                    //TODO ich glaube hier gibt er dem officer nicht alle Handbaggages
                    //TODO i dont think so
                    HandBaggage handBaggage = tray.getHandBaggage();
//                    for (HandBaggage handBaggage : tray.getHandBaggage()) {
                        getOfficer1().getFederalPoliceOffice().getFederalPoliceOfficerO3().getBaggagesOfArrested().add(handBaggage);
//                    }
                    getOfficer1().getFederalPoliceOffice().getFederalPoliceOfficerO3().setPassenger(passengerInPresence);
                    supervisorInPresence.unlock(baggageScanner);
                }
                else if (record.getResult().getScanResult().equals(ScanResult.explosive)){
                    TestStripe testStripe = new TestStripe();
                    swipeTestStripe(testStripe);
                    putTestStripeIntoExplosiveTraceDetector(baggageScanner.getManualPostControl().getExplosiveTraceDetector(), testStripe);
                }
            }
        } else {

        }


    }

    private void swipeTestStripe(TestStripe testStripe){
        testStripe.setExp();
    }

    private void putTestStripeIntoExplosiveTraceDetector(ExplosiveTraceDetector explosiveTraceDetector, TestStripe testStripe){
        explosiveTraceDetector.checkTestStripeForExplosive(testStripe);
    }



    private void setAlarm(BaggageScanner baggageScanner) {
        baggageScanner.setStatus(Status.locked);
    }

    public void tellOtherInspector(Inspector inspector, Record record){
        inspector.hearSentence(record);
    }

    public void hearSentence(Record record){
        this.record = record;
    }

    public void openBaggageGetKnifeAndThrowAway(HandBaggage handBaggage) {
            for (Layer layer : handBaggage.getLayers()
                 ) {
                for (int i = 0; i < layer.getContent().length; i++) {
                    if(layer.getContent()[i] == 'K'){
                        // if there is the knife, "throw" it away and put an 'a' in
                        layer.getContent()[i] = 'a';
                    }
                }
        }
    }

    private void putTrayToBelt(Tray tray, BaggageScanner baggageScanner) {
        baggageScanner.getBelt().getTrays().offer(tray);
    }

    public void putOnTrack1(BaggageScanner baggageScanner, Tray tray) {
        baggageScanner.getTrack1().putTray(tray);
    }

    public FederalPoliceOfficer getOfficer1() {
        return officer1;
    }

    public FederalPoliceOfficer getOfficer2() {
        return officer2;
    }

    public void setOfficer1(FederalPoliceOfficer officer1) {
        this.officer1 = officer1;
    }

    public void setOfficer2(FederalPoliceOfficer officer2) {
        this.officer2 = officer2;
    }
}
