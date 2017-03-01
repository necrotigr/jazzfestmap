package net.jazzfestmap.geocoder.service;

import net.jazzfestmap.geocoder.service.GoogleMapsGeocodeService;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

/**
 * Created by Сергей on 01.03.2017.
 */
public class URLEncoderTest {

    @Test
    public void urlTest() {
        try {
            String urlEncoded = URLEncoder.encode("Viljandi, Estonia", "utf-8");
            System.out.println(urlEncoded);
            String urlFormatted = String.format("%s", urlEncoded);
            System.out.println(urlFormatted);
            assertEquals(urlEncoded, urlFormatted);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void geocodeUrlEncodeTest() {
        String sample = "Viljandi, Estonia";
        String urlEncoded = new GoogleMapsGeocodeService().getGeocodeUrl("APIKEY", sample);
        System.out.println(urlEncoded);
        URI.create(urlEncoded);
    }
}
