import main.*;
import main.FederalPoliceOffice.FederalPoliceOffice;
import main.baggageScanner.BaggageScanner;
import main.baggageScanner.Scanner;
import main.baggageScanner.Tray;
import main.configuration.SecurityControl;
import main.employee.*;
import main.passenger.HandBaggage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestLuca {

    SecurityControl securityControl;
    BaggageScanner baggageScanner;

    @BeforeEach
    public void setup(){
        securityControl = new SecurityControl();
        baggageScanner = securityControl.getBaggageScanner();
    }

    @Test
    @Order(2)
    public void assignStationsWithEmployees() {
        Inspector inspectorI1 = new Inspector(1, "Clint Eastwood", "31.05.1930", true);
        Inspector inspectorI2 = new Inspector(2, "Natalie Portman", "09.06.1981", false);
        Inspector inspectorI3 = new Inspector(3, "Bruce Willis", "19.03.1955", true);
        Supervisor supervisor = new Supervisor(4, "Jodie Foster", "19.11.1962", false, false);
        FederalPoliceOfficer federalPoliceOfficerO1 = new FederalPoliceOfficer(5, "Wesley Snipes", "31.07.1962", null);
        Technician technician = new Technician(6, "Jason Statham",  "26.07.1967");
        HouseKeeper houseKeeper = new HouseKeeper(7, "Jason Clarke", "17.07.1969");
        FederalPoliceOfficer federalPoliceOfficerO2 = new FederalPoliceOfficer(8, "Toto", "01.01.1969", null);
        FederalPoliceOfficer federalPoliceOfficerO3 = new FederalPoliceOfficer(9, "Harry", "01.01.1969", null);

        BaggageScanner baggageScanner = new BaggageScanner();




        Assertions.assertTrue(baggageScanner.getRollerConveyor().getInspectorI1() instanceof Inspector);
        Assertions.assertTrue(baggageScanner.getRollerConveyor().getInspectorI1().getName().equals("Clint Eastwood"));
        Assertions.assertTrue(baggageScanner.getOperatingStation().getInspectorI2() instanceof Inspector);
        Assertions.assertTrue(baggageScanner.getOperatingStation().getInspectorI2().getName().equals("Natalie Portman"));
        Assertions.assertTrue(baggageScanner.getManualPostControl().getInspectorI3() instanceof Inspector);
        Assertions.assertTrue(baggageScanner.getManualPostControl().getInspectorI3().getName().equals("Bruce Willis"));
        Assertions.assertTrue(baggageScanner.getSupervision().getSupervisor() instanceof Supervisor);
        Assertions.assertTrue(baggageScanner.getSupervision().getSupervisor().getName().equals("Jodie Foster"));
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1() instanceof FederalPoliceOfficer);
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1().getName().equals("Wesley Snipes"));
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO2() instanceof FederalPoliceOfficer);
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO2().getName().equals("Toto"));
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO3() instanceof FederalPoliceOfficer);
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO3().getName().equals("Harry"));
        Assertions.assertTrue(baggageScanner.getTechnician() instanceof Technician);
        Assertions.assertTrue(baggageScanner.getTechnician().getName().equals("Jason Statham"));
        Assertions.assertTrue(baggageScanner.getHouseKeeper() instanceof HouseKeeper);
        Assertions.assertTrue(baggageScanner.getHouseKeeper().getName().equals("Jason Clarke"));

    }


    @Test
    @Order(3)
    public void scanInspectorCard(){
        Assertions.assertEquals(baggageScanner.getStatus(), Status.shutdown);
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        Assertions.assertEquals(baggageScanner.getStatus(), Status.locked);
        baggageScanner.setStatus(Status.shutdown);
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "5000");
        Assertions.assertEquals(baggageScanner.getStatus(), Status.locked);
        baggageScanner.getSupervision().getSupervisor().unlock(baggageScanner);
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "5000");
        Assertions.assertEquals(baggageScanner.getStatus(), Status.activated);

    }

    @Test
    @Order(3)
    public void checkCardOfEmployee(){
        IDCard idCardProfileTypeK = new IDCard();
        IDCard idCardProfileTypeO = new IDCard();
        idCardProfileTypeK.setMagnetStripe(new MagnetStripe(ProfilType.K, "1000"));
        idCardProfileTypeO.setMagnetStripe(new MagnetStripe(ProfilType.O, "1000"));

        Assertions.assertFalse(baggageScanner.getOperatingStation().getCardReader().scan(idCardProfileTypeK, "1000"));
        Assertions.assertFalse(baggageScanner.getOperatingStation().getCardReader().scan(idCardProfileTypeO, "1000"));
    }

    @Test
    @Order(6)
    public void onlySupervisorCanUnlockBaggageScanner(){
        baggageScanner.setStatus(Status.locked);
        baggageScanner.getSupervision().getSupervisor().unlock(baggageScanner);
        Assertions.assertEquals(baggageScanner.getStatus(), Status.shutdown);
    }

    @Test
    @Order(7)
    public void checkIfHandBaggageContainsWeapon(){
        int positionOfWeapon = 100;

        HandBaggage handBaggage = new HandBaggage();
        handBaggage.getLayers()[0].getContent()[positionOfWeapon] = 'W';
        baggageScanner.getScanner().getTrays().add(new Tray(handBaggage));
        baggageScanner.getScanner().startScanning();
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().size(), 1);
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().peek().getResult().getScanResult(), ScanResult.weapon);

    }

    @Test
    @Order(8)
    public void checkIfHandBaggageContainsKnife(){
        int positionOfWeapon = 100;

        HandBaggage handBaggage = new HandBaggage();
        handBaggage.getLayers()[0].getContent()[positionOfWeapon] = 'K';
        baggageScanner.getScanner().getTrays().add(new Tray(handBaggage));
        baggageScanner.getScanner().startScanning();
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().size(), 1);
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().peek().getResult().getScanResult(), ScanResult.knife);

    }

    @Test
    @Order(9)
    public void checkIfHandBaggageContainsExplosive(){
        int positionOfWeapon = 100;

        HandBaggage handBaggage = new HandBaggage();
        handBaggage.getLayers()[0].getContent()[positionOfWeapon] = 'E';
        baggageScanner.getScanner().getTrays().add(new Tray(handBaggage));
        baggageScanner.getScanner().startScanning();
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().size(), 1);
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().peek().getResult().getScanResult(), ScanResult.explosive);

    }
}
