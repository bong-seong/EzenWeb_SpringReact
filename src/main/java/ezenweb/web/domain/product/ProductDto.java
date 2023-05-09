package ezenweb.web.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductDto {


    private String id;
    private String pname;
    private int pprice;
    private String pcategory;
    private String pcomment;
    private String pmanufacturer;
    private byte pstate;
    private int pstock;
    // 관리자용
    private String cdate;
    private String udate;

    // 1. 저장용 [ 관리자 페이지 ]
    public ProductEntity toSaveEntity() {
        return ProductEntity.builder()
                .id( this.id)
                .pname( this.pname )
                .pprice( this.pprice )
                .pcategory( this.pcategory )
                .pcomment( this.pcomment )
                .pmanufacturer( this.pmanufacturer )
                .pstate( this.pstate )
                .pstock( this.pstock )
                .build();
    }

    // 2. 수정용

}
