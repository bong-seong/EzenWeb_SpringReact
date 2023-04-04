package ezenweb.example.day02.contorller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/put")
public class PutMappingController {

    // 1.
    @PutMapping("/method1")
    public ParamDto method1( @RequestBody ParamDto dto ) {
        log.info( "put method1 : " + dto );
        return dto;
    }

    // 2.
    @PutMapping("/method2")
    public Map<String,String> method2(@RequestBody Map<String,String> map ) {
        log.info( "put method2 : " + map );
        return map;
    }

}
