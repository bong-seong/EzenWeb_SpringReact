package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDto {

    private int rno;
    private String rcontent;
    private String rdate;
    private int rindex;
    private int bno;
    private int mno;

    public ReplyEntity toEntity() {
        return ReplyEntity.builder()
                .rcontent( this.rcontent )
                .rindex( this.rindex )
                .build();
    }

}
