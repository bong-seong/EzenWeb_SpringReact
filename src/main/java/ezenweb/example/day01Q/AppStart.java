package ezenweb.example.day01Q;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // MVC , 내장톰캣 , Restgul API , 스프링 컨테이너 기본세틍 등등
public class AppStart {
    public static void main(String[] args) {
        // SpringApplication.run( 현재클래스명.class ) ;
        SpringApplication.run( AppStart.class );
    }
}
