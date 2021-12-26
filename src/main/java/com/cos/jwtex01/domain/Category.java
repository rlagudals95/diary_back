package com.cos.jwtex01.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import com.google.gson.annotations.Since;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // User 클래가 자동으로 MySql에 테이블 생성
@Data
@NoArgsConstructor // bean 생성자
@AllArgsConstructor
@Builder // 빌더 패턴
@Table(name = "Category")
public class Category{
	
	@Id
    @GeneratedValue
    private Long category_no;
	
	@ManyToOne
    @JoinColumn(name = "admin_no") // 외래키
    private User user;
	
	@Column(length = 100, nullable = false)
    private String name;
    
	@Column(length = 2, nullable = false)
    private String complete_yn;
	
	@Column(length = 2, nullable = false)
    private String use_yn;
	@Column(nullable = false)

    private int progress;
	
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp 
    private Date create_date;
    
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp 
    private Date update_date;
}