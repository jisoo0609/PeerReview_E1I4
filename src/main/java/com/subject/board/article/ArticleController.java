package com.subject.board.article;

import com.subject.board.board.BoardService;
import com.subject.board.comment.CommentService;
import com.subject.board.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
  private final ArticleService articleService;
  private final BoardService boardService;
  private final CommentService commentService;

  // Create
  // 게시글 생성 view로 이동
  @GetMapping("/create")
  public String createPage() {
    return "article/create";
  }

  // create 로직
  @PostMapping("/create")
  public String create(
    @RequestParam("board-id") Long boardId,
    @RequestParam("title") String title,
    @RequestParam("content") String content,
    @RequestParam("password") String password
  ) {
    articleService.create(boardId, title, content, password);
    return "redirect:/article";
  }

  // Read
  // 전체 보기(= 전체 게시판)
  @GetMapping
  public String readAll(Model model) {
    model.addAttribute("articles", articleService.readAll());
    return "home";
  }

  // 상세 보기
  @GetMapping("/{articleId}")
  public String readOne(
    @PathVariable("articleId") Long articleId,
    Model model
  ) {
    model.addAttribute("article", articleService.readOne(articleId));
    model.addAttribute("comments", commentService.findByArticleId(articleId));
    return "article/read";
  }

  // Update
  // 비밀번호 view
  @GetMapping("/{articleId}/password-view/update")
  public String passwordViewUpdate(
    @PathVariable("articleId") Long articleId,
    Model model
  ) {
    model.addAttribute("article", articleService.readOne(articleId));
    model.addAttribute("type", "article");
    model.addAttribute("method", "update");
    return "password";
  }

  // 비밀번호 확인 후 -> update view로 이동
  @PostMapping("/{articleId}/passwordCheck/update")
  public String checkPasswordUpdate(
    @PathVariable("articleId") Long articleId,
    @RequestParam("input-password") String inputPassword,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (articleService.checkPassword(articleId, inputPassword)) {
      // 비밀번호 일치
      model.addAttribute("article", articleService.readOne(articleId));
      model.addAttribute("boards", boardService.readAll());
      return "article/update";
    } else {
      // 비밀번호 불일치
      redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
      return "redirect:/article/" + articleId + "/password-view/update"; // 다시 비밀번호 입력 페이지로 리다이렉트
    }
  }

  // update 실행
  @PostMapping("/{articleId}/update")
  public String update(
    @PathVariable("articleId") Long articleId,
    @RequestParam("board-id") Long boardId,
    @RequestParam("title") String title,
    @RequestParam("content") String content
  ) {
    articleService.update(articleId, boardId, title, content);
    return String.format("redirect:/article/%d", articleId);
  }

  // Delete
  // 비밀번호 view
  @GetMapping("/{articleId}/password-view/delete")
  public String passwordViewDelete(
    @PathVariable("articleId") Long articleId,
    Model model
  ) {
    model.addAttribute("article", articleService.readOne(articleId));
    model.addAttribute("type", "article");
    model.addAttribute("method", "delete");
    return "password";
  }

  // 비밀번호가 일치하면 삭제, 틀리면 경고창
  @PostMapping("/{articleId}/passwordCheck/delete")
  public String checkPasswordDelete(
    @PathVariable("articleId") Long articleId,
    @RequestParam("input-password") String inputPassword,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (articleService.checkPassword(articleId, inputPassword)) {
      articleService.delete(articleId);
      return "redirect:/article";
    } else {
      redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
      return "redirect:/article/" + articleId + "/password-view/delete"; // 다시 비밀번호 입력 페이지로 리다이렉트
    }
  }

  // Search
  @PostMapping("/search")
  public String searchArticle(
    @RequestParam("category") String category,
    @RequestParam("search") String search,
    Model model
  ) {
    model.addAttribute("articles", articleService.search(category, search));
    return "article/searchArticle";
  }
}
