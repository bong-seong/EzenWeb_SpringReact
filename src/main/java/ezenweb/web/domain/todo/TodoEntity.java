package ezenweb.web.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity @Builder
@Table(name = "todo")
public class TodoEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column private String title;
    @Column private boolean done;


    public TodoDto toDto() {
        return TodoDto.builder()
                .id( this.id )
                .title( this.title )
                .done( this.done )
                .build();
    }
}
