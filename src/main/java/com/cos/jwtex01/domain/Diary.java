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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // User 클래가 자동으로 MySql에 테이블 생성
@Data
@NoArgsConstructor // bean 생성자
@AllArgsConstructor
@Builder // 빌더 패턴
@Table(name = "Diary")
@DynamicInsert // null 것(role)은 제외하고 insert 그래야 role의 default값(user)이 들어감
public class Diary{
	
	@Id
    @GeneratedValue
    private Long diary_no;
	
	@ManyToOne
    @JoinColumn(name = "admin_no") // 외래키
    private User user;

	@Column(length = 1000, nullable = false)
    private String title;	
	
    @Column(length = 5000, nullable = false)
    private String content;
    
    @Temporal(TemporalType.DATE)
    @CreationTimestamp // insert 시 자동 생성
    private Date create_date;
    
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp // update 시 자동 생성
    private Date update_date;
}