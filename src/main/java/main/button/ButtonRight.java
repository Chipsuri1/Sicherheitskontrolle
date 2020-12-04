package main.button;

import main.baggageScanner.components.Belt;
import main.baggageScanner.components.Scanner;
import main.employee.Employee;
import main.employee.Inspector;

public class ButtonRight extends Button {

    private Scanner scanner;
    private Belt belt;

    public ButtonRight(Scanner scanner, Belt belt){
        this.belt = belt;
        this.scanner = scanner;
    }

    public boolean buttonAction(Employee employee){
        if(employee instanceof Inspector){
            int sizeOfBeltHandBaggage = belt.getTrays().size();
            for(int i = 0; i < sizeOfBeltHandBaggage; i++){
                scanner.getTrays().offer(belt.getTrays().poll());
            }
            return true;
        }
        else{
            return false;
        }
    }
}
