package net.jazzfestmap.admin.controllers;

import net.jazzfestmap.admin.model.Festival;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 19.03.2017.
 */
@RestController
public class AdminController {

    @RequestMapping("/fests")
    public List<Festival> getFestivals() {
        return new ArrayList<>();
    }

}
