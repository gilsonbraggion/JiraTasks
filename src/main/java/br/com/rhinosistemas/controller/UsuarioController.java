package br.com.rhinosistemas.controller;

import br.com.rhinosistemas.bean.RetornoJson;
import br.com.rhinosistemas.config.JiraConfig;
import br.com.rhinosistemas.model.Filtro;
import br.com.rhinosistemas.model.Sprint;
import br.com.rhinosistemas.model.Usuario;
import br.com.rhinosistemas.util.JiraUtil;
import br.com.rhinosistemas.util.QueryUtil;
import br.com.rhinosistemas.util.TabelaUtil;
import br.com.rhinosistemas.util.Util;

import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

	private static final String HORAS_USUARIO = "horasUsuario";
	
	@Autowired
    private JiraConfig config;

	@GetMapping(value = "/horasLogadasUsuario")
	public String iniciarHorasLogadas(HttpSession session, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		Filtro filtro = new Filtro();
		model.addAttribute("filtro", filtro);

		return HORAS_USUARIO;
	}

	@GetMapping(value = "/pesquisarHorasLogadasUsuario")
	public String horasLogadas(HttpSession session, Filtro filtro, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParseException {

		Usuario usuario = Util.getUsuarioSession(session);
		
		if (usuario == null) {
			return LoginController.RETORNO_LOGIN;
		}

		if (filtro.getDataInicio() == null) {
			model.addAttribute("mensagemErro", "Todos os campos são obrigatórios");
			model.addAttribute("filtro", filtro);

			return HORAS_USUARIO;
		}

		String retornoDadosSprint = JiraUtil.realizarChamadaAgile(config, usuario, "sprint/" + filtro.getSprint());
		String retornoJson = JiraUtil.realizarChamadaRest(config, usuario, QueryUtil.queryHorasPorUsuario());
		RetornoJson retornoWorklog = new Gson().fromJson(retornoJson, RetornoJson.class);

		Sprint sprint = new Gson().fromJson(retornoDadosSprint, Sprint.class);

		TabelaUtil.setListagemTabela(model, retornoWorklog, sprint.getStartDateDay(), sprint.getEndDateDay());

		model.addAttribute("filtro", filtro);

		return HORAS_USUARIO;
	}

}