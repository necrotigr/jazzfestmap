package net.jazzfestmap.geocoder.service.googlemaps;

import net.jazzfestmap.coords.LatLng;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Сергей on 19.02.2017.
 */
public class ResponseParserTest {

    @Test
    public void testExample() {
        String jsonResponse = "{\n" +
                "   \"results\" : [\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"1600\",\n" +
                "               \"short_name\" : \"1600\",\n" +
                "               \"types\" : [ \"street_number\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Amphitheatre Pkwy\",\n" +
                "               \"short_name\" : \"Amphitheatre Pkwy\",\n" +
                "               \"types\" : [ \"route\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Mountain View\",\n" +
                "               \"short_name\" : \"Mountain View\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Santa Clara County\",\n" +
                "               \"short_name\" : \"Santa Clara County\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"California\",\n" +
                "               \"short_name\" : \"CA\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"United States\",\n" +
                "               \"short_name\" : \"US\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"94043\",\n" +
                "               \"short_name\" : \"94043\",\n" +
                "               \"types\" : [ \"postal_code\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"1600 Amphitheatre Parkway, Mountain View, CA 94043, USA\",\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 37.4224764,\n" +
                "               \"lng\" : -122.0842499\n" +
                "            },\n" +
                "            \"location_type\" : \"ROOFTOP\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 37.4238253802915,\n" +
                "                  \"lng\" : -122.0829009197085\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 37.4211274197085,\n" +
                "                  \"lng\" : -122.0855988802915\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJ2eUgeAK6j4ARbn5u_wAGqWA\",\n" +
                "         \"types\" : [ \"street_address\" ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}";

        ResponseParser parser = new ResponseParser();
        LatLng result = parser.parseGoogleMapsGeocodingResponse(jsonResponse);
        assertEquals("37.4224764", result.getLatitude().toString());
        assertEquals("-122.0842499", result.getLongitude().toString());
    }
}
