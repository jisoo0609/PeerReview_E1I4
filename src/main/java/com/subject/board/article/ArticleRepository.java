package com.subject.board.article;

import com.subject.board.entity.ArticleEntity;
import com.subject.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
  // 제목 검색
  List<ArticleEntity> findAllByTitleContaining(String title);

  // 내용 검색
  List<ArticleEntity> findAllByContentContaining(String content);

  // 게시판별 검색
  // 게시판별 제목 검색
  List<ArticleEntity> findAllByBoard_IdAndTitleContaining(Long boardId, String title);

  // 게시판별 내용 검색
  List<ArticleEntity> findAllByBoard_IdAndContentContaining(Long boardId, String content);
}
