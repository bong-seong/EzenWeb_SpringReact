package ezenweb.web.service;

import ezenweb.web.domain.board.PageDto;
import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.domain.todo.TodoEntity;
import ezenweb.web.domain.todo.TodoEntityRepository;
import ezenweb.web.domain.todo.TodoPageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class TodoService {

    @Autowired TodoEntityRepository todoEntityRepository;

    // 1. GET
    public TodoPageDto get( int page ){

        Pageable pageable = PageRequest.of( page-1 , 3 , Sort.by(Sort.Direction.DESC , "id") );

        Page<TodoEntity> todoEntityPage = todoEntityRepository.findAll( pageable );

        List<TodoDto> list = new ArrayList<>();
        todoEntityPage.forEach( (o) -> {
            list.add( o.toDto() );
        });

        return TodoPageDto.builder()
                .todoDtoList( list )
                .totalCount( todoEntityPage.getTotalElements() )
                .totalPage( todoEntityPage.getTotalPages() )
                .page( page )
                .build();
    }

    // 2. POST
    public boolean post( TodoDto todoDto ){
        TodoEntity entity = todoEntityRepository.save( todoDto.toEntity() );
        return entity.getId() > 0;
    }

    // 3. PUT
    @Transactional
    public boolean put( TodoDto todoDto ){

        Optional<TodoEntity> entityOptional = todoEntityRepository.findById( todoDto.getId() );

        if( entityOptional.isPresent() ){
            TodoEntity entity = entityOptional.get();

            entity.setTitle( todoDto.getTitle() );
            entity.setDone( todoDto.getDone() );

            return true;
        }

        return false;
    }

    // 4. DELETE
    public boolean delete( int id ){

        Optional<TodoEntity> entityOptional = todoEntityRepository.findById( id );

        if( entityOptional.isPresent() ){
            TodoEntity entity = entityOptional.get();

            todoEntityRepository.delete( entity );

            return true;
        }


        return false;
    }
}
