package com.subject.board.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String content;
  @Column(nullable = false)
  private String password;
  @ManyToOne
  private ArticleEntity article;
}
