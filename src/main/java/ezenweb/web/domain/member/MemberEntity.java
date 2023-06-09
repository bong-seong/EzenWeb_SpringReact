package ezenweb.web.domain.member;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.board.BoardEntity;
import ezenweb.web.domain.board.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "member")
public class MemberEntity extends BaseTime {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int mno;            // 1. 회원번호

    @Column private String memail;      // 2. 회원아이디 [ 이메일 ]
    @Column private String mpassword;   // 3. 회원비밀번호
    @Column private String mname;       // 4. 회원이름
    @Column private String mphone;      // 5. 회원전화번호
    @Column private String mrole;       // 6. 회원등급

    @OneToMany( mappedBy = "memberEntity")
    @Builder.Default
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany( mappedBy = "memberEntity")
    @Builder.Default
    private List<ReplyEntity> replyEntityList = new ArrayList<>();

    //toDto 출력용
    public MemberDto toDto() {
        return MemberDto.builder()
                .mno( this.mno)
                .memail( this.memail )
                .mname( this.mname )
                .mpassword( this.mpassword )
                .mphone( this.mphone )
                .cdate( this.cdate.toString() )
                .udate( this.udate.toString() )
                .build();
    }

}
