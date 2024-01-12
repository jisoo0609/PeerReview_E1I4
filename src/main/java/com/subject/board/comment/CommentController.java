package com.subject.board.comment;

import com.subject.board.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;
  private final ArticleService articleService;

  // Create
  @PostMapping("/{articleId}/comment")
  public String createComment(
    @PathVariable("articleId") Long articleId,
    @RequestParam("content") String content,
    @RequestParam("password") String password
  ) {
    commentService.create(content, password, articleId);
    return String.format("redirect:/article/%d", articleId);
  }

  // 비밀번호 확인
  // delete-password-view
  @GetMapping("/{articleId}/comment/{commentId}/password-view")
  public String passwordViewComment(
    @PathVariable("articleId") Long articleId,
    @PathVariable("commentId") Long commentId,
    Model model
  ) {
    model.addAttribute("article", articleService.readOne(articleId));
    model.addAttribute("comment", commentService.readOne(commentId));
    model.addAttribute("type", "comment");
    model.addAttribute("method", "delete");
    return "password";
  }

  // Delete
  @PostMapping("/{articleId}/comment/{commentId}/delete")
  public String deleteComment(
    @PathVariable("articleId") Long articleId,
    @PathVariable("commentId") Long commentId,
    @RequestParam("input-password") String inputPassword,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (commentService.checkPassword(commentId, inputPassword)) {
      // 비밀번호 일치
      commentService.delete(commentId);
      return String.format("redirect:/article/%d", articleId);
    } else {
      // 비밀번호 불일치
      redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
      return "redirect:/article/" + articleId + "/password-view/update"; // 다시 비밀번호 입력 페이지로 리다이렉트
    }
  }

}
