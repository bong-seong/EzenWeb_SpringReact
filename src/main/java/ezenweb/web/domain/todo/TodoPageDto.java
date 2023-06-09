package ezenweb.web.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class TodoPageDto {

    private long totalCount;                    // 1. 전체 게시물 수
    private int totalPage;                      // 2. 전체 페이지수
    private List<TodoDto> todoDtoList;        // 3. 현재페이지의 게시물 dto 들
    private int page ;                          // 4. 현재 페이지번호
    private int id ;                           // 5. 현재 카테고리번호
}
