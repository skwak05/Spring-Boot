package com.boot.tutorial.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.tutorial.model.Board;
import com.boot.tutorial.repository.BoardRepository;
import com.boot.tutorial.validator.BoardValidator;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardValidator boardValidator;

	@GetMapping("/list")
	public String list(Model model) {
		List<Board> boards = boardRepository.findAll();
		model.addAttribute("boards", boards);
		return "board/list";
	}

	@GetMapping("/form")
	public String form(Model model, @RequestParam(required = false) Long id) {
		if (id == null) {
			model.addAttribute("board", new Board());
		} else {
			Board board = boardRepository.findById(id).orElse(null);
			model.addAttribute("board", board);
		}
		return "board/form";
	}

	@PostMapping("/form")
	public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {
		boardValidator.validate(board, bindingResult);

		if (bindingResult.hasErrors()) {
			return "board/form";
		}
		boardRepository.save(board);
		return "redirect:/board/list";
	}

}