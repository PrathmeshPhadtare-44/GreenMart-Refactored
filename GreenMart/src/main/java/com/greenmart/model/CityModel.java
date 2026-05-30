package com.greenmart.model;
import lombok.Data;

import java.util.List;

@Data
public class CityModel {
    private List<String> cities;

    public CityModel() {
        this.cities = List.of("Mumbai", "Pune", "Nagpur", "Nashik", "Aurangabad", "Thane", "Solapur");
    }
}
