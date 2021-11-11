package com.primary_education_system.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String rawPassword;

    private Integer statusUser; //1 un_active, 2 active

    public UserEntity() {
    }


}
