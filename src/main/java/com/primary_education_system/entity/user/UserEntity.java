package com.primary_education_system.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.primary_education_system.entity.RoleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    private String name;
    private String username;
    private String email;
    private String subEmail;
    private String phone;
    private String idToken;
    private String accessToken;
    private String refreshToken;
    private Date birthday;
    //1 nam. 2 nu
    private int gender;
    private String address;
    private String job;
    private String live;
    private String zipCode;
    private Date updatedTime;
    private Date createdTime;

    private Long appId;
    private boolean isChangePassword;
    private Integer statusUser;
    private Integer userType;
    private boolean isDeleted;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserEntity() {
    }


}
