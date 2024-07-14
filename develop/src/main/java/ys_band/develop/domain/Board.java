package ys_band.develop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Board extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("board_id")
    private Long boardId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "board")
    private List<Post> posts;

}