package br.com.rhinosistemas.controller;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import br.com.rhinosistemas.bean.Issues;
import br.com.rhinosistemas.bean.RetornoWorklog;
import br.com.rhinosistemas.bean.Worklog;
import br.com.rhinosistemas.bean.Worklogs;
import br.com.rhinosistemas.model.Filtro;
import br.com.rhinosistemas.model.HorasLogadas;
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
	
	
	@PostMapping(value = "/pesquisarHorasLogadas")
	public String horasLogadas(HttpSession session, Filtro filtro, Model model) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParseException {
		
		String retornoJson = Util.realizarChamadaRest(session, queryHorasLancadas(filtro.getKey(), filtro.getSprint()));
		
		RetornoWorklog retornoWorklog = new Gson().fromJson(retornoJson, RetornoWorklog.class);
		
		Map<String, HorasLogadas> mapRetorno = new HashMap<>();
		
		for (Issues issue : retornoWorklog.getIssues()) {
			
			Worklog worklog = issue.getFields().getWorklog();
			
			for (Worklogs work : worklog.getWorklogs()) {
				
				String nomeUsuario = work.getAuthor().getDisplayName();
				Long horasRegistradas = work.getHoursSpent();
				String dataRegistro = work.getStarted();
				String dataMap = Util.formataData(dataRegistro);
				
				String chaveMap = nomeUsuario + dataMap; 
				
				HorasLogadas horas = new HorasLogadas();
				if (!mapRetorno.containsKey(chaveMap)) {
					
					horas.setNomeUsuario(nomeUsuario);
					Date data = Util.convertStringToDate(dataRegistro);
					horas.setDataWorkLog(data);
					
					horas.setQuantidadeHoras(horasRegistradas);
				} else {
					horas = mapRetorno.get(chaveMap);
					Long horasAcumuladas = horas.getQuantidadeHoras() + horasRegistradas;
					horas.setQuantidadeHoras(horasAcumuladas);
				}
				
				mapRetorno.put(chaveMap, horas);

			}
			
		}
		
		System.out.println(mapRetorno);
		
//		Map<String, Map<Date, Long>> collect = retornoWorklog.getIssues().stream()
//				.flatMap(e -> e.getFields().getWorklog().getWorklogs().stream())
//				.collect(Collectors.groupingBy(
//						e -> e.getAuthor().getDisplayName(),
//						Collectors.groupingBy(Worklogs::getStartedDate
//								, Collectors.summingLong(Worklogs::getHoursSpent) 
//								)
//						)
//						);
//		
//		collect.forEach((k,v) -> System.out.println(k + " = " + v));
		model.addAttribute("filtro", filtro);
		
		return "lancamentoHoras";
	}
	
	
	

//		String jqlQuery = "project = STIBR AND status = \"In Progress\" AND resolution = Unresolved AND Sprint = 9040 ORDER BY assignee ASC, updated DESC";

//		String jqlQuery = "sprint=9040 AND key=STIBR-991 AND worklogdate >= 2018-01-01 AN";
	
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
		public String queryHorasLancadas(String projeto, String sprint ) {
			StringBuilder builder = new StringBuilder();
			
			
			builder.append("project = STIBR AND Sprint = 9040 ORDER BY assignee ASC");
			
//			builder.append("project="+projeto+"");
//			
//			if (StringUtils.isNotBlank(sprint)) {
//				builder.append(" and ");
//				builder.append("sprint="+sprint+"");
//			}
			
			builder.append("&");
			builder.append("fields=worklog");
			builder.append("&");
			builder.append("maxResults=9999");
//			builder.append(" order by worklogAuthor ASC");
			
			return builder.toString();

		}
		
}
