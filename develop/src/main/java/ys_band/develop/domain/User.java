package ys_band.develop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;


    //고운 db는 schoolId로 되어있을 듯함.
    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nickname;

    //@Column(nullable = false)
   //Private String firstName;

    //@Column(nullable = false)
    //private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

//////
    private String session;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Scrap> scraps;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    @OneToMany(mappedBy = "user")
    private List<Youtube> youtubeList;


    // Performances 엔티티 설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Performance> performances;

    // Reservations 엔티티 설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservation> reservations;

    @ManyToMany
    @JoinTable(
            name = "user_session",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "session_id")
    )
    @JsonIgnore
    private List<Session> sessions;

    @ManyToMany(fetch = FetchType.EAGER) //User 객체는 데이터베이스에서 Lazy Loading으로 연관된 엔티티(예: authorities)를 가져오도록 설정되어 있습니다. 그러나 User 객체를 로드한 후에 영속성 컨텍스트가 닫혀서 연관된 데이터를 로드하려고 하면 예외가 발생
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "userId",referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name",referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", sessions=" + sessions +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }

}