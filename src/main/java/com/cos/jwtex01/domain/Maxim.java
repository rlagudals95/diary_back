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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Maxim {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long maxim_no;
	
    @ManyToOne
    @JoinColumn(name = "admin_no") // 외래키
    private User user;
    
    @Column(unique = true, nullable = false)
    private String content;
  
    @Column(nullable = false)
    private Long like;
    
    @Column(length = 2, nullable = true)
    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp // insert 시 자동 생성
    private Date create_date;
    
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp // update 시 자동 생성
    private Date update_date;
    
}
