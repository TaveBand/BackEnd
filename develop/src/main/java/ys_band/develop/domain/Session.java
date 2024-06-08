package ys_band.develop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long session_id;

    @Column(nullable = false)
    private String sessionInfo;

    @ManyToMany(mappedBy = "sessions")
    @JsonIgnore
    private List<User> users;

}
