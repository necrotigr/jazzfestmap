package net.jazzfestmap.geocoder.controller;

import net.jazzfestmap.coords.LatLng;
import net.jazzfestmap.geocoder.service.GeocodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by Сергей on 18.02.2017.
 *
 * Основная точка входа - запрос/ответ
 */
@RestController
@RequestMapping("geocode")
public class GeocodeController {

    private final GeocodeService geocodeService;

    @Inject
    public GeocodeController(GeocodeService geocodeService) {
        this.geocodeService = geocodeService;
    }


    @RequestMapping("singleAddr")
    public LatLng geocodeAddress(@RequestParam String address) {
        return geocodeService.geocode(address);
    }

    @RequestMapping("multipleAddr")
    public Map<String, LatLng> geocodeAddress(@RequestParam List<String> lstAddr) {
        return geocodeService.geocode(lstAddr);
    }
}
