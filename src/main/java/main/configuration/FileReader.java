package main.configuration;

import java.io.*;

public class FileReader {

    public String[] readContent(Integer lineNumber, String FILEPATH) {

        String[] parts = new String[3];
        BufferedReader br = null;
        try {
            br = new BufferedReader(new java.io.FileReader(new File(FILEPATH)));
            String line = null;

            for(int i = 0; i < lineNumber;i++) {
                br.readLine();
            }

            line = br.readLine();
            parts = line.split(";");


        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return parts;
    }

}