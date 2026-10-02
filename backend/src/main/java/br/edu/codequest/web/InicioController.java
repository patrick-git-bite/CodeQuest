package br.edu.codequest.web;

import br.edu.codequest.escola.EscolaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

	private final EscolaRepository escolas;

	public InicioController(EscolaRepository escolas) {
		this.escolas = escolas;
	}

	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("escolas", escolas.findAll(Sort.by("id")));
		return "inicio";
	}
}