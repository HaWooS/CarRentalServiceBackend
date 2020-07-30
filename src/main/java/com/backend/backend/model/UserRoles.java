package com.backend.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int user_id;
    @Column
    private int role_id;

    public UserRoles(){}

    public UserRoles(int role_id) {
        this.role_id = role_id;
    }

    public UserRoles(int user_id, int role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
