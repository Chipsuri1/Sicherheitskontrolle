package main.baggageScanner.components;

import main.baggageScanner.CardReader;
import main.button.ButtonLeft;
import main.button.ButtonRectangle;
import main.button.ButtonRight;
import main.employee.Inspector;

public class OperatingStation {

    private Inspector inspectorI2 = new Inspector(2, "Natalie Portman", "09.06.1981", true);

    private CardReader cardReader;
    private ButtonLeft buttonLeft;
    private ButtonRight buttonRight;
    private ButtonRectangle buttonRectangle;

    public OperatingStation(Scanner scanner, Belt belt, BaggageScanner baggageScanner){
        buttonLeft = new ButtonLeft(scanner, belt);
        buttonRight = new ButtonRight(scanner, belt);
        buttonRectangle = new ButtonRectangle(scanner);
        cardReader = new CardReader(baggageScanner);
    }


    public Inspector getInspectorI2() {
        return inspectorI2;
    }


    public CardReader getCardReader() {
        return cardReader;
    }

    public ButtonLeft getButtonLeft() {
        return buttonLeft;
    }

    public ButtonRectangle getButtonRectangle() {
        return buttonRectangle;
    }

    public ButtonRight getButtonRight() {
        return buttonRight;
    }
}
