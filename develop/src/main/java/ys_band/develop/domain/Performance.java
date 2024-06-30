package ys_band.develop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long performance_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String content; // 추가된 속성

    @Column(nullable = false)
    private java.sql.Date date;

    @Column(nullable = false)
    private java.sql.Time time;

    @Column(nullable = false)
    private String venue;

    @Column(nullable = false)
    private int total_seats;

    @Column(nullable = false)
    private int current_seats;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    @Column
    private String image_path;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
    public Performance() {
        this.current_seats = 0;
    }


}