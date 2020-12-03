package main.configuration;

public interface IStringMatching {
    String search(String text, String pattern);
    int searchForPattern(String text, String pattern);
}