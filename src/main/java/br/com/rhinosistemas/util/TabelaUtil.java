package br.com.rhinosistemas.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.ui.Model;

import br.com.rhinosistemas.bean.RetornoJson;
import br.com.rhinosistemas.bean.Worklogs;
import br.com.rhinosistemas.model.TableUser;
import br.com.rhinosistemas.model.WorklogHours;

public class TabelaUtil {

	public static void setListagemTabela(Model model, RetornoJson retornoWorklog, Date dataInicio, Date dataFinal) {

		TreeSet<Date> listaDatas = new TreeSet<>(retornoWorklog.getIssues().stream().flatMap(e -> e.getFields().getWorklog().getWorklogs().stream()).map(Worklogs::getStartedDateDay).filter(date -> date.compareTo(dataInicio) >= 0 && date.compareTo(dataFinal) <= 0).collect(Collectors.toSet()));

		Map<String, Map<Date, Long>> collect = retornoWorklog.getIssues().stream().flatMap(e -> e.getFields().getWorklog().getWorklogs().stream()).filter(e -> e.getStartedDate().compareTo(dataInicio) >= 0 && e.getStartedDate().compareTo(dataFinal) <= 0)
				.collect(Collectors.groupingBy(e -> e.getAuthor().getDisplayName(), Collectors.groupingBy(Worklogs::getStartedDateDay, Collectors.summingLong(Worklogs::getHoursSpent))));

		List<TableUser> listaHoras = new ArrayList<>();
		collect.forEach((k, v) -> {
			TableUser user = new TableUser();
			user.setUser(k.toUpperCase());

			List<WorklogHours> hours = new ArrayList<>();
			for (Date date : listaDatas) {
				
				Long time = v.get(date);
				if (time == null) {
					time = 0l;
				}

				hours.add(new WorklogHours(date, time));
			}
			user.setHours(hours);
			listaHoras.add(user);
		});

		model.addAttribute("listaDatas", listaDatas);
		model.addAttribute("listaHoras", listaHoras);

	}

}
