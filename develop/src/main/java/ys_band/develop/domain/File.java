package ys_band.develop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long file_id;

    @Column(nullable = false)
    private String file_url;

    @Column(nullable = false)
    private String file_type;

    @Column(nullable = false)
    private LocalDateTime created_at;

    private String originalFileName;        // 재현.

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
    }
}