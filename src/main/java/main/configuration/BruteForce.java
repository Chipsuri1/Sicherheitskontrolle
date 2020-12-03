package main.configuration;

public class BruteForce implements IStringMatching {

    public String search(String text, String pattern){
        int position = searchForPattern(text, pattern);

        if (position == -1) {
            return "clean";
        } else {
            return String.valueOf(position);
        }
    }


    public int searchForPattern(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int j;

        for (int i = 0; i <= (n - m); i++) {
            j = 0;

            while ((j < m) && (text.charAt(i + j) == pattern.charAt(j))) {
                j++;
            }

            if (j == m) {
                return i;
            }
        }

        return -1;
    }
}
