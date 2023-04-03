package ezenweb.example.day01Q;

import lombok.*;

import javax.persistence.*;

@Entity // DB 테이블과 해당 클래스간 매핑[ 연결 ]
@Table( name = "testboard") // DB 테이블명
@Setter @Getter @ToString   // 롬복 제공하는 getter , setter , toString
@AllArgsConstructor @NoArgsConstructor // 롬복 제공하는 빈생성자 , 풀생성자
@Builder // 롬복 제공하는 빌더 패턴
public class BoardEntity {
    @Id // PK 선언 : 생략시 오류 발생 [ JPA 엔티티/테이블 당 PK 하나 이상 무조건 선언 ]
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto key
    private int bno;
    @Column // 일반 필드 선언
    private String btitle;
    @Column // 일반 필드 선언
    private String bcontent;

}
