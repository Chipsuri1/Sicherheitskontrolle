package main.configuration;

public class Application {


    public static void main(String... args) {
        SecurityControl airport = new SecurityControl();
        airport.checkPassengers();
    }

}
