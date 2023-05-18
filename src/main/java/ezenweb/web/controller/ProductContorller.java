package ezenweb.web.controller;

import ezenweb.web.domain.product.ProductDto;
import ezenweb.web.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductContorller { /* 리액트와 통신 역할[매핑] */

    @Autowired ProductService productService;

    @GetMapping("")
    public List<ProductDto> get() {
        return productService.get();
    }

    @GetMapping("/main")
    public List<ProductDto> mainGet() {
        return productService.mainGet();
    }

    @PostMapping("")
    public boolean post( ProductDto productDto ) {
        log.info("Product Write : " + productDto );
        return productService.post( productDto );
    }
    @PutMapping("")
    public boolean put( @RequestBody ProductDto productDto ) {
        return productService.put( productDto );
    }
    @DeleteMapping("")
    public boolean delete( @RequestParam String id ) {
        return productService.delete( id );
    }
}

/*
    1. 객체 전송 [ post , put ]
        axios.post( 'url' , object )
            ------> @RequestBody

    2. 폼 전송 [ post 필수 ]
        axios.post( 'url' , object )
            ------> DTO 받을때는 어노테이션 생략
            ------> @RequestParam("form 필드이름") : 폼 내 필드 하나의 데이터 매핑

    3. 쿼리스트링 전송 [ get , post , put , delete ]
        axios.post( 'url' , { params : { 필드명 : 데이터 } )
            ------> @RequestParam

    4. 매개변수 전송[ get , post , put , delete ]
        axios.post( 'url/데이터1/데이터2' )
            ------> @PathVariable
 */
