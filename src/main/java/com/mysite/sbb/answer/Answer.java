package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity		// JPA에서 자바객체를 DB의 테이블에 매핑
public class Answer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;		//Primary Key, 자동 중가 (1,1)
	
	//@Column(columnDefinition = "TEXT")
	@Column(length = 4000)
	private String content;
	
	private LocalDateTime createDate;  //create_date : DB에 저장된 컬럼명
	
	private LocalDateTime modifyDate;
	
	@ManyToOne		//Foreign key : 부모테이블의 PK, UK 컬럼의 값을 참조해서 값을 할당.
	private Question question;		//부모테이블인 Question 테이블의 Primary Key를 참조 (id)
					//question_id : 실제 DB에 저장된 컬럼명
	
	@ManyToOne
	private SiteUser author;
	
	@ManyToMany
	Set<SiteUser> voter;
	
}
