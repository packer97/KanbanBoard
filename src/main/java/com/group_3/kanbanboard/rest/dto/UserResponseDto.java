package com.group_3.kanbanboard.rest.dto;

import com.group_3.kanbanboard.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import java.util.Set;

@Schema(description = "Пользователь")
public class UserResponseDto {
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    private String secondName;
    @Schema(description = "username")
    private String username;
    @Schema(description = "E-mail")
    private String mail;
    @Schema(description = "Роль")
    private Set<UserRole> roles;

    public UserResponseDto() {
    }

    public UserResponseDto(String firstName, String secondName, String username, String mail, Set<UserRole> roles) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.username = username;
        this.mail = mail;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
