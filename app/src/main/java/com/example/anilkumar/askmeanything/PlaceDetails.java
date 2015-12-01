package com.example.anilkumar.askmeanything;

import com.google.api.client.util.Key;
import java.io.Serializable;

/**
 * Created by Anil Kumar on 29-11-2015.
 */
public class PlaceDetails implements Serializable{
    @Key
    public String status;

    @Key
    public Place result;

    @Override
    public String toString() {
        if (result!=null) {
            return result.toString();
        }
        return super.toString();
    }
}
