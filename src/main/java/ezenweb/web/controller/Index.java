package ezenweb.web.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {

    @GetMapping("/") // localhost:8080 요청시 아래 템플릿
    public Resource Resource() {
        return new ClassPathResource("templates/index.html");
    }
}
