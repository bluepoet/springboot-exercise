package net.bluepoet.exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bluepoet on 2017. 1. 1..
 */
@SpringBootApplication
public class ReactiveExercise001Application {
    @RestController
    public static class MyRestController {
        @GetMapping("/rest")
        public String rest(int idx) {
            return "rest " + idx;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveExercise001Application.class, args);
    }
}
