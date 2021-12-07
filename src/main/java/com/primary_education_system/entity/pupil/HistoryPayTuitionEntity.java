package com.primary_education_system.entity.pupil;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history_pay_tuition")
@Getter
@Setter
public class HistoryPayTuitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long pupilId;
    private Long quantity;
    private Date createdTime;

    public HistoryPayTuitionEntity() {
    }

    public HistoryPayTuitionEntity(Long pupilId, Long quantity, Date createdTime) {
        this.pupilId = pupilId;
        this.quantity = quantity;
        this.createdTime = createdTime;
    }
}
