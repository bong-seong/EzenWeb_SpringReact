package ezenweb.web.controller;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody
@Slf4j // 로그 기능 주입
@RequestMapping("/member")
public class MemberController {

    // 1. @Autowired 없을때 객체[빈] 생성
    // MemberService service = new MemberService();

    // 2. @Autowired 있을때 객체[빈] 자동 생성
    @Autowired
    MemberService memberService;

    // 1. [ C ] 회원가입
    @PostMapping("/info")   // URL 같아도 HTTP메소드 다르므로 식별가능
    public boolean write(@RequestBody MemberDto memberDto ){ // JAVA의 클래스내 메소드 이름은 중복 불가능
        log.info("member info write : " + memberDto );
        boolean result = memberService.write(memberDto);
        return result ;
    }

    // 2. [ R ] 회원정보 호출
    @GetMapping("/info")
    public MemberDto info( @RequestParam int mno ){
        log.info("member info  : " + mno );
        MemberDto result = memberService.info(mno);
        return result ;
    }

    // 3. [ U ] 회원정보 수정
    @PutMapping("/info")
    public boolean update( @RequestBody MemberDto memberDto ){
        log.info("member info update : " + memberDto );
        boolean result = memberService.update(memberDto);
        return result;
    }

    // 4. [ D ] 회원탈퇴
    @DeleteMapping("/info")
    public boolean delete( @RequestParam int mno){
        log.info("member info delete : " + mno );
        boolean result = memberService.delete(mno);
        return result;
    }

}
