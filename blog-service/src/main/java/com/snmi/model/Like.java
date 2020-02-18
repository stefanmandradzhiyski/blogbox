package com.snmi.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * Like entity is a part of statistics entities architecture
 * Represents news likes in the system and likes table in the database
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "likes")
public class Like extends BlogBoxBaseStatistic {

    @Override
    public String toString() {
        return "Like{}";
    }
}
