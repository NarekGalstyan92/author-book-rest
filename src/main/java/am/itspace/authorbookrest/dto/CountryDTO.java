package am.itspace.authorbookrest.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CountryDTO {
    private String href;
    private Name name;
    private List<String> tld;
    private String cca2;
    private String ccn3;
    private String cca3;
    private String cioc;
    private String fifa;
    private boolean independent;
    private String status;
    private boolean unMember;
    private List<Currency> currencies;
    private Idd idd;
    private List<Capital> capital;
    private List<String> altSpellings;
    private String region;
    private String subregion;
    private List<String> continents;
    private List<Language> languages;
    private LatLng latLng;
    private String landlocked;
    private double area;
    private String flag;
    private Flags flags;
    private List<Demonym> demonyms;
    private CoatOfArms coatOfArms;
    private int population;
    private Maps maps;
    private Map<String, Object> gini;
    private Car car;
    private List<String> timezones;
    private String startOfWeek;
    private Map<String, Object> postalCode;
    private GDP gdp;

    @Data
    public static class Name {
        private String common;
        private String official;
        private List<NativeName> nativeName;
        private List<Translation> translations;
    }

    @Data
    public static class NativeName {
        private String lang;
        private String official;
        private String common;
    }

    @Data
    public static class Translation {
        private String lang;
        private String official;
        private String common;
    }

    @Data
    public static class Currency {
        private String name;
        private String alphaCode;
        private String symbol;
    }

    @Data
    public static class Idd {
        private String root;
        private List<String> suffixes;
    }

    @Data
    public static class Capital {
        private String name;
        private LatLng latLng;
    }

    @Data
    public static class Language {
        private String code;
        private String name;
    }

    @Data
    public static class LatLng {
        private double lat;
        private double lng;
    }

    @Data
    public static class Flags {
        private String png;
        private String svg;
    }

    @Data
    public static class Demonym {
        private String langCode;
        private String f;
        private String m;
    }

    @Data
    public static class CoatOfArms {
        private String png;
        private String svg;
    }

    @Data
    public static class Maps {
        private String googleMaps;
        private String openStreetMaps;
    }

    @Data
    public static class GDP {
        private String currency;
        private double value;
        private int year;
    }

    @Data
    public static class Car {
        private String side;
    }
}
