package main;

import main.configuration.Configuration;


public class MagnetStripe {

    private ProfilType profilType;
    private String pin;

    public MagnetStripe(ProfilType profilType, String secretKey){
        this.profilType = profilType;
//        int pinInt = Configuration.instance.mersenneTwister.nextInt(1000, 9999);
        this.pin = Configuration.instance.aes.encrypt("5000", secretKey);

    }


    public ProfilType getProfilType() {
        return profilType;
    }

    public String getPin() {
        return pin;
    }
}
