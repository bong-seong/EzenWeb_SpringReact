package ezenweb.web.service;

import ezenweb.web.domain.product.ProductDto;
import ezenweb.web.domain.product.ProductEntity;
import ezenweb.web.domain.product.ProductEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService { /* 주요기능과 DB 처리 요청 */

    @Autowired private ProductEntityRepository productEntityRepository;


    @Transactional
    public List<ProductDto> get() {
        log.info("get : " );

        // 1. 모든 엔티티 호출
        List<ProductEntity> productEntityList = productEntityRepository.findAll();

        // 2. 모든 엔티티를 DTO 로 변환
                        // js : 리스트명.forEach( o => 실행문 );       리스트명.map( o => 실행문 );
                        // java : 리스트명.forEach( o -> 실행문 );     리스트명.stream().map( o -> 실행문 );
        List<ProductDto> productDtoList = productEntityList.stream().map( o -> o.toAdminDto() ).collect( Collectors.toList() );

        return productDtoList;
    }

    @Transactional
    public boolean post( ProductDto productDto ) {
        log.info("post : " + productDto );

        // 1. id 생성 [ 오늘날짜 + 등록 밀리초 + 난수 ]
        String number = "";

        for( int i=0; i<3; i++ ) {
            number += new Random().nextInt(10);
        }

        String pid = LocalDateTime.now().format( DateTimeFormatter.ofPattern("yyyyMMddSSS") ) + number ;

        // 2. DTO에 id 넣기
        productDto.setId( pid );

        // 3. DB 저장
        productEntityRepository.save( productDto.toSaveEntity() );

        return true;
    }

    @Transactional
    public boolean put( ProductDto productDto ) {
        log.info("put : " + productDto );

        // 1. 업데이트 할 엔티티 찾기
        Optional<ProductEntity> optionalProductEntity = productEntityRepository.findById( productDto.getId() );

        if( optionalProductEntity.isPresent() ){

            ProductEntity productEntity = optionalProductEntity.get();

            productEntity.setPcategory( productDto.getPcategory() );
            productEntity.setPcomment( productDto.getPcomment() );
            productEntity.setPprice( productDto.getPprice() );
            productEntity.setPname( productDto.getPname() );
            productEntity.setPstock( productDto.getPstock() );
            productEntity.setPmanufacturer( productDto.getPmanufacturer() );
            productEntity.setPstate( productDto.getPstate() );

            return true;
        }


        return false;
    }

    @Transactional
    public boolean delete( String id ) {
        log.info("delete : " + id );

        // 1. 삭제할 엔티티 찾기
        Optional<ProductEntity> optionalProductEntity = productEntityRepository.findById( id );
        // 2. 해당 엔티티가 존재하면
        optionalProductEntity.ifPresent( o -> {
            productEntityRepository.delete( o ) ;
        });

        return true;
    }

}
