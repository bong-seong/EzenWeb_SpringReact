package ezenweb.web.domain.board;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Table( name = "reply" )
public class ReplyEntity extends BaseTime {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int rno;

    @Column private String rcontent;
    @Column private String rdate;
    @Column private int rindex;

    @ManyToOne
    @JoinColumn( name = "mno")
    @ToString.Exclude
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn( name = "bno")
    @ToString.Exclude
    private BoardEntity boardEntity;

    public ReplyDto toDto() {
        return ReplyDto.builder()
                .rno( this.rno )
                .rcontent( this.rcontent )
                .rdate(
                        this.cdate.toLocalDate().toString().equals( LocalDateTime.now().toLocalDate().toString() ) ?
                        this.cdate.toLocalTime().format( DateTimeFormatter.ofPattern( "HH:mm:ss") ) :
                        this.cdate.toLocalDate().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) )
                )
                .rindex( this.rindex )
                .bno( this.boardEntity.getBno() )
                .mno( this.memberEntity.getMno() )
                .build();
    }
}
