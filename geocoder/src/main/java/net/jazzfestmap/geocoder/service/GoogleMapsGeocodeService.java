package net.jazzfestmap.geocoder.service;

import net.jazzfestmap.coords.CoordsStatus;
import net.jazzfestmap.coords.LatLng;
import net.jazzfestmap.geocoder.service.googlemaps.ResponseParser;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.http.client.HttpClient;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Сергей on 18.02.2017.
 *
 * Сервис геокодинга, работающий с Google Maps
 * Данные запроса и полученного ответа сохраняет в кэш внутри памяти, во избежание повторных запросов
 */
@Service
public class GoogleMapsGeocodeService implements GeocodeService {

    public static final String GOOGLE_GEOGODING_API_URL = "https://maps.googleapis.com/maps/api/geocode";

    // TODO replace with Spring Cache implementation
    private LinkedHashMap<String, LatLng> addrCoordsCache;

    @Value("${geocoding.api.key}")
    private String apiKey;

    @Value("${geocoding.request.delay:500}")
    private int delayBetweenRequests;

    @Inject
    private ResponseParser responseParser;


    String getGeocodeUrl(String apiKey, String addr) {
        try {
            return String.format("%s/json?address=%s&key=%s",
                    GOOGLE_GEOGODING_API_URL,
                    URLEncoder.encode(addr, "utf-8"),
                    apiKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    LatLng checkCache(String addr) {
        if (addrCoordsCache == null)
            addrCoordsCache = new LinkedHashMap<>();
        LatLng coords = addrCoordsCache.get(addr);
        if ( (coords == null) || (coords.getStatus() != null) || (coords.getLatitude() == 0 && coords.getLongitude() == 0) ||
                (coords.getStatus() != CoordsStatus.CORRECT) )
            return null;
        else
            return coords;
    }

    @Override
    public LatLng geocode(String addr) {
        LatLng result = checkCache(addr);
        if (result == null) {
            result = requestGeocoding(addr);
        }
        return result;
    }

    @Override
    public Map<String, LatLng> geocode(Collection<String> addrs) {
        Map<String, LatLng> addrMap = new HashMap<>();
        for (String addr : addrs) {
            addrMap.put(addr, geocode(addr));
        }
        return addrMap;
    }

    LatLng requestGeocoding(String addr) {
        try {
            HttpClient httpClient = HttpClientBuilder.create()
                    .build();
            String geocodeUrl = getGeocodeUrl(this.apiKey, addr);
            HttpUriRequest uriRequest = new HttpGet(geocodeUrl);
            HttpResponse response = httpClient.execute(uriRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream responseContent = response.getEntity().getContent();
                LatLng coords = responseParser.parseGoogleMapsGeocodingResponse(IOUtils.toString(responseContent,
                                                                                Charset.forName("UTF-8")));
                if (coords != null)
                    System.out.println(addr + " -> " + coords.getLatitude() + "," + coords.getLongitude());
                saveToCache(addr, coords);
                return coords;
            } else
                return LatLng.undetected();
        } catch (IOException e) {
            e.printStackTrace();
            return LatLng.undetected();
        }
    }

    private void saveToCache(String addr, LatLng coords) {
        addrCoordsCache.put(addr, coords);
    }

}
