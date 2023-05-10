package ezenweb.web.domain.product;

import lombok.*;

import javax.persistence.*;

@Entity @Table( name="productimg")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductImgEntity {

        @Id private String uuidFile;    // 1. 이미지식별이름 [ pk ]

        @Column private String originalFilename; // 2. 이미지 이름

        // 3. 제품객체 [ fk ]
        @ManyToOne // fk 필드 선언시
        @JoinColumn(name = "id")
        @ToString.Exclude
        private ProductEntity productEntity;

}
