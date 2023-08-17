package com.springgmap.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    private int id;
    private double lat;
    private double lng;
    private String restaurantId;
    private String name;
    private String types;
    private String vicinity;

    public Restaurant(double lat, double lng,String restaurantId , String name, String types, String vicinity) {
        this.lat = lat;
        this.lng = lng;
        this.restaurantId = restaurantId;
        this.name = name;
        this.types = types;
        this.vicinity = vicinity;
    }

    @Override
    public String toString(){

        return "Location : latitude= "+lat+
                ", longitude= "+lng+
                ", restaurant_id= "+restaurantId+
                ", name= "+name+
                ", types= "+types+
                ", vicinity"+vicinity;
    }
}
