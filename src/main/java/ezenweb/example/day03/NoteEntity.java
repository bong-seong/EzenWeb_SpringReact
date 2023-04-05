package ezenweb.example.day03;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 객체 = 레코드 , 테이블 = 클래스
@Entity // 테이블과 해당 클래스 객체간 매핑 [ 연결 ]
@Table( name = "note")
@Data   // getter, setter , toString
@AllArgsConstructor @NoArgsConstructor @Builder
public class NoteEntity {

    @Id // PK primary key // JPA 사용시 한개 이상 필수
    @GeneratedValue( strategy = GenerationType.IDENTITY) // auto key
    private int nno;

    @Column
    private String ncontent;

    // Entity --> Dto
    public NoteDto toDto() {
        return NoteDto.builder()
                .nno( this.nno )
                .ncontent( this.ncontent )
                .build();
    }
}
