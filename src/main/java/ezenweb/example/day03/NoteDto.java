package ezenweb.example.day03;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 롬복 사용
@Data   // getter, setter , toString
@AllArgsConstructor @NoArgsConstructor @Builder
public class NoteDto {

    // 1. 필드
    private int nno;
    private String ncontent;

    // dto --> Entity [ 서비스에서 사용 ]
    public NoteEntity toEntity() {
        return NoteEntity.builder()
                .nno( this.nno )
                .ncontent( this.ncontent )
                .build();
    }
}
