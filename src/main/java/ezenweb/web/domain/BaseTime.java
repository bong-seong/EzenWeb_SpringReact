package ezenweb.web.domain;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass @EntityListeners( AuditingEntityListener.class ) // @EnableJpaAuditing 필수
public class BaseTime {

    @CreatedDate
    public LocalDateTime cdate;    // 1. 생성날짜

    @LastModifiedDate
    public LocalDateTime udate;    // 2. 수정날짜

}
