package com.example.restapi.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;




    @NotBlank
    @Column(name="username")
    private String username;


    @JsonIgnore
    @OneToMany
    @JoinColumn(name="user_id")
    private Set<post> posts;

    public Set<post> getPosts() {
        return posts;
    }

    public void setPosts(Set<post> posts) {
        this.posts = posts;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }


    @Column(name = "picByte", length = 10000)
    private byte[] picByte;

    @NotBlank
    @Column(name="email")
    @Email(message = "Enter a correct email adress")
    private String email;



    @Column(name = "password")
    @Size(min =7,message = "Your password should contain atleast 7 characters.")
    private String password;





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
