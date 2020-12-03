import main.FederalPoliceOffice.FederalPoliceOffice;
import main.baggageScanner.BaggageScanner;
import main.configuration.SecurityControl;
import main.employee.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestLuca {

    SecurityControl securityControl;

    @BeforeEach
    public void setup(){
        securityControl = new SecurityControl();
    }

    @Test
    @Order(1)
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

//        Assertions.assertEquals(baggageScanner.getRollerConveyor().getInspectorI1(), inspectorI1);
//        Assertions.assertEquals(baggageScanner.getOperatingStation().getInspectorI2(), inspectorI2);
//        Assertions.assertEquals(baggageScanner.getManualPostControl().getInspectorI3(), inspectorI3);
//        Assertions.assertEquals(baggageScanner.getSupervision().getSupervisor(), supervisor);
//        Assertions.assertEquals(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1(), federalPoliceOfficerO1);
//        Assertions.assertEquals(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO2(), federalPoliceOfficerO2);
//        Assertions.assertEquals(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO3(), federalPoliceOfficerO3);
//        Assertions.assertEquals(baggageScanner.getTechnician(), technician);
//        Assertions.assertEquals(baggageScanner.getHouseKeeper(), houseKeeper);

    }
}