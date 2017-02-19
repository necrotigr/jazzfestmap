package net.jazzfestmap.geocoder.service.googlemaps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jazzfestmap.coords.LatLng;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Сергей on 18.02.2017.
 */
@Service
public class ResponseParser {

    private static final Logger logger = LogManager.getLogger(ResponseParser.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public LatLng parseGoogleMapsGeocodingResponse(String jsonResponse) {
        try {
            JsonNode root = mapper.readTree(jsonResponse);
            String status = root.findPath("status").asText();
            if (status.equals("OK")) {
                JsonNode location = root.findPath("location");
                double lat = location.findPath("lat").asDouble(0);
                double lng = location.findPath("lng").asDouble(0);
                return new LatLng(lat, lng);
            } else {
                System.out.println("Server returned status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
