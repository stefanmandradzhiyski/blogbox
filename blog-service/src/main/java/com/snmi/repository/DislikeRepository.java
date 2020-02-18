package com.snmi.repository;

import com.snmi.model.Dislike;
import org.springframework.stereotype.Repository;

/**
 * Dislikes statistic repository
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Repository
public interface DislikeRepository extends BlogBoxBaseStatisticRepository<Dislike> {

}
