package main.configuration;

public enum Configuration {
    instance;

    public SearchType searchType = SearchType.BoyerMoore;

    public final int NUMBER_OF_PASSENGERS = 568;
    public final int NUMBER_OF_BAGGAGE = 609;
    public final int NUMBER_OF_CONTENT_PER_LAYER = 10000;

    public final MersenneTwister mersenneTwister = new MersenneTwister();
    public final DataGenerator dataGenerator = new DataGenerator();
    public final FileReader fileReader = new FileReader();
    public final AES aes = new AES();
    public final BruteForce bruteForce = new BruteForce();
    public final BoyerMoore boyerMoore = new BoyerMoore();
    public final KnuthMorrisPratt knuthMorrisPratt = new KnuthMorrisPratt();


    // i/o
    public final String fileSeparator = System.getProperty("file.separator");
    public final String userDirectory = System.getProperty("user.dir");
    public final String lineSeparator = System.getProperty("line.separator");
    public final String DATA_FILEPATH = userDirectory + fileSeparator + "resources" + fileSeparator + "passenger_baggage.txt" + fileSeparator;

}
