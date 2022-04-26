package com.example.antipolice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class AntiPoliceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntiPoliceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}


/*

*ENTITETI*
-3 glavni entiteti
-2 pomoshni entiteti (converter za Latitude and Longitude)


*RELACII*
-2 relacii ManyToOne
-1 relacija OneToOne
-Nitu edna ManyToMany



*TEHNOLOGII*
-jQuery
-Rest Api
-Leaflet JS
-Thymeleaf (HTML)
-CSS
-Bootstrap
-Spring JPA
-Spring Security
-MySql Database
-Cron / cron job (za avtomatsko izvrshuvanje na akcii na odreden vremenski interval)

 */
