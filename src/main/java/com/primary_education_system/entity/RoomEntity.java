package com.primary_education_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean isSelected;

    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;

    public RoomEntity() {
    }

    public RoomEntity(String name, Date createdTime, Date updatedTime) {
        this.name = name;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
