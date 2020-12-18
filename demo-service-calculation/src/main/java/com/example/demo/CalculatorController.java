package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class CalculatorController {
    private int result;

    @GetMapping("/add")
    public MathObject getAddResult() {
        MathObject add = new MathObject();
        result = 4 + 5;
        add.setResult(result);
        return add;
    }
}