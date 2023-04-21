package ezenweb.web.controller;

import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController @Slf4j
@CrossOrigin(origins = "http://192.168.17.34:3000") // 해당 컨트롤러는 http://localhost:3000 요청 CORS 정책 적용
@RequestMapping("/todo")
public class TodoController {

    @Autowired TodoService todoService;

    @GetMapping("")
    public List<TodoDto> get(){     // TodoDto , TodoService , TodoEntity , Repository 작업
        // [ 과제 ] 서비스 구현
        /*
        // 테스트 [ 서비스에게 전달 받았다는 가정 ]
        List<TodoDto> list = new ArrayList<>();
        list.add(new TodoDto( 1 , "게시물1" , true ) );
        list.add(new TodoDto( 2 , "게시물2" , false ) );
        list.add(new TodoDto( 3 , "게시물3" , true ) );
        */
        return todoService.get(); // 서비스에서 리턴 결과를 axios 에게 응답
    }


    @PostMapping("")
    public boolean post(@RequestBody TodoDto todoDto ){
        return todoService.post( todoDto );
    }


    @PutMapping("")
    public boolean put( @RequestBody TodoDto todoDto ){
        log.info("put : " + todoDto );
        return todoService.put( todoDto );
    }


    @DeleteMapping("")
    public boolean delete( @RequestParam int id ){
        return todoService.delete( id );
    }
}

