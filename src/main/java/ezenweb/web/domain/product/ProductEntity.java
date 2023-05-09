package ezenweb.web.domain.product;

import ezenweb.web.domain.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Data @AllArgsConstructor @NoArgsConstructor @Builder
@Entity @Table( name = "product" )
public class ProductEntity extends BaseTime {

    @Id private String id; // 제품번호 [ JPA는 1개 이상 ID 필요! ]
    @Column( nullable = false ) private String pname;           // 제품명 [ JPA 로 DB 필드 선언시 _ : 제외 ]
    @Column( nullable = false ) private int pprice;             // 제품가격
    @Column( nullable = false ) private String pcategory;       // 제품카테고리
    @Column( nullable = true , columnDefinition = "TEXT" ) private String pcomment; // 제품 설명
    @Column( nullable = false , length = 100 ) private String pmanufacturer;        // 제조사
    @ColumnDefault("0")@Column( nullable = false ) private byte pstate;             // 제품상태 [ 0 : 판매중 , 1 : 판매중 , 2 : 재고없음 ]
    @ColumnDefault("0")@Column( nullable = false ) private int pstock;              // 제품 재고/수량

    // 제품이미지 [ 1 : 다 ] 연관관계
    // 구매내역 [ 1 : 다 ] 연관관계 [ *추후 ]

    // 1. 출력용 [ 관리자보는 입장 - 관리자 페이지에서 출력하는 용도 ]
    public ProductDto toAdminDto() {
        return ProductDto.builder()
                .id( this.id)
                .pname( this.pname )
                .pprice( this.pprice )
                .pcategory( this.pcategory )
                .pcomment( this.pcomment )
                .pmanufacturer( this.pmanufacturer )
                .pstate( this.pstate )
                .pstock( this.pstock )
                .cdate( this.cdate.toString() )
                .udate( this.cdate.toString() )
                .build();
    }

    // 2. 출력용 [ 사용자보는 입장 - 메인 페이지에서 출력하는 용도 ]
    // public ProductDto toUserDto(){}

}
