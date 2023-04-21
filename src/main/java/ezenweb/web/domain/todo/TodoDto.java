package ezenweb.web.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class TodoDto {

    private int id;         // Todo 식별변호
    private String title;   // Todo 내용
    private boolean done;   // 체크여부

    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .title( this.title )
                .done( this.done )
                .build();
    }

    public boolean getDone() {
        return this.done;
    }
}
