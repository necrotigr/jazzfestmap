package net.jazzfestmap.grabber.controllers;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JacksonInject;
import net.jazzfestmap.grabber.services.GrabberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Сергей on 19.02.2017.
 */
@RestController("grab")
public class GrabberController {

    @Inject
    private GrabberService grabber;

    @RequestMapping("url")
    public String grab(@RequestParam String url) {
        return grabber.grab(url);
    }

    @RequestMapping("urlList")
    public Map<String, String> grab(@RequestParam Collection<String> urlColl) {
        Map<String, String> result = new LinkedHashMap<>();
        for (String url : urlColl) {
            String content = grabber.grab(url);
            result.put(url, content);
        }
        return result;
    }

}
