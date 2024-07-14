package ys_band.develop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching //레디스 캐싱 하기 위함. @Cacheable 같은 어노테이션 인식
public class DailBandApplication {

    public static void main(String[] args) {

        SpringApplication.run(DailBandApplication.class, args);
    }

}
