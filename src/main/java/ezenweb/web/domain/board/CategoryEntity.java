package ezenweb.web.domain.board;

import ezenweb.web.domain.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Table( name = "bcategory")
public class CategoryEntity extends BaseTime {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int cno;

    @Column
    private String cname;
    // 양방향
    // 카테고리[pk] <----> 게시물[fk]
    // pk 테이블에는 fk 흔적 남긴적이 없다. [필드 존재x 객체 존재o ]
    @OneToMany( mappedBy = "categoryEntity") // 하나가 다수에게 [ PK --> FK ]
    @Builder.Default
    private List<BoardEntity> boardEntityList = new ArrayList<>();



}
