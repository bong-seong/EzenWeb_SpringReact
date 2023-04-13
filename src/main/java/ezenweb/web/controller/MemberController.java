package ezenweb.web.controller;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody
@Slf4j // 로그 기능 주입
@RequestMapping("/member")
public class MemberController {

    // 1. 회원가입 페이지 반환
    @GetMapping("/signup") // localhost:8080/member/signup 요청시 아래 템플릿[html] 반환
    public Resource getSignup(){
        return new ClassPathResource("templates/member/signup.html");
    }

    // 2. 로그인페이지 반환
    @GetMapping("/login") // localhost:8080/member/login
    public Resource getLogin(){
        return new ClassPathResource("templates/member/login.html");
    }

    // 3. 회원 수정 페이지 반환
    @GetMapping("/update")
    public Resource getUpdate(){
        return new ClassPathResource("templates/member/update.html");
    }

    // 4. 회원 삭제 페이지 반환
    @GetMapping("/delete")
    public Resource getDelete(){
        return new ClassPathResource("templates/member/delete.html");
    }

    // 5. 아이디 찾기 페이지 반환
    @GetMapping("/findid")
    public Resource getFindId(){
        return new ClassPathResource("templates/member/findid.html");
    }

    // 6. 비밀번호 찾기 페이지 반환
    @GetMapping("/findpw")
    public Resource getFindPw(){
        return new ClassPathResource("templates/member/findpw.html");
    }



    

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
    public MemberDto info(){
        return memberService.info();
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
    public boolean delete( @RequestParam int mno , @RequestParam String mpassword ){
        log.info("member info delete : " + mno );
        log.info("member info delete : " + mpassword );
        boolean result = memberService.delete( mno , mpassword );
        return result;
    }

    // ------------ 스프링 시큐리티 적용될 경우 아래코드는 사용 X ------------

    /*
    @PostMapping("/login")
    public boolean getLogin( @RequestBody MemberDto memberDto ) {
        boolean result = memberService.getLogin(memberDto);
        return result;
    }
    */
    /*
    @GetMapping("/logout")
    public boolean logout(){
        return memberService.logout();
    }
    */

    // 과제
    @PostMapping("/findid")
    public String findid( @RequestBody MemberDto memberDto ) {
        return memberService.findid(memberDto);
    }
    @PostMapping("/findpw")
    public String findpw( @RequestBody MemberDto memberDto ) {
        return memberService.findpw(memberDto);
    }




}
