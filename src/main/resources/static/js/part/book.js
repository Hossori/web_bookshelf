'use strict';

function showBookListInBookshelf(page) {
    resetContainers();
    let bookContainer = $('#'+getProperty('book.list.container.id'));
    _containers.push(bookContainer);
    let bookshelfId = bookContainer.attr('data-id');
    $.ajax({
        url : '/rest/book/getBookPagesInBookshelf',
        type : 'get',
        data : {page : page,
        		bookshelfId : bookshelfId},
        dataType : 'json'
    })
    .done(function(res) {
		// reflesh book container
		let pages = res.data.bookPages;
		let books = pages.content;
		new BookList(books, bookContainer);
		// reflesh pagination container
        let paginationContainer = $('#'+getProperty('pagination.container.id'));
        _containers.push(paginationContainer);
        let paginationMethodName = 'showBookListInBookshelf';
        new Pagination(pages, paginationMethodName , paginationContainer);
    });
}

class BookList {

    constructor(bookList, container) {
		bookList.forEach(book => {
			let html = this.createHtml(book);
			container.append(html);
		});
    }

    createHtml(book) {
		
		let name = document.createElement('p');
		name.append(document.createTextNode('書籍名　'+book.name));
		let state = document.createElement('p');
		let stateText;
		let stateArray = ['読後', '読中', '未読', '積読'];
		if(book.state) {
			stateText = stateArray[book.state];
		} else {
			stateText = '-';
		}
		state.append(document.createTextNode('　状態　'+stateText));
		let evaluation = document.createElement('p');
		let evaluationText = book.evaluation === null ? '-' : book.evaluation;
		evaluation.append(document.createTextNode('　評価　'+evaluationText));
		let createdAt = document.createElement('p');
		let createdAtText = formatDateTime(new Date(book.createdAt), 'datetime');
		createdAt.append(document.createTextNode('登録日　'+createdAtText));

		let bookInfoDiv = document.createElement('div');
		bookInfoDiv.append(name);
		bookInfoDiv.append(createdAt);
		bookInfoDiv.append(state);
		bookInfoDiv.append(evaluation);
		bookInfoDiv.setAttribute('class', 'bookListInfo');
		
		let div = document.createElement('div');
		div.append(bookInfoDiv);
		div.setAttribute('id', 'book'+book.id);
		div.setAttribute('class', 'bookList');
		
		let anchor = document.createElement('a');
		anchor.setAttribute('href', '/book/show?id='+book.id);
		anchor.append(div);
		
		return anchor;
    }

}

function showBookDetail() {
	let bookNameTd = $('#'+getProperty('book.detail.name.id')+' td');
	let bookStateTd = $('#'+getProperty('book.detail.state.id')+' td');
	let bookEvaluationTd = $('#'+getProperty('book.detail.evaluation.id')+' td');
	let bookMemoTd = $('#'+getProperty('book.detail.memo.id')+' td');
	
	let bookName = bookNameTd.attr('data');
	bookNameTd.append(document.createTextNode(bookName));
	
	let stateArray = ['読後', '読中', '未読', '積読'];
	let bookState = bookStateTd.attr('data') ? stateArray[bookStateTd.attr('data')] : '-';
	bookStateTd.append(document.createTextNode(bookState));
	
	let bookEvaluation = '';
	if(bookEvaluationTd.attr('data')) {
		let starCount = 5;
		for(let i = 0; i < bookEvaluationTd.attr('data'); i++) {
			bookEvaluation += '★';
			starCount--;
		}
		for(; 0 < starCount; starCount--) {
			bookEvaluation += '☆';
		}
	} else {
		bookEvaluation = '-';
	}
	bookEvaluationTd.append(document.createTextNode(bookEvaluation));
	
	let bookMemo = bookMemoTd.attr('data') ? bookMemoTd.attr('data') : '-';
	let bookMemoPre = document.createElement('pre');
	bookMemoPre.append(document.createTextNode(bookMemo));
	bookMemoTd.append(bookMemoPre);
}