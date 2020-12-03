package main.baggageScanner;

import main.IDCard;
import main.ProfilType;
import main.Status;
import main.configuration.Configuration;


public class CardReader {

    private BaggageScanner baggageScanner;

    public CardReader(BaggageScanner baggageScanner){
        this.baggageScanner = baggageScanner;
    }

    public void checkCard(IDCard idCard, String pin){
        if(scan(idCard, pin)){
            baggageScanner.setStatus(Status.activated);
            System.out.println("Activated BaggageScanner");
        }else {
            baggageScanner.setStatus(Status.locked);
            System.out.println("Locked BaggageScanner");
        }
    }

    public boolean scan(IDCard idCard, String pin){
        if(idCard.getMagnetStripe().getProfilType().equals(ProfilType.O) || idCard.getMagnetStripe().getProfilType().equals(ProfilType.K)){
            System.out.println("Permission denied, ProfileType equals " + idCard.getMagnetStripe().getProfilType());
            return false;
        }

        int amountOfTries = 0;
        while (amountOfTries <=2){
            String validPin = Configuration.instance.aes.decrypt(idCard.getMagnetStripe().getPin(), idCard.getSecretKey());
            if(validPin.equals(pin)){
                return true;
            }else{
                amountOfTries++;
            }
        }

        System.out.println("Permission denied, invalid PIN");
        return false;
    }
}
