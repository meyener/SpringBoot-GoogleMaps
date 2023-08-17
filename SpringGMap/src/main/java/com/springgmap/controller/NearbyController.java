package com.springgmap.controller;

import com.springgmap.model.Restaurant;
import com.springgmap.service.GooglePlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NearbyController {

    private final GooglePlaceService googlePlaceService;

    public NearbyController(GooglePlaceService googlePlaceService) {
        this.googlePlaceService = googlePlaceService;
    }

    @GetMapping("/search-places")
    public String searchPlaces(double latitude,double longitude,int radius){
        return googlePlaceService.searchNearbyPlaces(latitude,longitude,radius);
    }

    @PostMapping("/search-places")
    public void savePlaces(double latitude,double longitude,int radius){
         googlePlaceService.saveRestaurantWithLocationAndRadius(latitude,longitude,radius);
    }
    @GetMapping("/getbyid={id}")
    public Restaurant getRestaurantById(@PathVariable int id){
        return googlePlaceService.getRestaurantById(id);
    }
    @GetMapping("/restaurants")
    public List<Restaurant> getAll(){
        return googlePlaceService.getAll();
    }
}
