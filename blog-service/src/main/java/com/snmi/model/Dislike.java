package com.snmi.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * Dislike entity is a part of statistics entities architecture
 * Represents news dislikes in the system and dislikes table in the database
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "dislikes")
public class Dislike extends BlogBoxBaseStatistic {

    @Override
    public String toString() {
        return "Dislike{}";
    }
}
