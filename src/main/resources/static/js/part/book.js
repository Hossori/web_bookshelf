'use strict';

var _stateArray = JSON.parse(getProperty('book.state.array'));

function showBookListInBookshelf(page) {
    resetContainers(page);
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
        new BookList(books, bookshelfId, page, bookListContainer);
        // reflesh pagination container
        let paginationContainer = $('#'+getProperty('pagination.container.id'));
        _containers.push(paginationContainer);
        let paginationMethodName = 'showBookListInBookshelf';
        new Pagination(pages, paginationMethodName , paginationContainer);
    });
}

class BookList {

    constructor(bookList, bookshelfId, bookshelfPage, container) {
        this.bookshelfId = bookshelfId;
        this.bookshelfPage = bookshelfPage;
        this.createStarsOfEvaluation = createStarsOfEvaluation;
        bookList.forEach(book => {
            let html = this.createHtml(book);
            container.append(html);
        });
    }

    createHtml(book) {
        // create object for tr
        let trContents = {};
        // initialize object for tr
        // ["name", "state", "evaluation", "createdAt"]
        let trKeys = JSON.parse(getProperty('book.list.key.array'));
        for(let key of trKeys) {
            trContents[key] = {
                tooltip : null,
                data : document.createElement('td')
            };
        }
        // create header array, data array, id array
        let tooltipContentArray = JSON.parse(getProperty('book.list.caption.array'));
        let dataContentArray = [
            book.name,
            book.state !== undefined ? _stateArray[book.state] : getProperty('book.list.state.unset'),
            this.createStarsOfEvaluation(book.evaluation),
            formatDateTime(new Date(book.createdAt), 'datetime'),
        ];
        let idArray = JSON.parse(getProperty('book.list.id.array'));
        // set header, data to object for tr
        for(let i = 0; i < trKeys.length; i++) {
            let key = trKeys[i];
            let tooltipContent = tooltipContentArray[i];
            let dataContent = dataContentArray[i];

            if(key === 'memo') {
                let pre = document.createElement('pre');
                pre.append(document.createTextNode(dataContent));
                dataContent = pre;
            } else {
                dataContent = document.createTextNode(dataContent);
            }

            let trContent = trContents[key];
            trContent.tooltip = tooltipContent;
            trContent.data.append(dataContent);
        }
        // set tr id and insert tr to table
        let bookInfoTable = document.createElement('table');
        for(let i = 0; i < trKeys.length; i++) {
            let tr = document.createElement('tr');
            let trContent = trContents[trKeys[i]];
            tr.append(trContent.data);
            tr.setAttribute('title', trContent.tooltip);
            tr.setAttribute('id', idArray[i]);
            bookInfoTable.append(tr);
        }
        bookInfoTable.setAttribute('class', 'bookListInfo');

        let div = document.createElement('div');
        div.append(bookInfoTable);
        div.setAttribute('id', 'book'+book.id);
        div.setAttribute('class', 'bookList');

        let anchor = document.createElement('a');
        anchor.setAttribute('href', '/book/show?id='+book.id+
                                    '&bookshelfId='+this.bookshelfId+
                                    '&bookshelfPage='+this.bookshelfPage);
        anchor.append(div);

        return anchor;
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

    let backButton = $('#'+getProperty('book.detail.button.back.id'));
    let backUrl = '/bookshelf/show?id='+backButton.attr('data-backId')
                                +'&page='+backButton.attr('data-backPage');
    backButton.attr('onclick', 'location.href='+'\''+backUrl+'\'');
}

class BookDetail {
    constructor(book, container) {
        this.book = book;
        this.createStarsOfEvaluation = createStarsOfEvaluation;

        let table = this.createHtml();
        container.append(table);
    }

    createHtml() {
        // create object for tr
        let trContents = {};
        // initialize object for tr
        // ["name", "state", "evaluation", "createdAt", "updatedAt", "memo"]
        let trKeys = JSON.parse(getProperty('book.detail.key.array'));
        for(let key of trKeys) {
            trContents[key] = {
                header : document.createElement('th'),
                data : document.createElement('td')
            };
        }
        // create header array, data array, id array
        let headerContentArray = JSON.parse(getProperty('book.detail.caption.array'));
        let dataContentArray = [
            this.book.name,
            this.book.state !== undefined ? _stateArray[this.book.state] : '-',
            this.createStarsOfEvaluation(this.book.evaluation),
            formatDateTime(new Date(this.book.createdAt), 'datetime'),
            formatDateTime(new Date(this.book.updatedAt),'datetime'),
            this.book.memo
        ];
        let idArray = JSON.parse(getProperty('book.detail.id.array'));
        // set header, data to object for tr
        for(let i = 0; i < trKeys.length; i++) {
            let key = trKeys[i];
            let headerContent = headerContentArray[i];
            let dataContent = dataContentArray[i];

            if(key === 'memo') {
                headerContent = document.createTextNode(headerContent);
                let pre = document.createElement('pre');
                pre.append(document.createTextNode(dataContent));
                dataContent = pre;
            } else {
                headerContent = document.createTextNode(headerContent);
                dataContent = document.createTextNode(dataContent);
            }

            let trContent = trContents[key];
            trContent.header.append(headerContent);
            trContent.data.append(dataContent);
        }
        // set tr id and insert tr to table
        let table = document.createElement('table');
        for(let i = 0; i < trKeys.length; i++) {
            let tr = document.createElement('tr');
            let trContent = trContents[trKeys[i]];
            tr.append(trContent.header);
            tr.append(trContent.data);
            tr.setAttribute('id', idArray[i]);
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