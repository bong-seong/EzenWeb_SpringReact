package ezenweb.web.service;

import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.domain.todo.TodoEntity;
import ezenweb.web.domain.todo.TodoEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class TodoService {

    @Autowired TodoEntityRepository todoEntityRepository;

    // 1. GET
    public List<TodoDto> get(){

        List<TodoEntity> entityList = todoEntityRepository.findAll() ;

        List<TodoDto> list = new ArrayList<>();

        entityList.forEach( (e) -> {
            list.add( e.toDto() );
        });

        return list;
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
