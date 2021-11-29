package com.primary_education_system.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String name;
    private String username;

    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthday;
    private String address;
    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;
    private boolean isChangePassword;
    private boolean isHomeroomTeacher;

    private Integer statusUser; //1 un_active, 2 active

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    public UserEntity() {
    }


}
