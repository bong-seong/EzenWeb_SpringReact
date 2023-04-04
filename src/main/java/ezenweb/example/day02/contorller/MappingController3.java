package ezenweb.example.day02.contorller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// 스프링 관리하는 IoC 컨테이너 빈[객체] 등록
@RestController // @ResponseBody + @Controller
@Slf4j      // 스프링 로그 메소드 제공 [ trace < debug < info < warn < error ]
public class MappingController3 {

    @GetMapping( "/blue" )
    public String getBlue(){
        log.info("클라이언트로부터 getBlue 메소드 요청이 들어옴");
        return "정상 응답";
    }

    @PostMapping( "/blue" )
    public String postBlue(){
        log.info("클라이언트로부터 postBlue 메소드 요청이 들어옴");
        return "정상 응답";
    }

    @PutMapping( "/blue" )
    public String putRed(){
        log.info("클라이언트로부터 putBlue 메소드 요청이 들어옴");
        return "정상 응답";
    }

    @DeleteMapping( "/blue" )
    public String deleteBlue(){
        log.info("클라이언트로부터 deleteBlue 메소드 요청이 들어옴");
        return "정상 응답";
    }

}


/*
    크롬/ajax/js -------- 요청 --------->   서블릿 컨테이너    --------> Dispatcher Servlet
        http://localhost:8080/orange                                    핸들러 매핑으로 해당 컨트롤러( 스프링 빈 등록 ) 검색
                                                            --------> Mapping 검색
                                                                         @RequestMapping( value = "/orange", method = RequestMethod.GET )
                          <------------------ 응답 -------------------

 */