package com.example.demo.controllers;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class AuxController {

    public static String clearString(String type,String line){
        String stringWLK;
        String stringWRK;
        String stringWQ;
        String stringWRC;
        String stringWN;
        String stringWS;
        String finalString;

        if(type.equals("Dimension")) {
            // Se eliminan la llave izquierda
            stringWLK = line.replaceAll("\\{","");
            // Se eliminan la llave derecha
            stringWRK = stringWLK.replaceAll("}","");
            // Se eliminan las comillas
            stringWQ = stringWRK.replaceAll("\"","");
            // Se eliminan el corchete derecho
            stringWRC = stringWQ.replaceAll("]","");
            // Se elimina la palabra name
            stringWN = stringWRC.replaceAll("name","");
            // Se elimina la palabra score
            stringWS = stringWN.replaceAll("score","");
            // Se eliminan los dos puntos
            finalString = stringWS.replaceAll(":","");
        }
        else{
            // Se eliminan la llave izquierda
            stringWLK = line.replaceAll("\\{","");
            // Se eliminan la llave derecha
            stringWRK = stringWLK.replaceAll("}","");
            // Se eliminan las comillas
            stringWQ = stringWRK.replaceAll("\"","");
            // Se eliminan el corchete derecho
            stringWN = stringWQ.replaceAll("name","");
            // Se elimina la palabra score
            stringWS = stringWN.replaceAll("score","");
            // Se eliminan los dos puntos
            finalString = stringWS.replaceAll(":","");
            // Se elimina basura.
        }

        return finalString;
    }

    public static void readCSV() {
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/java/com/example/demo/information/TBD VOLUNTARIOS.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if( i != 0){
                    String[] parts = line.split("\\[");
                    // Información Personal del Voluntario.
                    String[] personalInfo = parts[0].split(",");
                    // La información queda en personalInfo[i] -> 0 A 4
                    // Dimensiones del Usuario
                    String parcialDimensions = clearString("Dimension",parts[1]);
                    String[] personalDimension = parcialDimensions.split(",");
                    // Herramientas del Usuario
                    String[] parts2 = parts[2].split("]");
                    String parcialEquipment = clearString("Equipment",parts2[0]);
                    String[] personalEquipment = parcialEquipment.split(",");
                    // En parts2[1] están las latitudes.
                }
                i ++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
