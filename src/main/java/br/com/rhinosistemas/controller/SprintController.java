package br.com.rhinosistemas.controller;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import br.com.rhinosistemas.bean.Issues;
import br.com.rhinosistemas.bean.RetornoJson;
import br.com.rhinosistemas.model.AtividadesAndamento;
import br.com.rhinosistemas.model.Filtro;
import br.com.rhinosistemas.model.Sprint;
import br.com.rhinosistemas.model.Usuario;
import br.com.rhinosistemas.util.JiraUtil;
import br.com.rhinosistemas.util.QueryUtil;
import br.com.rhinosistemas.util.TabelaUtil;
import br.com.rhinosistemas.util.Util;

@Controller
@RequestMapping(value = "/sprint")
public class SprintController {

	private static final String HORAS_SPRINT = "horasSprint";

	@GetMapping(value = "/horasLogadasSprint")
	public String iniciarHorasLogadas(HttpSession session, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		Filtro filtro = new Filtro();
		model.addAttribute("filtro", filtro);

		model.addAttribute("sprints", new ArrayList<>());

		return HORAS_SPRINT;
	}

	@GetMapping(value = "/pesquisarHorasLogadasSprint")
	public String horasLogadas(HttpSession session, Filtro filtro, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParseException {

		if (StringUtils.isBlank(filtro.getKey()) || StringUtils.isBlank(filtro.getSprint())) {
			model.addAttribute("mensagemErro", "Todos os campos são obrigatórios");
			model.addAttribute("filtro", filtro);

			return HORAS_SPRINT;
		}
		
		Usuario usuario = Util.getUsuarioSession(session);

		String retornoDadosSprint = JiraUtil.realizarChamadaAgile(usuario, "sprint/" + filtro.getSprint());
		String retornoJson = JiraUtil.realizarChamadaRest(usuario, QueryUtil.queryHorasLancadas(filtro.getKey(), filtro.getSprint()));
		RetornoJson retornoWorklog = new Gson().fromJson(retornoJson, RetornoJson.class);

		Sprint sprint = new Gson().fromJson(retornoDadosSprint, Sprint.class);

		// TreeSet<Date> listaDatas = new TreeSet<>(
		// retornoWorklog.getIssues().stream().
		// flatMap(e -> e.getFields().getWorklog().getWorklogs().stream()).
		// map(Worklogs::getStartedDateDay).
		// filter(date -> date.compareTo(sprint.getStartDateDay()) >= 0 &&
		// date.compareTo(sprint.getEndDateDay()) <= 0).
		// collect(Collectors.toSet()));

		// Map<String, Map<Date, Long>> collect = retornoWorklog.getIssues().stream().
		// flatMap(e -> e.getFields().getWorklog().getWorklogs().stream()).
		// filter(e -> e.getStartedDate().compareTo(sprint.getStartDateDay()) >= 0 &&
		// e.getStartedDate().compareTo(sprint.getEndDateDay()) <= 0)
		// .collect(Collectors.groupingBy(e -> e.getAuthor().getDisplayName(),
		// Collectors.groupingBy(Worklogs::getStartedDateDay,
		// Collectors.summingLong(Worklogs::getHoursSpent))));
		//
		// List<TableUser> listaHoras = new ArrayList<>();
		// collect.forEach((k, v) -> {
		// TableUser user = new TableUser();
		// user.setUser(k.toUpperCase());
		//
		// List<WorklogHours> hours = new ArrayList<>();
		// for (Date date : listaDatas) {
		// Long time = v.get(date);
		// if (time == null) {
		// time = 0l;
		// }
		//
		// hours.add(new WorklogHours(date, time));
		// }
		// user.setHours(hours);
		// listaHoras.add(user);
		// });
		//
		// // tira o nome cagado da sprint
		// int delimitador = sprint.getName().indexOf("{");
		// if (delimitador > 0) {
		// filtro.setSprintName(sprint.getName().substring(0, delimitador));
		// } else {
		// filtro.setSprintName(sprint.getName());
		// }
		//
		// model.addAttribute("listaDatas", listaDatas);
		// model.addAttribute("listaHoras", listaHoras);

		TabelaUtil.setListagemTabela(model, retornoWorklog, sprint.getStartDateDay(), sprint.getEndDateDay());

		model.addAttribute("filtro", filtro);

		return HORAS_SPRINT;
	}

	@GetMapping(value = "/atividadesAndamento")
	public String iniciarAtividadesAndamento(HttpSession session, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		Filtro filtro = new Filtro();
		model.addAttribute("filtro", filtro);

		return "atividadesAndamento";
	}

	@GetMapping(value = "/pesquisarAtividadesAndamento")
	public String atividadesEmAndamento(HttpSession session, Filtro filtro, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParseException {
		
		
		Usuario usuario = Util.getUsuarioSession(session);
		if (StringUtils.isBlank(filtro.getKey()) || StringUtils.isBlank(filtro.getSprint())) {
			model.addAttribute("mensagemErro", "Todos os campos são obrigatórios");
			model.addAttribute("filtro", filtro);
			return "atividadesAndamento";
		}

		List<AtividadesAndamento> listaAtividades = new ArrayList<>();
		String retornoJson = JiraUtil.realizarChamadaRest(usuario, QueryUtil.queryAtividadesAndamento(filtro.getKey(), filtro.getSprint()));

		RetornoJson retornoIssues = new Gson().fromJson(retornoJson, RetornoJson.class);

		for (Issues issue : retornoIssues.getIssues()) {
			AtividadesAndamento atividade = new AtividadesAndamento();

			atividade.setAssignee(issue.getFields().getAssignee().getDisplayName());
			atividade.setKey(issue.getKey());
			atividade.setSummary(issue.getFields().getSummary());

			listaAtividades.add(atividade);
		}

		model.addAttribute("listaAtividades", listaAtividades);
		model.addAttribute("filtro", filtro);

		return "atividadesAndamento";
	}

	@PostMapping(value = "/buscarSprintAtiva", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public String buscarSprintAtiva(@ModelAttribute @Valid Filtro filtro, BindingResult result) {

		return "";
	}
	
	// String jqlQuery = "worklogAuthor ='Alexi de Lara' or worklogAuthor ='ADRIANO
	// PAVAO' or worklogAuthor ='MONICA REIMBERG DE PAIVA' or worklogAuthor ='LUIS
	// LINO' or worklogAuthor ='KRISHAN WEISE' or worklogAuthor ='Cesar Roma' or
	// worklogAuthor ='Gabriel Silva' or worklogAuthor ='William Nascimento' or
	// worklogAuthor ='JONAS PEREZ' or worklogAuthor ='Eric Rocha' or worklogAuthor
	// ='FLAVIA ROSA' or worklogAuthor ='GUILHERME GUIARO' or worklogAuthor ='LUIZ
	// SILVA' or worklogAuthor ='NAYRA SCHALL' or worklogAuthor ='MARCO ANTONIO
	// Sousa' or worklogAuthor ='FERNANDO VICTOR ALCANTARA GORETTI' or worklogAuthor
	// ='RICARDO JUNIOR' or worklogAuthor ='FERNANDO GUIMARAES' AND worklogdate >=
	// 2018-01-01";

}
