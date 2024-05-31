package ys_band.develop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    // 이미지 파일 경로 추가
    @Column
    private String image_path;

    public Performance() {
        this.current_seats = 0;
    }
}