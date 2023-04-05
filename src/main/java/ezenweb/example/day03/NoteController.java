package ezenweb.example.day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController // MVC 컨트롤러
@Slf4j // 로그
@RequestMapping("/note")
public class NoteController {

    @Autowired // 생성자 자동 주입 [ * 단 스프링컨테이너에 등록이 되어있는 경우 ]
    NoteService noteService; // 서비스 객체

    // ---------------------- HTML 반환 ---------------------- //
    @GetMapping("")
    public Resource index() {
        return new ClassPathResource("/templates/note.html");
    }

    // ---------------------- RESTful API ---------------------- //
    // 1. 쓰기
    @PostMapping("/write")
    public boolean write(@RequestBody NoteDto dto ) {
        log.info( "write in : " + dto );

        // 1. 인스턴스 객체
        // NoteService service = new NoteService();
        // service.write( dto );

        // 2. @Autowired
        boolean result = noteService.write( dto );

        // * response.getwrite.print( result );
        return result;
    }
    // 2. 출력
    @GetMapping("/get")
    public ArrayList<NoteDto> get(){
        log.info( "get in");

        ArrayList<NoteDto> result = noteService.get();

        return result;
    }
    // 3. 삭제
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam int nno) {
        log.info( "delete in : " + nno );

        boolean result = noteService.delete( nno );

        return result;
    }
    // 4. 수정
    @PutMapping("/update")
    public boolean update(@RequestBody NoteDto dto) {
        log.info( "update in : " + dto );

        boolean result = noteService.update( dto );

        return result;
    }
}
