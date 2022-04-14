package com.example.antipolice.web.controllers;

import com.example.antipolice.model.PoliceState;
import com.example.antipolice.model.exceptions.LatLngNotFoundException;
import com.example.antipolice.service.MapCoordinatesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"/anti-police","/"})
public class HomeController {

    private final MapCoordinatesService mapCoordinatesService;

    public HomeController(MapCoordinatesService mapCoordinatesService) {
        this.mapCoordinatesService = mapCoordinatesService;
    }

    @GetMapping
    public String getHomePage(@RequestParam(required = false)String filter, Model model){
        model.addAttribute("body","map_view");
        if (filter != null && filter.equals("mostSubmitted"))
            model.addAttribute("filter",filter);
        return "base";
    }

    @GetMapping("/new_location")
    public String getAddPage(@RequestParam(required = false) String error,
            Model model){
        if(error!=null)
            model.addAttribute("errorMessage", error);
        model.addAttribute("policeStates", PoliceState.values());
        model.addAttribute("body","add_map");
        return "base";
    }

    @PostMapping("/new_location")
    public String submit(@RequestParam String cord, @RequestParam String state, HttpServletRequest request) {
        try {
            mapCoordinatesService.save(cord, state, request.getRemoteUser());
            return "redirect:/anti-police";
        }
        catch (LatLngNotFoundException e)
        {
            return "redirect:/new_location?error="+e.getMessage();
        }
    }
}
