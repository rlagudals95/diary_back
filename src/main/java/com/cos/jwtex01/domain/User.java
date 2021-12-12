package com.cos.jwtex01.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//    @Column(unique = true)
//    private String username;
//    @Column(unique = true)
//    private String email;
//    private String password;
//    private String role;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long admin_no;
	
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 400, nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;

    @Column(name = "role")
    private String role;
    

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp // insert 시 자동 생성
    private Date create_date;
    
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp // update 시 자동 생성
    private Date update_date;
    
}
