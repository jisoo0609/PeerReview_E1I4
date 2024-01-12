package com.subject.board.board;

import com.subject.board.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
  private final ArticleService articleService;
  private final BoardService boardService;

  // 랜딩 페이지(전체 게시판 보기)
  @GetMapping
  public String list(Model model) {
    model.addAttribute("boardList", boardService.readAll());

    return "boardList";
  }

  // 게시판 상세보기
  @GetMapping("/{boardId}")
  public String readBoardOne(
    @PathVariable("boardId") Long boardId,
    Model model
  ) {
    model.addAttribute("board", boardService.readBoard(boardId));

    return "boardRead";
  }

  // 게시글 생성 view 페이지로 이동
  @GetMapping("{boardId}/article")
  public String createArticle(Model model) {
    model.addAttribute("boards", boardService.readAll());
    return "article/create";
  }

  // 게시판별 Search
  @PostMapping("/{boardId}/search")
  public String searchBoard(
    @PathVariable("boardId") Long boardId,
    @RequestParam("category") String category,
    @RequestParam("search") String search,
    Model model
  ) {
    model.addAttribute("board", boardService.readBoard(boardId));
    model.addAttribute("articles", boardService.search(boardId, category, search));
    return "searchBoard";
  }
}
