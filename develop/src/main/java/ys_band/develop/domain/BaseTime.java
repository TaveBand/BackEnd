package ys_band.develop.domain;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public  abstract class BaseTime {
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modified_at;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        created_at = now;
        modified_at = now;
    }

    @PreUpdate
    protected void onUpdate() {
        modified_at = LocalDateTime.now();
    }


}
