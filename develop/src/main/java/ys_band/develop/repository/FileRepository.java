package ys_band.develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ys_band.develop.domain.File;

public interface FileRepository extends JpaRepository<File,Long> {
}
