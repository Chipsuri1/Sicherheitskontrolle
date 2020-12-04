package main.FederalPoliceOffice;

import main.FederalPoliceOffice.ScanResult;

public class Result {

    ScanResult scanResult;
    String position;

    public Result(ScanResult scanResult, String position){
        this.scanResult = scanResult;
        this.position = position;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public String getPosition() {
        return position;
    }
}
