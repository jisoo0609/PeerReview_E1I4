package com.subject.board.comment;

import com.subject.board.article.ArticleRepository;
import com.subject.board.entity.ArticleEntity;
import com.subject.board.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;

  // Create
  public void create(
    String content,
    String password,
    Long articleId) {
    Optional<ArticleEntity> article = articleRepository.findById(articleId);

    CommentEntity comment = new CommentEntity();
    comment.setContent(content);
    comment.setPassword(password);
    comment.setArticle(article.orElse(null));
    commentRepository.save(comment);
  }

  // Read
  // 게시글별 댓글 조회
  public List<CommentEntity> findByArticleId(Long articleId) {
    return commentRepository.findByArticleId(articleId);
  }

  public CommentEntity readOne(Long commentId) {
    return commentRepository.findById(commentId)
      .orElse(null);
  }

  // Delete
  public void delete(Long commentId) {
    commentRepository.deleteById(commentId);
  }

  // 비밀번호 확인
  public Boolean checkPassword(
    Long commentId,
    String inputPassword
  ) {
    String passwrod = readOne(commentId).getPassword();
    if (passwrod.equals(inputPassword)) {
      return true;
    } else return false;
  }
}
