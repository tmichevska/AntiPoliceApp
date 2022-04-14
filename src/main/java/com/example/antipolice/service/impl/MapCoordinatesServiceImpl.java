package com.example.antipolice.service.impl;

import com.example.antipolice.model.*;
import com.example.antipolice.model.exceptions.LatLngNotFoundException;
import com.example.antipolice.repository.MapCoordinatesRepository;
import com.example.antipolice.repository.UserRepository;
import com.example.antipolice.service.MapCoordinatesService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapCoordinatesServiceImpl implements MapCoordinatesService {

    private final MapCoordinatesRepository mapCoordinatesRepository;
    private final UserRepository userRepository;

    public MapCoordinatesServiceImpl(MapCoordinatesRepository mapCoordinatesRepository, UserRepository userRepository) {
        this.mapCoordinatesRepository = mapCoordinatesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MapCoordinates save(String latlng, String state, String username) {
        //System.out.println("HERE: "+latlng);
        if(latlng==null || latlng.equals(""))
        {
            throw new LatLngNotFoundException();
        }
        String [] parts = latlng.split(", ");
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
        return mapCoordinatesRepository.save(new MapCoordinates(new LatLng(parts[0],parts[1]), PoliceState.valueOf(state),user));
    }

    @Override
    public void save(MapCoordinates mapCoordinates) {
        mapCoordinatesRepository.save(mapCoordinates);
    }

    @Override
    public List<MapCoordinates> findAllValid() {
        return mapCoordinatesRepository.findAllByStatus(MapCoordStatus.VALID);
    }

    private boolean checkCordRadius(MapCoordinates mapCoordinate1,MapCoordinates mapCoordinate2){
        double d = Math.acos(Math.sin(Double.parseDouble(mapCoordinate1.getLatlng().getLat()) * 0.0175)
                * Math.sin(Double.parseDouble(mapCoordinate2.getLatlng().getLat()) * 0.0175)
                + Math.cos(Double.parseDouble(mapCoordinate1.getLatlng().getLat()) * 0.0175)
                * Math.cos(Double.parseDouble(mapCoordinate2.getLatlng().getLat()) * 0.0175)
                * Math.cos((Double.parseDouble(mapCoordinate1.getLatlng().getLng()) * 0.0175) - (Double.parseDouble(mapCoordinate2.getLatlng().getLng())) * 0.0175)) * 6371;

        return d <= 0.05;
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue
            (Map<K, V> map) {

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<K, V> comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Override
    public List<MapCoordinates> findAllMostSubmitted() {
        Map<String,Integer> map = new HashMap<>();

        mapCoordinatesRepository.findAllByStatus(MapCoordStatus.EXPIRED)
                .forEach(mapCoordinate1 ->{
                    List<MapCoordinates> temp = new ArrayList<>();
                    mapCoordinatesRepository.findAllByStatus(MapCoordStatus.EXPIRED)
                            .forEach(mapCoordinate2 ->{
                                if(checkCordRadius(mapCoordinate1,mapCoordinate2))
                                    temp.add(mapCoordinate2);
                    });
                    double lat = temp.stream().mapToDouble(cord -> Double.parseDouble(cord.getLatlng().getLat())).summaryStatistics().getAverage();
                    double lng = temp.stream().mapToDouble(cord -> Double.parseDouble(cord.getLatlng().getLng())).summaryStatistics().getAverage();
                    String latlng = lat +", "+lng;
                    map.putIfAbsent(latlng, temp.size());
                });

        List<Map.Entry<String,Integer>> temp = map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).collect(Collectors.toList());
        List<MapCoordinates> mapCoordinates = new ArrayList<>();
        if(temp.size() > 3) {
            int i = 1;
            while (i < 4){
                String [] parts = temp.get(temp.size() - i).getKey().split(", ");
                mapCoordinates.add(new MapCoordinates(new LatLng(parts[0],parts[1])));
                i++;
            }
        }else mapCoordinates = temp.stream().map(pair -> {
            String[] parts = pair.getKey().split(", ");
            return new MapCoordinates(new LatLng(parts[0], parts[1]));
        }).collect(Collectors.toList());

        return mapCoordinates;
    }
}
