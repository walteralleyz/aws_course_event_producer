package br.walleyz.aws_course.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/dog/{name}")
    public ResponseEntity<?> getDog(@PathVariable String name) {
        logger.info("name: {}", name);

        return ResponseEntity.ok(name);
    }

    @GetMapping("/hello/{name}")
    public ResponseEntity<?> sayHello(@PathVariable String name) {
        logger.info("Application says hello to: {}", name);

        return ResponseEntity.ok("Hello " + name);
    }
}
