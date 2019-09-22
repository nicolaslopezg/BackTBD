package com.example.demo.information;

import com.example.demo.models.Dimension;
import com.example.demo.models.Voluntary;
import com.example.demo.models.VoluntaryDimension;
import com.example.demo.models.VoluntaryTask;
import com.example.demo.repositories.DimensionRepository;
import com.example.demo.repositories.VoluntaryDimensionRepository;
import com.example.demo.repositories.VoluntaryRepository;
import com.example.demo.repositories.VoluntaryTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Auxiliar {

    @Autowired
    private VoluntaryRepository voluntaryRepository;
    @Autowired
    private DimensionRepository dimensionRepository;
    @Autowired
    private VoluntaryDimensionRepository voluntaryDimensionRepository;

    public  String clearString(String type,String line){
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

    public List<List<String>> readCSVPersonalInfo() {
        List<List<String>> result = new ArrayList<List<String>>();

        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/java/com/example/demo/information/TBD VOLUNTARIOS.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if( i != 0){
                    List<String> partialPersonalInfo = new ArrayList<String>();
                    String[] parts = line.split("\\[");
                    // Información Personal del Voluntario.
                    String[] personalInfo = parts[0].split(",");
                    // La información queda en personalInfo[i] -> 0 A 4
                    partialPersonalInfo.add(personalInfo[1]);
                    partialPersonalInfo.add(personalInfo[2]);
                    partialPersonalInfo.add(personalInfo[3]);
                    partialPersonalInfo.add(personalInfo[4]);
                    result.add(partialPersonalInfo);
                }
                i ++;
            }
        }
         catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<List<String>> readCSVDimensions(){
        List<List<String>> result = new ArrayList<List<String>>();
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/java/com/example/demo/information/TBD VOLUNTARIOS.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    List<String> partialDimensionInfo = new ArrayList<String>();
                    String[] parts = line.split("\\[");
                    String parcialDimensions = clearString("Dimension",parts[1]);
                    String[] personalDimension = parcialDimensions.split(",");
                    for(int j = 0; j < personalDimension.length; j++){
                        partialDimensionInfo.add(personalDimension[j]);
                    }
                    result.add(partialDimensionInfo);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws ParseException {

        List<List<String>> information = new ArrayList<List<String>>();
        information = readCSVPersonalInfo();
        List<List<String>> dimensions = new ArrayList<List<String>>();
        dimensions = readCSVDimensions();

        // Se guardan las dimensiones en la base de datos.
        dimensionRepository.save(new Dimension("Fuerza"));
        dimensionRepository.save(new Dimension("Destreza"));
        dimensionRepository.save(new Dimension("Liderazgo"));
        dimensionRepository.save(new Dimension("Motivacion"));
        dimensionRepository.save(new Dimension("Conocimiento"));

        // Se guardan los voluntarios en la base de datos.
        for(int i = 0; i < information.size(); i++){
            voluntaryRepository.save(new Voluntary(information.get(i).get(0),information.get(i).get(1),information.get(i).get(2),information.get(i).get(3),i));
        }

        for(int i = 0; i < dimensions.size(); i++){
            for(int j = 0; j < dimensions.get(i).size() - 1; j = j + 2){
                voluntaryDimensionRepository.save(new VoluntaryDimension(dimensionRepository.findDimensionByName(dimensions.get(i).get(j)),voluntaryRepository.findVoluntaryByIdVoluntary(Long.valueOf(i+1)),Integer.valueOf(dimensions.get(i).get(j+1))));
            }
        }

    }

}
