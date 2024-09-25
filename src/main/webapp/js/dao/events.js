var EventsDAO = (function() {
    var resourcePath = "rest/events/";
    var requestByAjax = function(data, done, fail, always) {
        done = typeof done !== 'undefined' ? done : function() {};
        fail = typeof fail !== 'undefined' ? fail : function() {};
        always = typeof always !== 'undefined' ? always : function() {};

        $.ajax(data).done(done).fail(fail).always(always);
    };

    function EventsDAO() {
        this.listSearch = function(titleContent, pageNumber, pageSize, done, fail, always) {
            requestByAjax({
                url: resourcePath + "?titleContent=" + titleContent + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize,
                type: 'GET'
            }, done, fail, always);

        };

        this.listList = function(done, fail, always) {
            requestByAjax({
                url: resourcePath + "recent",
                type: 'GET'
            }, done, fail, always);
        }
        this.listGetAll = function(pageNumber, pageSize, done, fail, always) {
            requestByAjax({
                url: resourcePath + "getAll" + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
                type: 'GET'
            }, done, fail, always);
        }

        this.getEvent = function(id, done, fail, always) {
            requestByAjax({
                url: resourcePath + id,
                type: 'GET',
            }, done, fail, always);
        };

		this.sizeListSearch = function(titleContent,done, fail, always) {
		    requestByAjax({
			url : resourcePath + "size/?titleContent=" + titleContent,
			type : 'GET'
		    }, done, fail, always);
			
		};

        this.sizeGetAll = function(done, fail, always) {
            requestByAjax({
                url: resourcePath + 'getAllNumber',
                type: 'GET',
            }, done, fail, always);
        };
    }

    return EventsDAO;
})();