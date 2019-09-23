package com.example.demo.information;

import com.example.demo.models.*;
import com.example.demo.repositories.DimensionRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.DistrictRepository;
import com.example.demo.repositories.VoluntaryDimensionRepository;
import com.example.demo.repositories.VoluntaryRepository;
import com.example.demo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Auxiliar {

    @Autowired
    private VoluntaryRepository voluntaryRepository;
    @Autowired
    private DimensionRepository dimensionRepository;
    @Autowired
    private VoluntaryDimensionRepository voluntaryDimensionRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

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

        // Se guardan las regiones en city
        cityRepository.save(new City("Tarapaca"));
        cityRepository.save(new City("Antofagasta"));
        cityRepository.save(new City("Atacama"));
        cityRepository.save(new City("Coquimbo"));
        cityRepository.save(new City("Valparaiso"));
        cityRepository.save(new City("Libertador General Bernardo O'Higgins"));
        cityRepository.save(new City("Maule"));
        cityRepository.save(new City("Concepcion"));
        cityRepository.save(new City("Araucania"));
        cityRepository.save(new City("Los Lagos"));
        cityRepository.save(new City("Aysen del General Carlos Ibañez del Campo"));
        cityRepository.save(new City("Magallanes y de la Antartica Chilena"));
        cityRepository.save(new City("Metropolitana de Santiago"));
        cityRepository.save(new City("Los Rios"));
        cityRepository.save(new City("Arica y Parinacota"));
        cityRepository.save(new City("Ñuble"));

        // Se guardan las comunas
        districtRepository.save(new District(cityRepository.findCityByName("Metropolitana de Santiago"),"Santiago de Chile"));
        districtRepository.save(new District(cityRepository.findCityByName("Concepcion"),"Concepcion"));
        districtRepository.save(new District(cityRepository.findCityByName("Valparaiso"),"Valparaiso"));
        districtRepository.save(new District(cityRepository.findCityByName("Coquimbo"),"La Serena"));
        districtRepository.save(new District(cityRepository.findCityByName("Antofagasta"),"Antofagasta"));
        districtRepository.save(new District(cityRepository.findCityByName("Araucania"),"Temuco"));
        districtRepository.save(new District(cityRepository.findCityByName("Tarapaca"),"Iquique"));
        districtRepository.save(new District(cityRepository.findCityByName("Libertador General Bernardo O'Higgins"),"Rancagua"));
        districtRepository.save(new District(cityRepository.findCityByName("Los Lagos"),"Puerto Montt"));
        districtRepository.save(new District(cityRepository.findCityByName("Maule"),"Talca"));
        districtRepository.save(new District(cityRepository.findCityByName("Arica y Parinacota"),"Arica"));
        districtRepository.save(new District(cityRepository.findCityByName("Ñuble"),"Chillan"));
        districtRepository.save(new District(cityRepository.findCityByName("Antofagasta"),"Calama"));
        districtRepository.save(new District(cityRepository.findCityByName("Atacama"),"Copiapo"));
        districtRepository.save(new District(cityRepository.findCityByName("Los Rios"),"Valdivia"));
        districtRepository.save(new District(cityRepository.findCityByName("Valparaiso"),"Quillota"));
        districtRepository.save(new District(cityRepository.findCityByName("Los Lagos"),"Osorno"));
        districtRepository.save(new District(cityRepository.findCityByName("Concepcion"),"Los Angeles"));
        districtRepository.save(new District(cityRepository.findCityByName("Maule"),"Curico"));
        districtRepository.save(new District(cityRepository.findCityByName("Magallanes y de la Antartica Chilena"),"Punta Arenas"));
        districtRepository.save(new District(cityRepository.findCityByName("Metropolitana de Santiago"),"Colina"));
        districtRepository.save(new District(cityRepository.findCityByName("Metropolitana de Santiago"),"Peñaflor"));
        districtRepository.save(new District(cityRepository.findCityByName("Coquimbo"),"Ovalle"));
        districtRepository.save(new District(cityRepository.findCityByName("Metropolitana de Santiago"),"Melipilla"));
        districtRepository.save(new District(cityRepository.findCityByName("Valparaiso"),"Los Andes"));
        districtRepository.save(new District(cityRepository.findCityByName("Metropolitana de Santiago"),"Buin"));
        districtRepository.save(new District(cityRepository.findCityByName("Valparaiso"),"San Felipe"));
        districtRepository.save(new District(cityRepository.findCityByName("Libertador General Bernardo O'Higgins"),"San Fernando"));
        districtRepository.save(new District(cityRepository.findCityByName("Metropolitana de Santiago"),"Talagante"));
        districtRepository.save(new District(cityRepository.findCityByName("Valparaiso"),"Limache - Olmue"));
        districtRepository.save(new District(cityRepository.findCityByName("Aysen del General Carlos Ibañez del Campo"),"Coyhaique"));
        districtRepository.save(new District(cityRepository.findCityByName("Valparaiso"),"San Antonio"));
        districtRepository.save(new District(cityRepository.findCityByName("Maule"),"Linares"));

        //Se guardan los roles
        roleRepository.save(new Role(1,"Todos los permisos"));
        roleRepository.save(new Role(2,"Permisos de coordinador"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Se guardan los usuarios
        userRepository.save(new User(197893478,"Carlos","Faundes","carlosfaundes@gmail.com",roleRepository.findRoleByTipo(1)));
        userRepository.save(new User(191234568,"Nicolas","Lopez","nicolaslopez@gmail.com",roleRepository.findRoleByTipo(1)));
        userRepository.save(new User(187531378,"Jorge","Ayala","jorgeayala@gmail.com",roleRepository.findRoleByTipo(1)));
        userRepository.save(new User(236763478,"Javier","Crackceres","javocrack@gmail.com",roleRepository.findRoleByTipo(1)));
        userRepository.save(new User(194576478,"Fabian","Fardo","fabianfardo@gmail.com",roleRepository.findRoleByTipo(1)));
        userRepository.save(new User(346653478,"Matias","Fernandez","maticrack14@gmail.com",roleRepository.findRoleByTipo(2)));
        userRepository.save(new User(191234348,"Esteban","Paredes","estebanparedes@gmail.com",roleRepository.findRoleByTipo(2)));
        userRepository.save(new User(191324378,"Humberto","Suazo","humbertosuazo@gmail.com",roleRepository.findRoleByTipo(2)));
        userRepository.save(new User(175467678,"Jorge","Valdivia","magovaldivia@gmail.com",roleRepository.findRoleByTipo(2)));
        userRepository.save(new User(196756578,"Claudio","Bravo","claudiobravo@gmail.com",roleRepository.findRoleByTipo(2)));
    }
}
