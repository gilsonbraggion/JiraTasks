$(document).ready(function() {

	$('#horasLogadas').DataTable({
		scrollY : "350px",
		scrollX : true,
		scrollCollapse : true,
		paging : false,
		columnDefs : [ {
			width : 270,
			targets : 0
		} ],
		fixedColumns : {
			leftColumns : 1
		},
		language: {
            processing:     "Processando...",
            search:         "",
            zeroRecords:    "Sabe nem digitar.",
            emptyTable:     "Tá vazio, pesquisa direito.",
            info:           "Mostrando _START_ até _END_ de _TOTAL_",
            infoEmpty:      "Nada para mostrar",
            infoFiltered:   "(Filtrando de _MAX_ elementos no total)",
            paginate: {
                first:      "Primeiro",
                previous:   "Anterior",
                next:       "Próximo",
                last:       "Útilmo"
            }
        }
	});
	
	var inputSearch = $('.dataTables_filter input[type=search]');
	inputSearch.addClass('form-control input-sm');
	inputSearch.prop('placeholder', 'Filtrar...');
	inputSearch.parent().addClass("hidden-print");
	inputSearch.parent().append('<i class="search-icon fa fa-search"></i>');

	$(".campoData").mask("99/99/9999", {
		placeholder : "mm/dd/yyyy"
	});

	$("#selectBoard").change(function() {
		
		$("#conteudoTabela").empty();

		$.ajax({
			url : "http://localhost:8090/sprint/buscarSprintAtiva?numeroBoard="+this.value
		}).then(function(data) {
			
			$("#pesquisar").removeAttr("disabled")

			var name = data.split("|")[0]; 
			var id = data.split("|")[1];
			
			$("#optionSelected").attr("value", id);
			$("#optionSelected").attr("label", name);
			
		});

	});

});