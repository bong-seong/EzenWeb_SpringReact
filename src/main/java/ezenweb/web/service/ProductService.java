package ezenweb.web.service;

import ezenweb.web.domain.file.FileDto;
import ezenweb.web.domain.product.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
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
    @Autowired private ProductImgEntityRepository productImgEntityRepository;
    @Autowired private FileService fileService;

    // 1. [ main 출력용 ] 현재 판매중인 제품만 호출
    @Transactional
    public List<ProductDto> mainGet(){
        List<ProductEntity> productEntityList = productEntityRepository.findAllState();

        List<ProductDto> productDtoList = productEntityList.stream().map(
                o -> o.toMainDto()
        ).collect( Collectors.toList() );

        return productDtoList;
    }

    @Transactional
    public List<ProductDto> get() {
        log.info("get : " );

        // 1. 모든 엔티티 호출
        List<ProductEntity> productEntityList = productEntityRepository.findAll();

        // 2. 모든 엔티티를 DTO 로 변환
                        // js : 리스트명.forEach( o => 실행문 );       리스트명.map( o => 실행문 );
                        // java : 리스트명.forEach( o -> 실행문 );     리스트명.stream().map( o -> 실행문 );
        List<ProductDto> productDtoList = productEntityList.stream().map(
                o -> o.toAdminDto()
        ).collect( Collectors.toList() );

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
        ProductEntity productEntity = productEntityRepository.save( productDto.toSaveEntity() );

        // 4. 첨부파일 업로드
        // 만약에 첨부파일이 1개 이상이면
        if( productDto.getPimgs().size() > 0 ){
            // 하나씩 업로드
            productDto.getPimgs().forEach( (img) ->{
                // 업로드된 파일 결과 [ 리턴 ]
                FileDto fileDto = fileService.fileUpload( img );

                // DB 저장
                ProductImgEntity productImgEntity = productImgEntityRepository.save( ProductImgEntity.builder()
                                                        .originalFilename( fileDto.getOriginalFilename() )
                                                        .uuidFile( fileDto.getUuidFile() )
                                                        .build()
                                                    );

                // 단방향 : 이미지객체에 제품객체 등록
                productImgEntity.setProductEntity( productEntity );

                // 양방향 : 제품객체에 이미지객체 등록
                productEntity.getProductImgEntityList().add( productImgEntity );
            });
        }

        return true;
    } // 2 end



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

            // 3. 파일도 같이 삭제
            if( o.getProductImgEntityList().size() > 0 ) {
                o.getProductImgEntityList().forEach( (img) -> {
                    // 1. 해당 이미지 경로를 찾아서 파일 객체화 
                    File file = new File( fileService.path + img.getUuidFile() );
                    // 2. 만약에 해당 경로의 파일이 존재하면 삭제
                    if( file.exists()){
                        file.delete();
                    }
                });
            }

            // 4. DB/엔티티 삭제
            productEntityRepository.delete( o ) ;
        });
        return true;

    }
}
