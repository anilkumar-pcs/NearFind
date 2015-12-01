package com.example.anilkumar.askmeanything;

import com.google.api.client.util.Key;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Anil Kumar on 29-11-2015.
 */
public class PlacesList implements Serializable{
    @Key
    public String error_message;

    @Key
    public String status;

    @Key
    public List<Place> results;
}
