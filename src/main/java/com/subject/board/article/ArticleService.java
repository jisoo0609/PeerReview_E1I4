package com.subject.board.article;

import com.subject.board.board.BoardRepository;
import com.subject.board.entity.ArticleEntity;
import com.subject.board.entity.BoardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
  private final BoardRepository boardRepository;
  private final ArticleRepository articleRepository;

  // Create
  public void create(
    // board의 pk를 받아온다.
    Long boardId,
    String title,
    String content,
    String password
  ) {
    Optional<BoardEntity> board = boardRepository.findById(boardId);

    ArticleEntity article = new ArticleEntity();
    article.setTitle(title);
    article.setContent(content);
    article.setPassword(password);
    article.setBoard(board.orElse(null));
    articleRepository.save(article);
  }

  // Read
  // 전체 보기
  public List<ArticleEntity> readAll() {
    return articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
  }

  // 상세보기
  public ArticleEntity readOne(Long articleId) {
    return articleRepository.findById(articleId)
      .orElse(null);
  }

  // Update
  public void update(
    Long articleId,
    Long boardId,
    String title,
    String content
  ) {
    Optional<BoardEntity> board = boardRepository.findById(boardId);

    ArticleEntity article = readOne(articleId);
    article.setTitle(title);
    article.setContent(content);
    article.setBoard(board.orElse(null));
    articleRepository.save(article);
  }

  // Delete
  public void delete(Long articleId) {
    articleRepository.deleteById(articleId);
  }

  // 비밀번호 확인
  public Boolean checkPassword(
    Long articleId,
    String inputPassword
  ) {
    String password = readOne(articleId).getPassword();
    if (password.equals(inputPassword)){
      return true;
    }
    else return false;
  }

  // Search
  public List<ArticleEntity> search(
    String category,
    String search
  ) {
    List<ArticleEntity> articles = null;
    switch (category) {
      case "title":
        articles = articleRepository.findAllByTitleContaining(search);
        break;
      case "content":
        articles = articleRepository.findAllByContentContaining(search);
        break;
    }

    return articles;
  }
}
