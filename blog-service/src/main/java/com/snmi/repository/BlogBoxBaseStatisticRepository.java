package com.snmi.repository;

import java.util.List;

import com.snmi.model.BlogBoxBaseStatistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * BlogBoxBaseStatisticRepository is no repository bean
 * If other repository want to inherit all methods just need to extend that repo
 * @param <T> take other repository which entity extends the BlogBoxBaseStatistic superclass
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@NoRepositoryBean
public interface BlogBoxBaseStatisticRepository<T extends BlogBoxBaseStatistic> extends CrudRepository<T, Long> {

    boolean existsByNewsIdAndUserId(Long newsId, Long userId);
    void deleteByNewsIdAndUserId(Long newsId, Long userId);
    void deleteAllByNewsId(Long newsId);
    List<T> findAllByNewsId(Long newsId);
    List<T> findAllByUserId(Long userId);
    Long countByUserId(Long userId);

}
