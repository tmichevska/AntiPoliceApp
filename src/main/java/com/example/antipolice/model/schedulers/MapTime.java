package com.example.antipolice.model.schedulers;

import com.example.antipolice.model.MapCoordStatus;
import com.example.antipolice.service.MapCoordinatesService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;


@Component
public class MapTime {
    private final MapCoordinatesService mapCoordinatesService;

    public MapTime(MapCoordinatesService mapCoordinatesService) {
        this.mapCoordinatesService = mapCoordinatesService;
    }

    @Scheduled(fixedRate = (1000*1800))
    public void reportCurrentTime() {
        mapCoordinatesService.findAllValid().forEach(mapCoordinate ->{
            long secondsDifference = Duration.between(mapCoordinate.getTimeSubmited(),LocalDateTime.now()).getSeconds();
            int threeHoursInSec = 3*3600;
            if (secondsDifference >= threeHoursInSec) {
                mapCoordinate.setStatus(MapCoordStatus.EXPIRED);
                mapCoordinatesService.save(mapCoordinate);
            }
        });
    }
}
