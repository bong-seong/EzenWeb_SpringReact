package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoardService {

    @Autowired private BoardEntityRepository boardEntityRepository;
    @Autowired private CategoryEntityRepository categoryEntityRepository;
    @Autowired private MemberEntityRepository memberEntityRepository;

    // 1. 카테고리 등록
    public boolean categoryWrite( BoardDto boardDto ){

        log.info("service board dto : " + boardDto);

        // 1. 입력 받은 cname 을 Dto 에서 Entity 로 형변환
        CategoryEntity entity = categoryEntityRepository.save( boardDto.toCategoryEntity() );

        // 2. 만약에 생성된 엔티티의 PK 가 1보다 크면 save 성공
        if( entity.getCno() >= 1 ){
            return true;
        }

        return false;
    }

    // 2. 게시물 쓰기
    @Transactional
    public boolean boardWrite ( BoardDto boardDto ) {

        log.info("service board dto : " + boardDto);

        // 1. 선택된 카테고리 번호를 이용한 카테고리 엔티티 찾기
        Optional<CategoryEntity> categoryEntityOptional = categoryEntityRepository.findById( boardDto.getCno() );

        // 2. 만약에 선택된 카테고리가 존재하지 않으면 리턴
        if( !categoryEntityOptional.isPresent() ){
            return false;
        }
        CategoryEntity categoryEntity = categoryEntityOptional.get();


        // 로그인된 회원의 엔티티 찾기
        // 1. 인증된 회원 정보 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( o.equals("anonymousUser") ){
            return false;
        }
        // 2.
        MemberDto loginDto = (MemberDto)o;
        // 3. 회원엔티티 찾기
        MemberEntity memberEntity = memberEntityRepository.findByMemail( loginDto.getMemail() );

        // -------------------------------------------------------- //

        // 3. 게시물 쓰기
        BoardEntity boardEntity = boardEntityRepository.save( boardDto.toBoardEntity() );

        if( boardEntity.getBno() < 1 ){
            return false;
        }

        // 4. 양방향 관계
            // 1. 카테고리 엔티티에 생성된 게시물 등록
        categoryEntity.getBoardEntityList().add( boardEntity );
            // 2. 생성된 게시물에 카테고리 엔티티 등록
        boardEntity.setCategoryEntity( categoryEntity );


        // 5. 양방향 관계
            // 1. 게시물 엔티티에 로그인된 회원 등록
        boardEntity.setMemberEntity( memberEntity );
            // 2. 로그인된 회원 엔티티에 생성된 게시물 엔티티 등록
        memberEntity.getBoardEntityList().add( boardEntity );

        // --- {
        //          "bno" : 1 ,
        //          "btitle" : "title" ,
        //          "bcontent" : "content" ,
        //          "categoryEntity" :
        //              {
        //                "cno" : 1,
        //                "cname" : "공지사항"
        //              }
        //     }
        log.info( boardEntity.toString() );

        return true;
    }

    // 3. 내가 쓴 게시물 출력
    public List<BoardDto> myboards() {
        log.info("service myboard : ");
        return null;
    }

}
