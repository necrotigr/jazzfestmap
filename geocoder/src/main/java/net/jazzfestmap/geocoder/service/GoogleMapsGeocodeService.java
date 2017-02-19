package net.jazzfestmap.geocoder.service;

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
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
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

    private Map<String, LatLng> cache;

    @Value("${geocoding.api.key}")
    private String apiKey;

    @Value("${geocoding.request.delay:500}")
    private int delayBetweenRequests;

    @Inject
    private ResponseParser responseParser;


    String getGeocodeUrl(String apiKey, String addr) {
        return String.format("%s/json?address=%s&key=%s", GOOGLE_GEOGODING_API_URL, addr, apiKey);
    }

    LatLng checkCache(String addr) {
        return cache.get(addr);
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
            HttpUriRequest uriRequest = new HttpGet(getGeocodeUrl(addr, this.apiKey));
            HttpResponse response = httpClient.execute(uriRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream responseContent = response.getEntity().getContent();
                return responseParser.parseGoogleMapsGeocodingResponse(IOUtils.toString(responseContent, Charset.forName("UTF-8")));
            } else
                return LatLng.undetected();
        } catch (IOException e) {
            e.printStackTrace();
            return LatLng.undetected();
        }
    }

}
