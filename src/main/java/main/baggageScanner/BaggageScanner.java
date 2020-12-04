package main.baggageScanner;

import main.FederalPoliceOffice.FederalPoliceOffice;
import main.Record;
import main.ScanResult;
import main.Status;
import main.employee.Employee;
import main.employee.HouseKeeper;
import main.employee.Supervisor;
import main.employee.Technician;

import static main.Status.*;

public class BaggageScanner {

    private OperatingStation operatingStation;
    private ManualPostControl manualPostControl = new ManualPostControl();
    private RollerConveyor rollerConveyor = new RollerConveyor();
    private Belt belt = new Belt();
    private Supervision supervision = new Supervision();
    private Scanner scanner;
    private FederalPoliceOffice federalPoliceOffice;

    private Track track1 = new Track();
    private Track track2 = new Track();

    private Technician technician = new Technician(6, "Jason Statham", "26.07.1967", this);
    private HouseKeeper houseKeeper = new HouseKeeper(7, "Jason Clarke", "17.07.1969");

    private Status status = shutdown;

    public BaggageScanner() {
        this.scanner = new Scanner(this);
        this.operatingStation = new OperatingStation(scanner, belt, this);
        this.federalPoliceOffice = new FederalPoliceOffice(this);
    }

    public void scanHandBaggage() {
        if(getStatus().equals(Status.activated)){
            rollerConveyor.getInspectorI1().pushHandBaggage(rollerConveyor.getTrays(), belt.getTrays());
            operatingStation.getInspectorI2().push(operatingStation.getButtonRight());
            operatingStation.getInspectorI2().push(operatingStation.getButtonRectangle());

            while (scanner.getTrays().size() != 0) {
                doNextStepAfterScanning(scanner.getTrays().poll());
            }
        }else{
            System.out.println("BaggageScanner is not activated, status: " + getStatus());
        }

    }

    public void doNextStepAfterScanning(Tray tray) {
        Record record = tray.getRecord();

        if (record.getResult().getScanResult().equals(ScanResult.knife) || record.getResult().getScanResult().equals(ScanResult.weapon) || record.getResult().getScanResult().equals(ScanResult.explosive)) {
            //manuelle Nachkontrolle durch Inspektor I3 auf Track 01
            manualPostControl.setTray(tray);
            manualPostControl.makeManuelPostControll(this);

        } else {
//            System.out.println("Passenger "+ tray.getHandBaggage().getPassenger().getName()+ " is crispy clean!");
            //Gib Passagier Handbaggage zurück über Track 02
            track2.putTray(tray);
        }
    }

    public boolean maintenance(Employee employee){
        if(employee instanceof Technician){
            setStatus(maintenance);
            //do maintenance
            setStatus(activated);
            return true;
        }
        return false;
    }

    public boolean unlock(Employee employee){
        if(getStatus().equals(Status.locked) && employee instanceof Supervisor){
            setStatus(activated);
            return true;
        }
        return false;
    }

    public OperatingStation getOperatingStation() {
        return operatingStation;
    }

    public ManualPostControl getManualPostControl() {
        return manualPostControl;
    }

    public Belt getBelt() {
        return belt;
    }

    public Supervision getSupervision() {
        return supervision;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Track getTrack1() {
        return track1;
    }

    public Track getTrack2() {
        return track2;
    }

    public Status getStatus() {
        return status;
    }

    public void start() {
        setStatus(deactivated);
    }

    public void shutdown() {
        setStatus(shutdown);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RollerConveyor getRollerConveyor() {
        return rollerConveyor;
    }

    public FederalPoliceOffice getFederalPoliceOffice() {
        return federalPoliceOffice;
    }

    public HouseKeeper getHouseKeeper() {
        return houseKeeper;
    }

    public Technician getTechnician() {
        return technician;
    }
}

