package br.com.rhinosistemas.util;

public class QueryUtil {

	/**
	 * Query para filtrar as horas lançadas por um projeto / Sprint
	 * 
	 * @return
	 */
	public static String queryHorasLancadas(String projeto, String sprint) {
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

}
