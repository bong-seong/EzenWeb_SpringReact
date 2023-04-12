package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service // 서비스 레이어
public class MemberService {

    @Autowired
    private MemberEntityRepository memberEntityRepository;

    @Autowired
    private HttpServletRequest request; // 요청 객체

    // 1. 회원가입
    @Transactional
    public boolean write( MemberDto memberDto ){

        MemberEntity entity = memberEntityRepository.save( memberDto.toEntity() );

        if( entity.getMno() > 0 ){
            return true;
        }

        return false;
    }

    // ** 로그인 [ 시큐리티 사용하기 전 ]
    @Transactional
    public boolean getLogin( MemberDto memberDto ){
        /*
        // 1. 이메일로 엔티티 찾기
        MemberEntity entity = memberEntityRepository.findByMemail( memberDto.getMemail() );
            log.info( "entity: " + entity );

        // 2. 패스워드 검증
        if( entity.getMpassword().equals( memberDto.getMpassword() ) ){
            // == : 스택 메모리 내 데이터 비교
            // .equals() : 힙 메모리 내 데이터 비교
            // .matches() : 문자열 주어진 패턴 포함 동일여부 체크
                // 세션 사용 : 메소드 밖 필드에 @Autowired HttpServletRequest request 추가
            request.getSession().setAttribute("login" , memberDto.getMno() ) ;

            return true;
        }
         */

        // 2. 입력받은 이메일과 패스워드가 동일한지
        Optional<MemberEntity> result = memberEntityRepository.findByMemailAndMpassword( memberDto.getMemail(), memberDto.getMpassword() );
            log.info( "result: " + result );
        if( result.isPresent() ){

            request.getSession().setAttribute("login", memberDto.getMno() ) ;

            return true;
        }
        return false;
    }


    // 2. 회원정보
    @Transactional
    public MemberDto info( int mno ){

        Optional<MemberEntity> entityOptional = memberEntityRepository.findById( mno );

        if( entityOptional.isPresent() ){
            MemberEntity entity =  entityOptional.get();
            return entity.toDto();
        }

        return null;
    }

    // 3. 회원수정
    @Transactional
    public boolean update( MemberDto memberDto ){

        Optional<MemberEntity> entityOptional = memberEntityRepository.findById( memberDto.getMno() );

        if( entityOptional.isPresent() ){

            MemberEntity entity = entityOptional.get();
            entity.setMname( memberDto.getMname() );
            entity.setMphone( memberDto.getMphone() );
            entity.setMrole( memberDto.getMrole() );
            entity.setMpassword( memberDto.getMpassword() );

            return true;
        }
        return false;
    }

    // 4. 회원탈퇴
    @Transactional
    public boolean delete( int mno){

        Optional<MemberEntity> entityOptional =
            memberEntityRepository.findById( mno );

        if( entityOptional.isPresent() ){
            memberEntityRepository.delete( entityOptional.get() );
            return true;
        }

        return false;
    }






}

