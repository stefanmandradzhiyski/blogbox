package com.snmi.repository;

import com.snmi.model.Favourite;
import org.springframework.stereotype.Repository;

/**
 * Comment statistic repository
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Repository
public interface FavouriteRepository extends BlogBoxBaseStatisticRepository<Favourite> {

}
