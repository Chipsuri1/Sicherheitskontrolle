package main.button;

import main.baggageScanner.Scanner;

public class ButtonRectangle extends Button {

    private Scanner scanner;

    public ButtonRectangle(Scanner scanner){
        this.scanner = scanner;
    }

    public void buttonAction(){
        scanner.startScanning();
    }
}
