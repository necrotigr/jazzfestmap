package net.jazzfestmap.grabber.services;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Сергей on 19.02.2017.
 * <p>
 * Main grabber
 * TODO: async grabbing
 */
@Service
public class FestGrabberService implements GrabberService {

    public static final String USER_AGENT_BROWSER = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0";

    @Override
    public String grab(String url) {
        HttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent(USER_AGENT_BROWSER)
                .build();
        HttpUriRequest uriRequest = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(uriRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
