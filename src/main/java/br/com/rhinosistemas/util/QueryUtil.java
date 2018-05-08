package br.com.rhinosistemas.util;

public class QueryUtil {

	/**
	 * Query para filtrar as horas lançadas por um projeto / Sprint
	 * 
	 * @return
	 */
	public static String queryHorasLancadas(String projeto, String sprint) {
		StringBuilder builder = new StringBuilder();

		builder.append(" Sprint = ").append(sprint);

		builder.append(" ORDER BY assignee ASC");

		builder.append("&");
		builder.append("fields=worklog");
		builder.append("&");
		builder.append("maxResults=9999");

		return builder.toString();

	}

	/**
	 * Query para buscar atividades em andamento
	 * 
	 * @return
	 */
	public static String queryAtividadesAndamento(String projeto, String sprint) {

		StringBuilder builder = new StringBuilder();

		builder.append("project = " + projeto + "");
		builder.append(" AND Sprint = " + sprint + "");
		builder.append(" AND status = \"In Progress\"");
		builder.append(" AND resolution = Unresolved");
		builder.append(" ORDER BY assignee ASC, updated DESC");
		builder.append("");

		return builder.toString();

	}
	
	public static String queryHorasPorUsuario() {
		
		StringBuilder builder = new StringBuilder();

		builder.append("worklogAuthor = 'Gilson Braggion' or worklogAuthor ='Alexi de Lara'");
		builder.append(" AND worklogdate >= 2018-02-10");
		
		builder.append(" ORDER BY assignee ASC");

		builder.append("&");
		builder.append("fields=worklog");
		builder.append("&");
		builder.append("maxResults=9999");
		
		return builder.toString();
		
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

}
