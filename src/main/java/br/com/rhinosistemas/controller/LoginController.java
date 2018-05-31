package br.com.rhinosistemas.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.rhinosistemas.model.Usuario;
import br.com.rhinosistemas.util.JiraSingleton;

@Controller()
public class LoginController {
	
	public static final String RETORNO_LOGIN = "login";

	@GetMapping(value="")
	public String getLogin(Model model) {
		
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return RETORNO_LOGIN;
	}
	
	@RequestMapping(value="/login")	
	public String realizarLogin(HttpSession session, Usuario usuario, Model model) throws URISyntaxException {
		
		if (StringUtils.isBlank(usuario.getUsuario()) || StringUtils.isBlank(usuario.getPassword())) {
			model.addAttribute("mensagemErro", "Usuário ou senha não preenchidos");
			return RETORNO_LOGIN;
		}
		
		session.setAttribute("usuario", usuario);
		
		JiraSingleton.getAccountService(session);
		
		return "filtros";
	}

}
