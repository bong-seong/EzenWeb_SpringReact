package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Table( name = "reply" )
public class ReplyEntity {

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

}
