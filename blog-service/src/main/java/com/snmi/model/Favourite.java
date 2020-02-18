package com.snmi.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * Favourite entity is a part of statistics entities architecture
 * Represents news and user favourites in the system and favourites table in the database
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "favourites")
public class Favourite extends BlogBoxBaseStatistic {

    @Override
    public String toString() {
        return "Favourite{}";
    }
}
