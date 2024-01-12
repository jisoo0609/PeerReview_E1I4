package com.subject.board.comment;

import com.subject.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
  List<CommentEntity> findByArticleId(Long articleId);
}
