package com.subject.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ArticleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;
  @Column(nullable = false)
  private String password;
  @ManyToOne
  private BoardEntity board;
  @OneToMany(mappedBy = "article")
  private List<CommentEntity> comments;
}
