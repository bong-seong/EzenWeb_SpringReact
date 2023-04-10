package ezenweb.example.day04.service;

import ezenweb.example.day04.domain.dto.ProductDto;
import ezenweb.example.day04.domain.entity.product.ProductEntity;
import ezenweb.example.day04.domain.entity.product.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // 서비스
public class ProductService {

    @Autowired
    private ProductEntityRepository productEntityRepository;

    // 1. 저장
    public boolean write(ProductDto dto ){
        // 1. 입력받은 dto 를 엔티티로 변환 후에 save 메소드 대입 후 생성된 엔티티 얻기
        ProductEntity entity = productEntityRepository.save( dto.toEntity() );
        return entity.getPno() > 0 ;
    }

    // 2. 수정
    @Transactional
    public boolean update(ProductDto dto ){
        // 1. 수정할 번호를 이용한 엔티티 찾기 [ 검색 ]
        Optional<ProductEntity> optionalProductEntity =
            productEntityRepository.findById( dto.getPno() );
        // 2. 포장 클래스에서 포장안에 엔티티가 있는지 검사
        if( optionalProductEntity.isPresent() ){
            // 3. 만약에 있으면 true 없으면 false
            // 4. 포장객체 안에 있는 엔티티 호출
            ProductEntity entity = optionalProductEntity.get();

            entity.setPname( dto.getPname() );
            entity.setPcontent( dto.getPcontent() );
            return true;
        }
        return false;
    }


    // 3. 출력
    public ArrayList<ProductDto> get(){

        List<ProductEntity> entityList = productEntityRepository.findAll();

        ArrayList<ProductDto> list = new ArrayList<>();

        entityList.forEach( e -> {
            list.add( e.toDto() );
        });

        return list;
    }

    public boolean delete(int pno){

        Optional<ProductEntity> optionalProductEntity = productEntityRepository.findById( pno );
        if( optionalProductEntity.isPresent() ){
            ProductEntity entity = optionalProductEntity.get();
            productEntityRepository.delete( entity );
            return true;
        }
        return false;
    }

}
