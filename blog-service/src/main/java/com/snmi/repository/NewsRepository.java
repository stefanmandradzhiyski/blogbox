package com.snmi.repository;

import com.snmi.enums.Status;
import com.snmi.model.News;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * News repository
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findByIdAndDisplay(Long id, boolean display);
    Optional<News> findByIdAndDisplayAndStatus(Long id, boolean display, Status status);
    List<News> findAllByUserIdAndDisplay(Long userId, boolean display);
    List<News> findAllByDisplay(boolean display);
    List<News> findAllByUserIdAndDisplayAndStatus(Long userId, boolean display, Status status);

}
