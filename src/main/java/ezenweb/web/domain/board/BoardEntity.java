package ezenweb.web.domain.board;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.member.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Table( name = "board")
public class BoardEntity extends BaseTime {

    @Id @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int bno;    // 게시물 번호

    @Column( nullable = false ) private String btitle; // 게시물 제목 [ 기본값 255 ]
    // @Column( columnDefinition = "MySQL 자료형" )
    @Column( columnDefinition = "text" ) private String bcontent; // [ MySQL text 자료형 ]

    @Column
    @ColumnDefault( "0" )
    private int bview; // 조회수

    // 작성일 , 수정일 BaseTime 클래스로부터 상속받아서 사용
    

    // FK = 외래키 [ 카테고리번호 = cno , 회원번호 = mno ]

    // 카테고리번호
    @ManyToOne // 다수가 하나에게 [ FK --> PK ]
    @JoinColumn( name = "cno" ) // FK필드명
    @ToString.Exclude // 해당 필드는 ToString 제외 필드 [ * 양방향 필수 ]
    private CategoryEntity categoryEntity;

    // 회원번호 [ 작성자 ]
    @ManyToOne //
    @JoinColumn( name = "mno" )
    @ToString.Exclude
    private MemberEntity memberEntity;



    // pk = 양방향 [ 해당 댓글 엔티티의 fk 필드와 매핑 ]
    // 댓글목록
    @OneToMany( mappedBy = "boardEntity")
    @Builder.Default
    private List<ReplyEntity> replyEntityList = new ArrayList<>();

    // 출력용 Entity --> Dto
    public BoardDto toDto(){
        return BoardDto.builder()
                .bno( this.bno )
                .btitle( this.btitle )
                .bcontent( this.bcontent )
                .cno( this.getCategoryEntity().getCno() )
                .cname( this.getCategoryEntity().getCname() )
                .mno( this.getMemberEntity().getMno() )
                .memail( this.getMemberEntity().getMemail().split("@")[0] )
                .bview( this.bview )
                // 날짜형변환
                .bdate(
                        // 삼항 연산자 [ 조건 ? 참 : 거짓 ]
                        // 만약에 작성 날짜/시간중 날짜가 현재 날짜와 동일하면
                        this.cdate.toLocalDate().toString().equals( LocalDateTime.now().toString() ) ?
                                this.cdate.toLocalTime().format( DateTimeFormatter.ofPattern( "HH:mm:ss") ) :
                                this.cdate.toLocalDate().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) )
                )
                .build();
    }
}

/*
    cdate [ LocalDateTime ]

 */
