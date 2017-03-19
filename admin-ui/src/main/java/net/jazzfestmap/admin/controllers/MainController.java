package net.jazzfestmap.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Сергей on 19.03.2017.
 */
@Controller
public class MainController {


    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
