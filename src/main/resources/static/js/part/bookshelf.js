'use strict';

function showBookshelfList(page) {
	resetContainers();
    $.ajax({
        url : '/rest/bookshelf/getBookshelfPages',
        type : 'get',
        data : {page : page},
        dataType : 'json'
    })
    .done(
        function(res) {
            let pages = res.data.bookshelfPages;
            let bookshelfs = pages.content;

            // reflesh bookshelf container
            let bookshelfContainer = $('#'+getProperty('bookshelf.container.id'));
            _containers.push(bookshelfContainer);
            let bookshelfMethodName = 'showBookshelfDetail';
            new BookshelfList(bookshelfs, bookshelfMethodName, bookshelfContainer);
            // reflesh pagination container
            let paginationContainer = $('#'+getProperty('pagination.container.id'));
            _containers.push(paginationContainer);
            let paginationMethodName = 'showBookshelfList';
            new Pagination(pages, paginationMethodName , paginationContainer);
        }
    );
}

function showBookshelfDetail(id) {
	resetContainers();
	$.ajax({
		url : '/rest/bookshelf/getBookshelfDetail',
		type : 'get',
		data : {id : id},
		dataType : 'json'
	})
	.done(
		function(res) {
			let bookshelf = res.data.bookshelf;
			let bookshelfContainer = $('#'+getProperty('bookshelf.container.id'));
			_containers.push(bookshelfContainer);
			new BookshelfDetail(bookshelf, bookshelfContainer);
		}
	);
}

class BookshelfList {

    constructor(bookshelfList, methodName, container) {
		this.methodName = methodName;
    	bookshelfList.forEach(bookshelf =>  {
			let bookshelfDiv = this.createHtml(bookshelf, methodName)
        	container.append(bookshelfDiv);
        });
    }

    createHtml(bookshelf) {
        let userName = document.createElement('p');
        userName.append(document.createTextNode(bookshelf.user.name))
        let name = document.createElement('p');
        name.append(document.createTextNode(bookshelf.name));
        let createdAt = document.createElement('p');
        createdAt.append(document.createTextNode(bookshelf.createdAt));
        
		let bookshelfInfoDiv = document.createElement('div');
        bookshelfInfoDiv.append(userName);
        bookshelfInfoDiv.append(name);
        bookshelfInfoDiv.append(createdAt);
        bookshelfInfoDiv.setAttribute('class', 'bookshelfListInfo');

		let div = document.createElement('div');
		div.append(bookshelfInfoDiv);
        div.setAttribute('id', 'bookshelf'+bookshelf.id);
        div.setAttribute('onclick', this.methodName+'('+bookshelf.id+')');
        div.setAttribute('class', 'bookshelfList');

        return div;
    }

}

class BookshelfDetail {

    constructor(bookshelf, container) {
		let bookshelfDiv = this.createHtml(bookshelf);
		bookshelfDiv.setAttribute('class', 'bookshelfDetail');
    	container.append(bookshelfDiv);
    }

    createHtml(bookshelf) {
		
		return div;
    }

}