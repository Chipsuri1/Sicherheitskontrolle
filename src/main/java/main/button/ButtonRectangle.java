package main.button;

import main.baggageScanner.Scanner;
import main.employee.Employee;
import main.employee.Inspector;

public class ButtonRectangle extends Button {

    private Scanner scanner;

    public ButtonRectangle(Scanner scanner){
        this.scanner = scanner;
    }

    public boolean buttonAction(Employee employee){
        if(employee instanceof Inspector){
            scanner.startScanning();
            return true;
        }else{
            return false;
        }
    }
}
