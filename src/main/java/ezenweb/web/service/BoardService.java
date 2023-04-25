package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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

    // 4. 모든 카테고리 출력
    @Transactional
    public List<CategoryDto> categoryList() {

        List<CategoryEntity> categoryEntityList = categoryEntityRepository.findAll();

        List<CategoryDto> list = new ArrayList<>();
        // 1. 형변환 List<엔티티> ---> MAP
        categoryEntityList.forEach( (e) -> {
            list.add( new CategoryDto( e.getCno() , e.getCname() ) );
        });
        return list;
    }

    // 2. 게시물 쓰기
    @Transactional
    public byte boardWrite ( BoardDto boardDto ) {

        log.info("service board dto : " + boardDto);

        // 1. 선택된 카테고리 번호를 이용한 카테고리 엔티티 찾기
        Optional<CategoryEntity> categoryEntityOptional = categoryEntityRepository.findById( boardDto.getCno() );

        // 2. 만약에 선택된 카테고리가 존재하지 않으면 리턴
        if( !categoryEntityOptional.isPresent() ){
            return 1;
        }
        CategoryEntity categoryEntity = categoryEntityOptional.get();


        // 로그인된 회원의 엔티티 찾기
        // 1. 인증된 회원 정보 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( o.equals("anonymousUser") ){
            return 2;
        }
        // 2.
        MemberDto loginDto = (MemberDto)o;
        // 3. 회원엔티티 찾기
        MemberEntity memberEntity = memberEntityRepository.findByMemail( loginDto.getMemail() );

        // -------------------------------------------------------- //

        // 3. 게시물 쓰기
        BoardEntity boardEntity = boardEntityRepository.save( boardDto.toBoardEntity() );

        if( boardEntity.getBno() < 1 ){
            return 3;
        }

        // 4. 양방향 관계 [ 카테고리안에 게시물 대입 , 게시물안에 카테코리 대입 ]
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

        /*
        // 공지사항 게시물 정보 확인
        Optional<CategoryEntity> optionalCategory = categoryEntityRepository.findById(1);
        log.info( "공지사항 엔티티 확인 : " + optionalCategory.get() );
        log.info( "공지사항 엔티티 확인 : " + optionalCategory.get().getBoardEntityList().get(1).getMemberEntity().getMemail() );
        log.info( boardEntity.toString() );
        */

        return 4;
    }

    // 5. 카테고리별 게시물 출력
    @Transactional
    public PageDto boardList( int cno , int page ) {
        // 1. pageable 인터페이스 [ 페이징처리 관련 api ]
        Pageable pageable = PageRequest.of( page-1 , 3 , Sort.by( Sort.Direction.DESC , "bno") );
            // PageRequest.of( 페이지번호 [ 0 시작 ] , 페이지당 표시할개수 , Sort.by( Sort.Direction.ASC/DESC , '정렬기준필드명' ) );
        Page<BoardEntity> entityPage = boardEntityRepository.findBySearch( cno , pageable );

        //
        List<BoardDto> list = new ArrayList<>();
        entityPage.forEach( (b) -> {
            list.add( b.toDto() );
        });

        log.info( "총 게시물수 : " + entityPage.getTotalElements() );
        log.info( "총 페이지수 : " + entityPage.getTotalPages() );

        return PageDto.builder()
                .boardDtoList( list )
                .totalCount( entityPage.getTotalElements() )
                .totalPage( entityPage.getTotalPages() )
                .cno( cno ).page( page )
                .build();
    }


    // 3. 내가 쓴 게시물 출력
    public List<BoardDto> myboards() {
        log.info("service myboard : ");
        // 1. 로그인 인증 세션[object] --> dto 강제형변환
        MemberDto memberDto = (MemberDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            // 일반회원dto : 모든정보 // oauth2dto : memail , mname , mrole
        // 2. 회원 엔티티 찾기
        MemberEntity entity = memberEntityRepository.findByMemail( memberDto.getMemail() ) ;
        // 3. 회원 엔티티 내 게시물 리스트를 반복문 돌려 dto 리스트로 형변환
        List<BoardDto> list = new ArrayList<>();
        entity.getBoardEntityList().forEach( (e) ->{
            list.add( e.toDto() );
        });
        return list;
    }

    // 6. 선택한 게시물 출력하기
    public BoardDto selectBoard( int bno ) {

        Optional<BoardEntity> boardEntityOptional = boardEntityRepository.findById( bno );

        if( boardEntityOptional.isPresent() ){
            return boardEntityOptional.get().toDto();
        }

        return null;
    }

    // 7. 선택한 게시물 삭제하기
    public boolean deleteBoard( int bno ) {


        // 게시물 찾기
        Optional<BoardEntity> boardEntityOptional = boardEntityRepository.findById( bno );

        BoardEntity boardEntity = null;

        if( boardEntityOptional.isPresent() ){
            boardEntity = boardEntityOptional.get();
        }

        // 유효성검사 ( 본인이 등록한 게시물인지 )
            // 현재 로그인한 세션 검색
        MemberDto memberDto = (MemberDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if( boardEntity.getMemberEntity().getMemail().equals( memberDto.getMemail() ) ){
            boardEntityRepository.delete( boardEntity );
            return true;
        }else{
            return false;
        }

    }


}
