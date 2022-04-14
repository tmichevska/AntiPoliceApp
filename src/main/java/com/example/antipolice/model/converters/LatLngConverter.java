package com.example.antipolice.model.converters;

import com.example.antipolice.model.LatLng;

import javax.persistence.Converter;
import javax.persistence.AttributeConverter;

@Converter
public class LatLngConverter implements AttributeConverter<LatLng, String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(LatLng latlng) {
        if (latlng == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (latlng.getLat() != null && !latlng.getLat()
                .isEmpty()) {
            sb.append(latlng.getLat());
            sb.append(SEPARATOR);
        }

        if (latlng.getLng() != null
                && !latlng.getLng().isEmpty()) {
            sb.append(latlng.getLng());
        }

        return sb.toString();
    }

    @Override
    public LatLng convertToEntityAttribute(String dbLatLng) {
        if (dbLatLng == null || dbLatLng.isEmpty()) {
            return null;
        }

        String[] pieces = dbLatLng.split(SEPARATOR);

        if (pieces == null || pieces.length == 0) {
            return null;
        }

        LatLng latlng = new LatLng();
        String firstPiece = !pieces[0].isEmpty() ? pieces[0] : null;
        if (dbLatLng.contains(SEPARATOR)) {
            latlng.setLat(firstPiece);

            if (pieces.length >= 2 && pieces[1] != null
                    && !pieces[1].isEmpty()) {
                latlng.setLng(pieces[1]);
            }
        } else {
            latlng.setLng(firstPiece);
        }

        return latlng;
    }
}
