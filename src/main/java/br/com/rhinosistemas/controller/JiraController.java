package br.com.rhinosistemas.controller;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import br.com.rhinosistemas.bean.Issues;
import br.com.rhinosistemas.bean.RetornoJson;
import br.com.rhinosistemas.bean.Worklogs;
import br.com.rhinosistemas.model.AtividadesAndamento;
import br.com.rhinosistemas.model.Filtro;
import br.com.rhinosistemas.model.Sprint;
import br.com.rhinosistemas.model.TableUser;
import br.com.rhinosistemas.model.WorklogHours;
import br.com.rhinosistemas.util.Util;

@Controller
@RequestMapping(value = "/jira")
public class JiraController {

	@GetMapping(value = "/horasLogadas")
	public String iniciarHorasLogadas(HttpSession session, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		
		Filtro filtro = new Filtro();
		model.addAttribute("filtro", filtro);
		
		return "lancamentoHoras";
	}

	@GetMapping(value = "/pesquisarHorasLogadas")
	public String horasLogadas(HttpSession session, Filtro filtro, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParseException {
		
		if (StringUtils.isBlank(filtro.getKey()) || StringUtils.isBlank(filtro.getSprint())) {
			model.addAttribute("mensagemErro", "Todos os campos são obrigatórios");
			model.addAttribute("filtro", filtro);
			
			return "lancamentoHoras";
		}
		
	    String retornoDadosSprint = Util.realizarChamadaAgile(session, "sprint/" + filtro.getSprint());
		String retornoJson = Util.realizarChamadaRest(session, queryHorasLancadas(filtro.getKey(), filtro.getSprint()));
		RetornoJson retornoWorklog = new Gson().fromJson(retornoJson, RetornoJson.class);
		
		Sprint sprint = new Gson().fromJson(retornoDadosSprint, Sprint.class);
		
		Set<Date> listaDatas = new TreeSet<>(retornoWorklog.getIssues().stream()
                .flatMap(e -> e.getFields().getWorklog().getWorklogs().stream())
                .map(Worklogs::getStartedDateDay)
                .filter(date -> date.after(sprint.getStartDateDay()) && date.before(sprint.getEndDateDay()))
                .collect(Collectors.toSet()));
        
        Map<String, Map<Date, Long>> collect = retornoWorklog.getIssues().stream()
                .flatMap(e -> e.getFields().getWorklog().getWorklogs().stream())
                .filter(e -> e.getStartedDateDay().after(sprint.getStartDateDay()) && e.getStartedDateDay().before(sprint.getEndDateDay()))
                .collect(Collectors.groupingBy(
                        e -> e.getAuthor().getDisplayName(),
                        Collectors.groupingBy(Worklogs::getStartedDateDay
                                , Collectors.summingLong(Worklogs::getHoursSpent) 
                                )
                        )
                 );
        
        List<TableUser> users = new ArrayList<>();
        collect.forEach((k,v) -> {
           TableUser user = new TableUser();
           user.setUser(k);
           
           List<WorklogHours> hours = new ArrayList<>();
           for (Date date : listaDatas) {
                Long time = v.get(date);
                if (time == null) {
                    time = 0l;
                }
                
                hours.add(new WorklogHours(date, time));
           }
           user.setHours(hours);
           users.add(user);
        });
        
        model.addAttribute("listaDatas", listaDatas);
        model.addAttribute("listaHoras", users);
        
		model.addAttribute("filtro", filtro);
		
		return "lancamentoHoras";
	}
	
	@GetMapping(value = "/atividadesAndamento")
	public String iniciarAtividadesAndamento(HttpSession session, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		
		Filtro filtro = new Filtro();
		model.addAttribute("filtro", filtro);
		
		return "atividadesAndamento";
	}
	
	@GetMapping(value = "/pesquisarAtividadesAndamento")
	public String atividadesEmAndamento(HttpSession session, Filtro filtro, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParseException {
		
		if (StringUtils.isBlank(filtro.getKey()) || StringUtils.isBlank(filtro.getSprint())) {
			model.addAttribute("mensagemErro", "Todos os campos são obrigatórios");
			model.addAttribute("filtro", filtro);
			return "atividadesAndamento";
		}
		
		List<AtividadesAndamento> listaAtividades = new ArrayList<>();
		String retornoJson = Util.realizarChamadaRest(session, queryAtividadesAndamento(filtro.getKey(), filtro.getSprint()));
		
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


	/**
	 * Query para filtrar as horas lançadas por um projeto / Sprint
	 * @return
	 */
    public String queryHorasLancadas(String projeto, String sprint) {
        StringBuilder builder = new StringBuilder();

        builder.append("project = ").append(projeto);
        
        builder.append(" AND Sprint = ").append(sprint);
        
        builder.append(" ORDER BY assignee ASC");

        builder.append("&");
        builder.append("fields=worklog");
        builder.append("&");
        builder.append("maxResults=9999");

        return builder.toString();

    }

    /**
     * Query para buscar atividades em andamento
     * @return
     */
    public String queryAtividadesAndamento(String projeto, String sprint) {

	    	StringBuilder builder = new StringBuilder();
	    	
	    	builder.append("project = "+projeto+"");
	    	builder.append(" AND Sprint = "+sprint+"");
	    	builder.append(" AND status = \"In Progress\"");
	    	builder.append(" AND resolution = Unresolved");
	    	builder.append(" ORDER BY assignee ASC, updated DESC");
	    	builder.append("");
	    	
	    	return builder.toString();
    	
    }
		
}
