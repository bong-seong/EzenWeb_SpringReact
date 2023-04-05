package ezenweb.example.day03;

// 비지니스 로직 담당 ( 실질적인 업무 담당 )

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service    // MVC 서비스
@Slf4j
public class NoteService {

    @Autowired
    NoteEntityRepository noteEntityRepository;

    // 1. 쓰기
    public boolean write( NoteDto dto ){
        log.info("service write in : " + dto );

        // 1. DTO --> 엔티티로 변환 후 SAVE
        // dto 안에는 nno x
        NoteEntity entity = noteEntityRepository.save( dto.toEntity() );
        if( entity.getNno() > 0 ){
            // 레코드가 생성되었으면 [ 등록 성공 ]
            return true;
        }
        return false;
    }

    // 2. 출력
    public ArrayList<NoteDto> get(){
        log.info("service get in");

        // 1. 모든 엔티티 호출
        List<NoteEntity> entityList = noteEntityRepository.findAll();
        // 2. 모든 엔티티 형변환 [ 엔티티 --> DTO ]
        ArrayList<NoteDto> list = new ArrayList<>();

        entityList.forEach( e -> {
            list.add( e.toDto() );
        });

        return list ;
    }

    // 3. 삭제
    public boolean delete( int nno ){
        log.info("service delete in : " + nno );

        // 1. 삭제할 식별변호 [ PK ]를 이용한 엔티티 검색 [ 검색성공시 : 엔티티 / 검색실패사 : null ]
        Optional<NoteEntity> optionalNoteEntity = noteEntityRepository.findById( nno );
        // 2. 포장클래스 <엔티티> : null 에 대한
        if( optionalNoteEntity.isPresent() ){ // 포장 클래스내 엔티티가 들어있으면
            NoteEntity noteEntity = optionalNoteEntity.get(); // 엔티티 꺼내기
            noteEntityRepository.delete( noteEntity ); // 찾은 엔티티를 리포지토리 통해 삭제하기
            return true;
        }
        return false;
    }

    // 4. 수정
    // @Transactional : 해당 메소드내 엔티티객체 필드의 변화가 있을경우 실시간으로 commit 처리
    @Transactional // import javax.transaction.Transactional
    public boolean update( NoteDto dto ){
        log.info("service update in : " + dto );
        // 1. 수정할 pk 번호를 이용한 엔티티 검색
        Optional<NoteEntity> optionalNoteEntity = noteEntityRepository.findById( dto.getNno() );
        // 2. 포장클래스
        if( optionalNoteEntity.isPresent() ){
            NoteEntity noteEntity = optionalNoteEntity.get(); // 엔티티 꺼내기
            // 3. 객체 내 필드변경 --> 엔티티 객체 필드 변경 --> 해당 레코드의 필드 값 변경
            noteEntity.setNcontent( dto.getNcontent() );
            return true;
        }
        return false;
    }

}
