package kr.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@Controller
public class BoardController {
	
	@Autowired//DI 의존성 주입
	private BoardMapper mapper; 
	
	@RequestMapping("/boardList.do") //HandlerMapping
	public String boardList(Model model) { //Model model객체바인딩
		List<Board> list = mapper.getLists();
		model.addAttribute("list", list); //객체바인딩
		return "boardList";
	}
	
	@GetMapping("/boardForm.do") //HandlerMapping
	public String boardForm() {
		return "boardForm";  // forward경로 
	}
	@PostMapping("/boardInsert.do")
	public String boardInsert(Board vo) { //여러개 동시에 받을 때 vo
		mapper.boardInsert(vo);
		return "redirect:/boardList.do"; //redirect 경로 
	}
	@GetMapping("/boardContent.do")
	public String boardContent(@RequestParam("idx") int idx, Model model) { //단일값만 받을 때 
		Board vo = mapper.boardContent(idx);
		// 조회수 증가
		mapper.boardCount(idx);
		model.addAttribute("vo", vo); 
		return "boardContent";
	}
	@GetMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable("idx") int idx) { //단일값만 받을 때 
		mapper.boardDelete(idx);
		return "redirect:/boardList.do";
	}
	@GetMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable("idx") int idx, Model model) { //Model model 화면에 데이터 넘길 때는 객체 바인딩 필요 
			//<a href="boardContent.do?idx=${vo.idx}"> == int idx 동일하다면 @PathVariable("idx")  생략가능 
		Board vo=mapper.boardContent(idx);
		model.addAttribute("vo", vo);		
		return "boardUpdate"; // boardUpdate.jsp
	}
	@PostMapping("/boardUpdate.do")
	public String boardUpdate(Board vo) { // idx, title, content
		mapper.boardUpdate(vo); // 수정		
		return "redirect:/boardList.do";
	}
}
