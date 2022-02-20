'use strict';

var _stateArray = JSON.parse(getProperty('book.state.array'));

function showBookListInBookshelf(page) {
    resetContainers();
    let bookListContainer = $('#'+getProperty('book.list.container.id'));
    _containers.push(bookListContainer);
    let bookshelfId = parseInt(bookListContainer.attr('data-id'));
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
        new BookList(books, bookListContainer);
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

}

function showBookDetail() {
    resetContainers();
    let bookDetailContainer = $('#'+getProperty('book.detail.container.id'));
    _containers.push(bookDetailContainer);

    let bookId = parseInt(bookDetailContainer.attr('data'));
    $.ajax({
        url : '/rest/book/getBookDetail',
        type : 'get',
        data : {bookId : bookId},
        dataType : 'json'
    })
    .done(function(res) {
        let book = res.data.book;
        // reflesh book container
        new BookDetail(book, bookDetailContainer);
    });
}

class BookDetail {
    constructor(book, container) {
        this.book = book;
        this.createStarsOfEvaluation = createStarsOfEvaluation;

        let table = this.createHtml();
        container.append(table);
    }

    createHtml() {
        // ["bookName", "bookState", "bookEvaluation", "bookCreatedAt", "bookUpdatedAt", "bookMemo"]
        let headerArray = JSON.parse(getProperty('book.detail.caption.array'));
        let dataArray = [
            this.book.name,
            this.book.state ? _stateArray[this.book.state] : '-',
            this.createStarsOfEvaluation(this.book.evaluation),
            formatDateTime(new Date(this.book.createdAt), 'datetime'),
            formatDateTime(new Date(this.book.updatedAt),'datetime'),
            this.book.memo
        ];

        let table = document.createElement('table');
        for(let r = 0; r < headerArray.length; r++) {
            let th = document.createElement('th');
            th.append(document.createTextNode(headerArray[r]));
            let td = document.createElement('td');
            td.append(document.createTextNode(dataArray[r]));
            let tr = document.createElement('tr');
            tr.append(th);
            tr.append(td);

            table.append(tr);
        }

        return table;
    }
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