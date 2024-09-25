var EventsSearchView = (function() {
	var dao;
	
	// Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
	var self;
	
	var formId = 'events-form';
	var listId = 'events-list';
	var paginationId = 'events-pagination';
	var formQuery = '#' + formId;
	var listQuery = '#' + listId;
	var paginationQuery = '#' + paginationId;

	var pageNumber = 1;
	const pageSize = 10;
	var previousId = 'previousId';
	var nextId = 'nextId';
	var previousQuery = '#' + previousId;
	var nextQuery = '#' + nextId;
	var iteracion = 1;
	var sizeList = 0;
	var numberOfPages;
	
	function EventsSearchView(eventsDao, formContainerId, listContainerId, paginationContainerId) {
		dao = eventsDao;
		self = this;
		
		insertSearchEventsForm($('#' + formContainerId));
		insertEventSearchList($('#' + listContainerId));
		insertSearchPagination($('#' + paginationContainerId));
		$(previousQuery).hide();
		$(nextQuery).hide();

		

		
		this.init = function() {
			
			$("#eventsSearch-container").prepend('<h1 class="display-8 fw-normal">Buscar eventos</h1></br>');
			 
			/*La acción por defecto de enviar formulario (submit) se sobreescribe
				para que el envío sea a través de AJAX*/
		
			$(formQuery).submit(function() {

                document.getElementById("btnSubmit").disabled = true;

				var eventSearch = self.getEventInForm();
				
				if($('#searchBackUp').length){
					$('#searchBackUp').remove();
					pageNumber = 1;
					$(previousQuery).hide();
					$(nextQuery).hide();
				}

                console.log("SizeList antes de llamar a dao:"+sizeList);

				dao.sizeListSearch(eventSearch, function(eventSearch){
		
					sizeList=eventSearch;
                    console.log("SizeList centro dao:"+sizeList);	

					numberOfPages= Math.ceil((sizeList/10));
				});

                console.log("SizeList después dao:"+sizeList);	



				

				$(formQuery).append('<div class="col-sm-4" id="searchBackUp">'+ eventSearch + '</div>');
				$('#searchBackUp').hide();
				

				dao.listSearch(eventSearch, pageNumber, pageSize, 
					function(eventSearch){
					$(listQuery).html('');

					if(eventSearch.length === 0){
						$(listQuery).append("<h1>No se han encontrado resultados para esta búsqueda");
					}else{


						$.each(eventSearch, function(index, individualEvent){
						
							appendToCards(individualEvent);
						});

						
			
					}

                    console.log("SizeList antes del if que activa el Next:"+sizeList);	

					if(sizeList > 10){
						
						$(nextQuery).show();
                        console.log("Entramos en activar Next");
					}
				
					
					self.resetForm();
				},
				self.enableForm
				
				);

				
				

				
				return false;
			});

			
			
			$('#btnClear').click(this.resetForm);
			

			$(nextQuery).click(function(){
				
				iteracion = iteracion + 1;

				pageNumber = pageNumber +1;
				var eventSearch = $('#searchBackUp').text();
				
				dao.listSearch(eventSearch, pageNumber, pageSize, 
					function(eventSearch){
					$(listQuery).html('');

						$.each(eventSearch, function(index, individualEvent){
						
							appendToCards(individualEvent);
						});

						$(previousQuery).show();
				
				});

				if(pageNumber === numberOfPages){
					$(nextQuery).hide();
				}
			});

			$(previousQuery).click(function(){

				if(pageNumber > 1){

					pageNumber = pageNumber -1;

				}
				if(pageNumber === 1){

					$(previousQuery).hide()
				}
				

				var eventSearch = $('#searchBackUp').text();


				dao.listSearch(eventSearch, pageNumber, pageSize, 
					function(eventSearch){
					$(listQuery).html('');

					
						$.each(eventSearch, function(index, individualEvent){
						
							appendToCards(individualEvent);
						});

						$(nextQuery).show();
				
				});

	
			});
		};

		


		this.getEventInForm = function() {
			var form = $(formQuery);
			return	form.find('input[name="titleContent"]').val();
			
		};

		this.disableForm = function() {
			$(formQuery + ' input').prop('disabled', true);
		};

		this.enableForm = function() {
			$(formQuery + ' input').prop('disabled', false);
		};

		this.resetForm = function() {
			$(formQuery)[0].reset();
			$(formQuery + ' input[name="titleContent"]').val('');
		};
	};
	
	var insertEventSearchList = function(parent) {
		parent.append(
			'<div class="row mb-2" id="' + listId + '">\
			</div>'
		);
	};

	var insertSearchEventsForm = function(parent) {
		parent.append(
			'<form id="' + formId + '" class="row mb-5 mb-10">\
					<div class="col-sm-4">\
						<input name="titleContent" id="searchInput" type="text" value="" placeholder="Título o descripción" class="form-control" required onkeyup="verify()"/>\
					</div>\
					<div class="col-sm-3">\
						<input id="btnSubmit" type="submit" value="Buscar" class="btn btn-dark" disabled/>\
						<input id="btnClear" type="reset" value="Limpiar" class="btn" />\
					</div>\
			</form>'
		);
	};

	var insertSearchPagination = function(parent) {
			
			
			parent.append(
				'<div class="col-md-4" id="' + paginationId +'">\
					<nav aria-label="Page navigation example">\
						<ul class="pagination">\
							<li class="page-item" id="'+previousId+'"><a class="page-link">Previous</a></li>\
							<li class="page-item" id="'+nextId+'"><a class="page-link">Next</a></li>\
						</ul>\
					</nav>\
				</div>'
			);
		
	};

	var createEventCard = function(individualEvent) {

        if (individualEvent.img === null) {
            var img = 'images/' + individualEvent.category + '.jpg';
        } else {
            var img = individualEvent.img;
        }
      
        var myDate = new Date(individualEvent.date);

                return '<div class="col-md-4">\
                <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative" style="word-break: break-all;">\
                    <div class="col-auto d-none d-lg-block">\
                        <img src="' + img + '" width="180" height="180">\
                        <div>\
                            <br/>\
                            <div style= "font-size: 12px;" class="mb-1 text-muted"><img src="icons/map.svg" width="15" height="15">' + ' ' + individualEvent.place.substring(0, 20) + ' ...</div>\
                            <div style= "font-size: 12px;" class="mb-1 text-muted"><img src="icons/calendar.svg" width="15" height="15">' + ' ' + myDate.toLocaleString() + '</div>\
                            <div style= "font-size: 12px;" class="mb-1 text-muted"><img src="icons/capacity.svg" width="15" height="15">' + ' ' + individualEvent.capacity + '</div>\
                        </div>\
                    </div>\
                    <div style="height:305px" class="col p-6 d-flex flex-column position-static">\
                        <strong class="d-inline-block mb-2 text-primary">' + individualEvent.category.toUpperCase() + '</strong>\
                        <h3 class="mb-0">' + individualEvent.title.substring(0, 20) + '</h3>\
                        <br/>\
                        <p style= "font-size: 18px;" class="card-text mb-auto">' + individualEvent.description.substring(0, 49) + ' ...</p>\
                        <a style= "font-size: 12px;" href="#" class="stretched-link text-right">Ver más</a>\
                    </div>\
                </div>\
            </div>';
	};

	var showErrorMessage = function(jqxhr, textStatus, error) {
		alert(textStatus + ": " + error);
	};
	

	var appendToCards = function(individualEvent) {
		
		$(listQuery)
			.append(createEventCard(individualEvent));
			
	};
	
	return EventsSearchView;
})();


var EventsListView = (function() {
    var dao;

    // Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
    var self;

    var listId = 'events-list';
    var listQuery = '#' + listId;

    function EventsListView(eventsDao, listContainerId) {
        dao = eventsDao;
        self = this;
        page = 0;
        pageSize = 10;
        insertEventListList($('#' + listContainerId));
        listGetAll($('#' + listContainerId));

        this.init = function() {
            $("#eventsList-container").prepend('<h1 class="display-8 fw-normal">Eventos próximos</h1></br>');
            dao.listList(
                function(eventList) {
                    $(listQuery).html('');
                    console.log(eventList);
                    if (eventList.length === 0) {
                        $(listQuery).append("<h1>No se han encontrado eventos recientes");
                    } else {
                        $.each(eventList, function(index, individualEvent) {
                            console.log(individualEvent);
                            appendToCards(individualEvent);

                        });
                    }
                }
            );
            $('#btnPrevious').hide();
            $('#btnNext').click(this.pageAdd);
            $('#btnPrevious').click(this.pageSubs);

        };
        this.pageAdd = function() {
            page = page + 1;
            if (page > 1) {
                $('#btnPrevious').show()
            }
            dao.sizeGetAll(function(number) {
                numberOfPages = Math.ceil((number / pageSize));
                if (page === numberOfPages) {
                    $('#btnNext').hide();
                }
            });
            dao.listGetAll(page, pageSize, function(eventList) {
                $(listQuery).html('');
                $.each(eventList, function(index, individualEvent) {
                    console.log(individualEvent);
                    appendToCards(individualEvent);

                });
            });


        };

        this.pageSubs = function() {
            page = page - 1;
            dao.sizeGetAll(function(number) {
                numberOfPages = Math.ceil((number / pageSize));
                if (page < numberOfPages) {
                    $('#btnNext').show();
                }
            });
            if (page === 1) {
                $('#btnPrevious').hide()
            }

            dao.listGetAll(page, pageSize, function(eventList) {
                $(listQuery).html('');
                $.each(eventList, function(index, individualEvent) {
                    appendToCards(individualEvent);
                });
            });
        };
    };

    var insertEventListList = function(parent) {
        parent.append(
            '<div class="row mb-2" id="' + listId + '">\
						</div>'
        );
    };

    var listGetAll = function(parent) {
        parent.append(
            '<div class="col-md-12">\
					<div class="stretched-link text-right">\
					<div style="height:305px" class="col p-6 d-flex flex-column position-static">\
					<div>\
					<button id="btnPrevious" class="btn btn-outline-dark" float-right>Ver anteriores</button>\
                    <button id="btnNext" class="btn btn-outline-dark" float-right>Ver mas</button>\
					</div></div></div></div>\
					'
        );
    };


    var createEventCard = function(individualEvent) {

        if (individualEvent.img === null) {
            var img = 'images/' + individualEvent.category + '.jpg';
        } else {
            var img = individualEvent.img;
        }

        var myDate = new Date(individualEvent.date);

        return '<div class="col-md-4">\
							<div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative" style="word-break: break-all;">\
								<div class="col-auto d-none d-lg-block">\
									<img src="' + img + '" width="180" height="180">\
									<div>\
										<br/>\
										<div style= "font-size: 12px;" class="mb-1 text-muted"><img src="icons/map.svg" width="15" height="15">' + ' ' + individualEvent.place.substring(0, 20) + ' ...</div>\
										<div style= "font-size: 12px;" class="mb-1 text-muted"><img src="icons/calendar.svg" width="15" height="15">' + ' ' + myDate.toLocaleString() + '</div>\
										<div style= "font-size: 12px;" class="mb-1 text-muted"><img src="icons/capacity.svg" width="15" height="15">' + ' ' + individualEvent.capacity + '</div>\
									</div>\
								</div>\
								<div style="height:305px" class="col p-6 d-flex flex-column position-static">\
									<strong class="d-inline-block mb-2 text-primary">' + individualEvent.category.toUpperCase() + '</strong>\
									<h3 class="mb-0">' + individualEvent.title.substring(0, 20) + '</h3>\
									<br/>\
									<p style= "font-size: 18px;" class="card-text mb-auto">' + individualEvent.description.substring(0, 49) + ' ...</p>\
									<a style= "font-size: 12px;" href="#" class="stretched-link text-right">Ver más</a>\
								</div>\
							</div>\
						</div>';
    };

    var showErrorMessage = function(jqxhr, textStatus, error) {
        alert(textStatus + ": " + error);
    };


    var appendToCards = function(individualEvent) {

        $(listQuery)
            .append(createEventCard(individualEvent));

    };




    return EventsListView;
})();