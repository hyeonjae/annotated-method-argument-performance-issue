package com.springapp.mvc;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HelloController {
    @ModelAttribute("DoorayAppKey")
    public String getDoorayAppKey(@RequestHeader("Dooray-App-Key") String doorayAppKey) {
        return doorayAppKey;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String printAppKey(@ModelAttribute("DoorayAppKey") String doorayAppKey) {
        return "{ \"doorayAppKey\": \"" + doorayAppKey + "\" }";
    }
}
