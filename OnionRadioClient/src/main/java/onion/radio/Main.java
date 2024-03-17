package onion.radio;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try{
            File stationsTxt = new File(args[0]);
            if(!stationsTxt.exists()) throw new RuntimeException();

        }catch (Exception e){
            throw new RuntimeException("Ye done goofed up lad.");
        }
    }
}