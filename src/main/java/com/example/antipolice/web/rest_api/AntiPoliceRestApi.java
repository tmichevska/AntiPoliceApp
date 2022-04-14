package com.example.antipolice.web.rest_api;

import com.example.antipolice.model.MapCoordinates;
import com.example.antipolice.service.MapCoordinatesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/anti-police")
public class AntiPoliceRestApi {

    private final MapCoordinatesService mapCoordinatesService;

    public AntiPoliceRestApi(MapCoordinatesService mapCoordinatesService) {
        this.mapCoordinatesService = mapCoordinatesService;
    }

    @GetMapping
    public List<MapCoordinates> getAll(@RequestParam(required = false) String filter){
        if(filter != null && filter.equals("mostSubmitted"))
            return mapCoordinatesService.findAllMostSubmitted();
        return mapCoordinatesService.findAllValid();
    }
}
