package main.button;

import main.baggageScanner.Belt;
import main.baggageScanner.Scanner;

public class ButtonLeft extends Button {

    private Scanner scanner;
    private Belt belt;

    public ButtonLeft(Scanner scanner, Belt belt){
        this.scanner = scanner;
        this.belt = belt;
    }

    public void buttonAction(){
        int sizeOfBeltHandBaggage = belt.getTrays().size();
        for(int i = 0; i < sizeOfBeltHandBaggage; i++){
            scanner.getTrays().offer(belt.getTrays().poll());
        }
    }
}
