package ezenweb.web.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Integer> {

    // 1. 해당 이메일로 엔티티 찾기
        // 인수로 들어온 email 과 동일한 엔티티[레코드] 찾아서 반환
        // select * from member where memail = ? ;
    MemberEntity findByMemail( String memail );
    // 2. 해당 이메일과 비밀번호가 일치한 엔티티 여부 확인
        // 인수로 들어온 이메일과 패스워드가 모두 일치하는 엔티티[레코드] 찾아서 존재 여부 반환 [ true/false ]
        // select * from member where memail =? and mpassword =? ;
    Optional<MemberEntity> findByMemailAndMpassword( String memail, String mpassword );
    // 3. [ 중복체크 활용 ] 만약에 동일한 이메일이 존재 하면 true 아니면 false
    boolean existsByMemail( String memail );
    boolean existsByMphone( String mphone );
    // 4. [ 로그인 활용 ] 만약에 동일한 이메일과 패스워드가 존재하면 true 아니면 false
    boolean existsByMemailAndMpassword( String memail, String mpassword );

    Optional<MemberEntity> findByMnoAndMpassword( int mno, String mpassword );

    // 아이디찾기 [ 이름 , 전화번호 ]
    Optional<MemberEntity> findByMnameAndMphone( String mname, String mphone );
    // 비밀번호찾기 [ 아이디 , 전화번호 ]
    boolean existsByMemailAndMphone( String memail, String mphone );

    /*
    // * query 예시
    @Query("select * from member where memail = ?")
    MemberEntity 아이디로엔티티찾기( String memail );
     */
}
/*
    .findById( pk 값 ) : 해당하는 pk 값이 검색 후 존재하면 레코드(엔티티) 반환
    .findAll() : 모든 레코드(엔티티) 반환
    .save( 엔티티 ) : 해당 엔티티를 DB 레코드로 INSERT
    .delete( 엔티티 ) : 해당 엔티티를 DB레코드에서 delete
    @Transactional --> setter 메소드 이용 : 수정
    --------> 그 외 추가 메소드 만들기
    검색 [ 레코드 반환 ]
            .findBy필드명( 인수 )            select * from member where memail = ? ;
            .findBy필드명And필드명            select * from member where memail = ? and mpassword =? ;
            .findBy필드명or필드명
     검색여부 [ true , false ]
        .existsBy필드명
     *
        Optional 필수 X
            MemberEntity
            Optional<MemberEntity>
     검색된 레코드 반환
        Optional<MemberEntity>  :


 */