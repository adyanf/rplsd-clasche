package com.rplsd.clasche;

import com.rplsd.clasche.parser.ClascheDriver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main( String[] args ) {
        ClascheDriver driver = new ClascheDriver();
        if(args.length == 0) {
            System.out.println("Start interactive Clasche interpreter");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String line;
                System.out.print("> ");
                while((line = br.readLine()) != null) {
                    if(line != "\n") driver.start(line);
                    System.out.print("> ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if(args.length == 1) {
            try {
                byte[] encoded = Files.readAllBytes(Paths.get(args[0]));
                driver.start(new String(encoded, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
