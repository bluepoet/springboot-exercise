package net.bluepoet.exercise;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bluepoet on 2016. 12. 27..
 */
@Component
public class MyService {

    @Transactional
    public int calculate(int a, int b) {
        return a + b;
    }
}
