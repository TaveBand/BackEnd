package ys_band.develop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("board_id")
    private Long boardId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime modified_at;

    @OneToMany(mappedBy = "board")
    private List<Post> posts;

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
        modified_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modified_at = LocalDateTime.now();
    }
}