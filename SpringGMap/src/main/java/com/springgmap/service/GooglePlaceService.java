package com.springgmap.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgmap.dao.GMapDao;
import com.springgmap.model.Restaurant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GooglePlaceService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final GMapDao gMapDao;

    //contsructor injection
    public GooglePlaceService(RestTemplate restTemplate,GMapDao gMapDao) {
        this.restTemplate = restTemplate;
        this.gMapDao=gMapDao;
    }
    //JSON returns as a String
    public String searchNearbyPlaces(double latitude,double longitude,double radius){
        String urlForRequest="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                +latitude+","+longitude+"&radius="+radius+"&key="+apiKey;
       return  restTemplate.getForObject(urlForRequest, String.class);
    }

    public void saveRestaurantWithLocationAndRadius(double latitude,double longitude,double radius){
        String urlForRequest="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                +latitude+","+longitude+"&radius="+radius+"&key="+apiKey;

        String json=restTemplate.getForObject(urlForRequest, String.class);

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode=null;

        try {
            rootNode=objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode resultsNode=rootNode.get("results");
        for (JsonNode jsonNode:resultsNode) {

            String types=jsonNode.get("types").toString();

            JsonNode geometryNode=jsonNode.get("geometry");
            JsonNode locationNode=geometryNode.get("location");

            double lat=locationNode.get("lat").asDouble();
            double lng=locationNode.get("lng").asDouble();

            String restaurantId = jsonNode.get("place_id").asText();
            String name = jsonNode.get("name").asText();
            String vicinity=null;

            try {
                vicinity=jsonNode.get("vicinity").asText();
            }catch (Exception e)
            {
                System.out.println("boş");
            }

            Restaurant restaurant=new Restaurant(lat,lng,restaurantId,name,types,vicinity);

            if(types.contains("restaurant")){
                gMapDao.create(restaurant);
            }
        }

    }

    public Restaurant getRestaurantById(int id){
        return gMapDao.get(id);
    }

    public List<Restaurant> getAll(){
        return gMapDao.list();
    }

}
