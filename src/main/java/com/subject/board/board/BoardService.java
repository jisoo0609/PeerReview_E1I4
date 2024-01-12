package com.subject.board.board;

import com.subject.board.article.ArticleRepository;
import com.subject.board.entity.ArticleEntity;
import com.subject.board.entity.BoardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;
  private final ArticleRepository articleRepository;

  // Read
  // 전체 게시판 보기
  public List<BoardEntity> readAll() {
    return boardRepository.findAll();
  }

  // 게시판 상세보기
  public BoardEntity readBoard(Long boardId) {
    return boardRepository.findById(boardId)
      .orElse(null);
  }

  // Search
  public List<ArticleEntity> search(
    Long boardId,
    String category,
    String search
  ) {
    List<ArticleEntity> articles = null;
    switch (category) {
      case "title":
        articles = articleRepository.findAllByBoard_IdAndTitleContaining(boardId, search);
        break;
      case "content":
        articles = articleRepository.findAllByBoard_IdAndContentContaining(boardId, search);
        break;
    }

    return articles;
  }
}
