package com.primary_education_system.entity.token;

import com.primary_education_system.entity.user.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private TokenType tokenType;
    private TypeUserToken typeUserToken;
    private Long userId;
    private Long deviceId;
    private Boolean isUsed;
    private Boolean isDeleted;
    private Date createdTime;

    public TokenEntity(String tokenString, TokenType forgotPasswordToken) {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public TypeUserToken getTypeUserToken() {
        return typeUserToken;
    }

    public void setTypeUserToken(TypeUserToken typeUserToken) {
        this.typeUserToken = typeUserToken;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public TokenEntity() {

    }

    public TokenEntity(String token) {
        this.token = token;
    }

    public TokenEntity(String token, TokenType tokenType, TypeUserToken typeUserToken, Long userId, Long deviceId) {
        this.token = token;
        this.tokenType = tokenType;
        this.typeUserToken = typeUserToken;
        this.userId = userId;
        this.deviceId = deviceId;
        this.isUsed = false;
        this.isDeleted = false;
        this.createdTime = new Date();
    }
}
