package net.jazzfestmap.geocoder.service;

import net.jazzfestmap.coords.LatLng;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Сергей on 18.02.2017.
 */
public interface GeocodeService {

    LatLng geocode(String addr);

    Map<String, LatLng> geocode(Collection<String> addrs);
}
