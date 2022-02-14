'use strict';

var _stateArray = JSON.parse(getProperty('book.state.array'));

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
		this.createStarsOfEvaluation = createStarsOfEvaluation;
		bookList.forEach(book => {
			let html = this.createHtml(book);
			container.append(html);
		});
    }
    
    createTrs() {
	
		let trs = {
			name : document.createElement('tr'),
			state : document.createElement('tr'),
			evaluation : document.createElement('tr'),
			createdAt : document.createElement('tr')
		};
		/*
		let nameTh = document.createElement('th');
		nameTh.append(document.createTextNode(getProperty('book.name.caption')));
		trs.name.append(nameTh);
		
		let stateTh = document.createElement('th');
		stateTh.append(document.createTextNode(getProperty('book.state.caption')));
		trs.state.append(stateTh);
		
		let evaluationTh = document.createElement('th');
		evaluationTh.append(document.createTextNode(getProperty('book.evaluation.caption')));
		trs.evaluation.append(evaluationTh);
		
		let createdAtTh = document.createElement('th');
		createdAtTh.append(document.createTextNode(getProperty('book.createdAt.caption')));
		trs.createdAt.append(createdAtTh);
		*/
		return trs;
	}

    createHtml(book) {
		
		let trs = this.createTrs();
		
		let nameTd = document.createElement('td');
		nameTd.append(document.createTextNode(book.name));
		trs.name.append(nameTd);
		
		let stateTd = document.createElement('td');
		stateTd.append(document.createTextNode(book.state ? _stateArray[book.state] : getProperty('book.list.state.unset')));
		trs.state.append(stateTd);
		
		let evaluationTd = document.createElement('td');
		let evaluationText = createStarsOfEvaluation(book.evaluation)
		evaluationText = evaluationText === '-' ? getProperty('book.list.evaluation.unset') : evaluationText;
		evaluationTd.append(document.createTextNode(evaluationText));
		trs.evaluation.append(evaluationTd);
		
		let createdAtTd = document.createElement('td');
		createdAtTd.append(document.createTextNode(formatDateTime(new Date(book.createdAt), 'datetime')));
		trs.createdAt.append(createdAtTd);

		let bookInfoTable = document.createElement('table');
		for(let tr of Object.values(trs)) {
			bookInfoTable.append(tr);
		}
		bookInfoTable.setAttribute('class', 'bookListInfo');
		
		let div = document.createElement('div');
		div.append(bookInfoTable);
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
	let bookCreatedAtTd = $('#'+getProperty('book.detail.createdAt.id')+' td');
	let bookUpdatedAtTd = $('#'+getProperty('book.detail.updatedAt.id')+' td');
	let bookMemoTd = $('#'+getProperty('book.detail.memo.id')+' td');
	
	let bookName = bookNameTd.attr('data');
	bookNameTd.append(document.createTextNode(bookName));
	
	let bookState = bookStateTd.attr('data') ? _stateArray[bookStateTd.attr('data')] : '-';
	bookStateTd.append(document.createTextNode(bookState));
	
	let bookEvaluation = createStarsOfEvaluation(bookEvaluationTd.attr('data'));
	bookEvaluationTd.append(document.createTextNode(bookEvaluation));
	
	let bookCreatedAt = formatDateTime(new Date(bookCreatedAtTd.attr('data')), 'datetime');
	bookCreatedAtTd.append(document.createTextNode(bookCreatedAt));
	
	let bookUpdatedAt = formatDateTime(new Date(bookUpdatedAtTd.attr('data')), 'datetime');
	bookUpdatedAtTd.append(document.createTextNode(bookUpdatedAt));
	
	let bookMemo = bookMemoTd.attr('data') ? bookMemoTd.attr('data') : '-';
	let bookMemoPre = document.createElement('pre');
	bookMemoPre.append(document.createTextNode(bookMemo));
	bookMemoTd.append(bookMemoPre);
}

function createStarsOfEvaluation(evaluationInt) {
	let evaluationStars = '';
	
	if(evaluationInt) {
		let starCount = 5;
		for(let i = 0; i < evaluationInt; i++) {
			evaluationStars += '★';
			starCount--;
		}
		for(; 0 < starCount; starCount--) {
			evaluationStars += '☆';
		}
	} else {
		evaluationStars = '-';
	}
	
	return evaluationStars;
}