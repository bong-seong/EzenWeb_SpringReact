package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.*;

// UserDetailsService : 일반유저 서비스 --> loadUserByUsername 구현
// OAuth2UserService : oauth2 유저 서비스 구현 --->

@Slf4j
@Service // 서비스 레이어
public class MemberService implements UserDetailsService , OAuth2UserService<OAuth2UserRequest , OAuth2User> {

    @Override // 토큰 결과 [ JSON { 필드명 : 값 , 필드명 : 값 } vs MAP { 키=값 , 키=값 } ]
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

            log.info( "서비스 매개변수 : " + userRequest );

        // 1. 인증[로그인] 결과 토큰 확인
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
            log.info("서비스 정보 : " + oAuth2UserService.loadUser( userRequest) );

        // 2. 전달받은 정보 객체
        OAuth2User oAuth2User = oAuth2UserService.loadUser( userRequest);
            log.info("회원정보 : " + oAuth2User.getAuthorities() );

        // 3. 클라이언트 ID 식별 [ 응답된 JSON 구조 다르기 때문에 클라이언트 ID 별 (구글vs카카오vs네이버) 로 처리 ]
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
            log.info("클라이언트 ID : " + registrationId );

        String email = null;
        String name = null;

        if( registrationId.equals( "kakao" ) ) { // 만약에 카카오 회원이면

            Map<String, Object> kakao_account = (Map<String , Object>)oAuth2User.getAttributes().get("kakao_account");
            log.info("카카오 회원 정보 : " + kakao_account );
            Map<String, Object> profile = (Map<String, Object>)kakao_account.get("profile");
            log.info("카카오 프로필 정보 : " + profile );

            email = (String)kakao_account.get("email");
            log.info("카카오 이메일 : " + email );
            name = (String)profile.get("nickname");
            log.info("카카오 이름 : " + name );

        }else if( registrationId.equals( "naver" ) ) { // 만약에 네이버 회원이면

            Map<String , Object> response = (Map<String, Object>)oAuth2User.getAttributes().get("response");
            log.info("네이버 회원 정보 : " + response );

            email = (String)response.get("email");
            name = (String)response.get("nickname");

        }else if( registrationId.equals( "google" ) ) { // 만약에 구글 회원이면

            email =  (String)oAuth2User.getAttributes().get( "email" );
            log.info("구글 이메일 : " + email );
            name = (String)oAuth2User.getAttributes().get( "name" );
            log.info("구글 이름 : " + name );

        }

        // !!!! : oAuth2User.getAttributes() map< String , Object > 구조
        // 구글 Attributes : { sub=123124534536, name=이름 , given_name=이름 , email=email@email.com }
        // 카카오 Attributes : [{id=2748109943, connected_at=2023-04-14T02:08:07Z, properties={nickname=김성봉}, kakao_account={profile_nickname_needs_agreement=false, profile={nickname=김성봉}


        // 인가 객체 [ OAuth2User ----> MemberDto 통합Dto ( 일반+oauth ) ]
        MemberDto memberDto = new MemberDto();
        memberDto.set소셜회원정보( oAuth2User.getAttributes() );
        memberDto.setMemail( email );
        memberDto.setMname( name );
            Set<GrantedAuthority> 권한목록 = new HashSet<>();
            SimpleGrantedAuthority 권한 = new SimpleGrantedAuthority( "ROLE_user" );
            권한목록.add( 권한 );
        memberDto.set권한목록( 권한목록 );

        // 1. DB 저장하기 전에 해당 이메일로 된 이메일 존재하는지 검사
        MemberEntity entity = memberEntityRepository.findByMemail( email );
        if( entity == null ) { // 첫방문
            // DB 처리 [ 첫 방문시에만 db 등록 , 그이후 방문시부터는 db 수정 ]
            memberDto.setMrole("oauthuser"); // DB에 저장할 권한명
            memberEntityRepository.save( memberDto.toEntity() );
        }else{ // 두번째 방문 이상이면 수정처리
            entity.setMname( name );
        }
        return memberDto;
    }

    @Autowired
    private MemberEntityRepository memberEntityRepository;

    // 1. 일반 회원가입 [ 본 애플리케이션 가입한 사람 ]
    @Transactional
    public boolean write( MemberDto memberDto ){

        MemberEntity checkentity = memberEntityRepository.findByMemail( memberDto.getMemail() );

        if( checkentity != null ){
            return false;
        }

        // 스프링 시큐리티에서 제공하는 암호화[ 사람이 이해하기 어렵고 컴퓨터는 이해할수 있는 단어 ] 사용하기
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                // 인코더 : 특정 형식으로변경 // 디코더 : 원본으로 되돌리기
        log.info("비크립트 암호화 사용 : " + passwordEncoder.encode("1234") );
        // 입력받은[DTO] 패스워드 암호화해서 다시 DTO에 저장
        memberDto.setMpassword( passwordEncoder.encode( memberDto.getMpassword() ) );
        // 등급 부여
        memberDto.setMrole("user");

        MemberEntity entity = memberEntityRepository.save( memberDto.toEntity() );

        if( entity.getMno() > 0 ){
            return true;
        }
        return false;
    }

    // ** 로그인 [ 시큐리티 적용 후 ]
        // 시큐리티는 API [ 누군가가 미리 만들어둔 메소드 안에서 커스터마이징[ 수정 ] ]


    // ** 로그인 [ 시큐리티 사용하기 전 ]
    /*
    @Transactional
    public boolean getLogin( MemberDto memberDto ){

        // 1. 이메일로 엔티티 찾기
        MemberEntity entity = memberEntityRepository.findByMemail( memberDto.getMemail() );
            log.info( "entity: " + entity );

        // 2. 찾은 엔티티 안에는 암호화된 패스워드
            // 엔티티안에 있는 패스워드[암호화된상태]와 입력받은 패스워드[안된상태]와 비교
        if( new BCryptPasswordEncoder().matches( memberDto.getMpassword() , entity.getMpassword() ) ){
            // == : 스택 메모리 내 데이터 비교
            // .equals() : 힙 메모리 내 데이터 비교
            // .matches() : 문자열 주어진 패턴 포함 동일여부 체크
                // 세션 사용 : 메소드 밖 필드에 @Autowired HttpServletRequest request 추가
            request.getSession().setAttribute("login" , memberDto.getMemail() ) ;

            return true;
        }


        // 2. 입력받은 이메일과 패스워드가 동일한지
        Optional<MemberEntity> result = memberEntityRepository.findByMemailAndMpassword( memberDto.getMemail(), memberDto.getMpassword() );
            log.info( "result: " + result );
        if( result.isPresent() ){

            request.getSession().setAttribute("login", memberDto.getMno() ) ;

            return true;
        }

        // 3.
        boolean result = memberEntityRepository.existsByMemailAndMpassword( memberDto.getMemail() , memberDto.getMpassword() );
        if( result ){
            request.getSession().setAttribute("login", memberDto.getMemail() ) ;
            return true;
        }


        return false;
    }
    */



    // 3. 회원수정
    @Transactional
    public boolean update( MemberDto memberDto ){

        Optional<MemberEntity> entityOptional = memberEntityRepository.findById( memberDto.getMno() );

        if( entityOptional.isPresent() ){

            MemberEntity entity = entityOptional.get();
            entity.setMname( memberDto.getMname() );
            entity.setMphone( memberDto.getMphone() );

            return true;
        }
        return false;
    }

    // 4. 회원탈퇴
    @Transactional
    public boolean delete( int mno , String mpassword ){

        Optional<MemberEntity> entityOptional = memberEntityRepository.findById( mno );

        if( entityOptional.isPresent() ){

            MemberEntity entity = entityOptional.get();

            if( new BCryptPasswordEncoder().matches( mpassword, entity.getMpassword() ) ){
                memberEntityRepository.delete( entity );
                return true;
            }
        }


        // Optional<MemberEntity> entityOptional = memberEntityRepository.findById( mno );
        return false;
    }
    
    // 5. 아이디 중복 확인
    public boolean idcheck( String memail ){
        log.info("service member idcheck : " + memail );
        return memberEntityRepository.existsByMemail( memail );
    }

    // 6. 전화번호 중복 확인
    public boolean phonecheck( String mphone ){
        log.info("service member phonecheck : " + mphone );
        return memberEntityRepository.existsByMphone( mphone );
    }

    

    // [ 스프링 시큐리티 적용했을때 사용되는 메소드 ]
    @Override
    public UserDetails loadUserByUsername( String memail ) throws UsernameNotFoundException {
        // 1. UserDetailsService 인터페이스 구형
        // 2. loadUserByUsername() 메소드 : 아이디 검증
            // 패스워드 검증 [ 시큐리티 자동 ]
        // 3. 검증 후 세션에 저장할 DTO 반환
        MemberEntity entity = memberEntityRepository.findByMemail( memail );

        if( entity == null) { throw new UsernameNotFoundException("해당 계정의 회원이 없습니다."); }

        MemberDto dto = entity.toDto();
            // Dto 권한(여러개) 넣어주기
        // 1. 권한목록 만들기
        Set<GrantedAuthority> 권한목록 = new HashSet<>();
        // 2. 권한객체 만들기 [ DB 에 존재하는 권한명( ROLE_!!!! ) 으로 ]
            // 권한 없을경우 : ROLE_ANONYMOUS / 권한 있을경우 ROLE_권한명 : ROLE_admin , ROLE_user
        SimpleGrantedAuthority 권한명 = new SimpleGrantedAuthority( "ROLE_" + entity.getMrole() );
        // 3. 만든 권한객체를 권한목록[컬렉션]에 추가
        권한목록.add( 권한명 );
        // 4. UserDetails에 권한목록 대입
        dto.set권한목록( 권한목록 );

        log.info( "dto : " + dto);
        return dto ; // UserDetails : 원본 데이터의 검증할 계정 , 패스워드 포함
    }


    // 2. [ 세션에 존재하는 ] 회원정보
    @Transactional
    public MemberDto info(){
        // 1. 시큐리티 인증[로그인] 된 UserDetails객체[세션]로 관리 했을때 [ Spring ]
            // SecurityContextHolder : 시큐리티 정보 저장소
            // SecurityContextHolder.getContext() : 시큐리티 저장된 정보 호출
            // SecurityContextHolder.getContext().getAuthentication() : 인증정보 호출
        log.info( "Auth : " + SecurityContextHolder.getContext().getAuthentication() );
        log.info( "Principal : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 인증된 회원의 정보

        if( o.equals("anonymousUser") ){ return null; }
            // [ Principal ]
            // 인증 실패시 : anonymousUser
            // 인증 성공시 : Dto

        // 2. 인증된 객체 내 회원정보 [ principal ] 타입 변환
        return (MemberDto) o ; // ? Object ----> dto

        /*
        // * 일반 세션으로 로그인 정보를 관리했을때
        String memail = (String)request.getSession().getAttribute("login");
        if( memail != null ){
            MemberEntity entity = memberEntityRepository.findByMemail( memail );

            MemberDto dto = entity.toDto();
            log.info("dto : " + dto );
            return dto ;
        }
        */
    }

    /*
    // 2. [ 세션에 존재하는 정보 제거 ] 로그아웃
    @Transactional
    public boolean logout() {
        request.getSession().setAttribute("login", null );
        return true;
    }
    */


    // 아이디 찾기
    public String findid( MemberDto dto ) {
        Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findByMnameAndMphone( dto.getMname(), dto.getMphone() );
        if( optionalMemberEntity.isPresent() ){
            MemberEntity entity = optionalMemberEntity.get();
            return entity.toDto().getMemail();
        }
        return null;
    }

    // 비밀번호 찾기
    @Transactional
    public String findpw( MemberDto dto ) {

        boolean result = memberEntityRepository.existsByMemailAndMphone( dto.getMemail(), dto.getMphone() );

        String newpwd = "";

        if( result ){

            // 6자리 난수 생성하기
            Random random = new Random();

            for( int i=0; i<6; i++ ){
                newpwd += random.nextInt(9);
            }

            MemberEntity entity = memberEntityRepository.findByMemail( dto.getMemail() );
            entity.setMpassword( new BCryptPasswordEncoder().encode( newpwd ) );

            return newpwd;
        }
        return null;
    }
}


