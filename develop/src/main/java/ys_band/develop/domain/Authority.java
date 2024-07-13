package ys_band.develop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Entity
@Data
@Table(name="authority")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name="authority_name")
    private String authorityName;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;

    @Override
    public String toString() {
        return "Authority{" +
                "authorityName='" + authorityName + '\'' +
                '}';
    }


}
