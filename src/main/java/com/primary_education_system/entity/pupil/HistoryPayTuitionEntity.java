package com.primary_education_system.entity.pupil;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date createdTime;

}
