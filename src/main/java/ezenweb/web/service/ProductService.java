package ezenweb.web.service;

import ezenweb.web.domain.product.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ProductService { /* 주요기능과 DB 처리 요청 */

    @Autowired

    @Transactional
    public List<ProductDto> get() {
        log.info("get : " );
        return null;
    }

    @Transactional
    public boolean post( ProductDto productDto ) {
        log.info("post : " + productDto );
        return false;
    }

    @Transactional
    public boolean put( ProductDto productDto ) {
        log.info("put : " + productDto );
        return false;
    }

    @Transactional
    public boolean delete( String id ) {
        log.info("delete : " + id );
        return false;
    }

}
