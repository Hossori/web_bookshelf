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
            let bookshelfContainer = $('#'+getProperty('bookshelf.list.container.id'));
            _containers.push(bookshelfContainer);
            new BookshelfList(bookshelfs, bookshelfContainer);
            // reflesh pagination container
            let paginationContainer = $('#'+getProperty('pagination.container.id'));
            _containers.push(paginationContainer);
            let paginationMethodName = 'showBookshelfList';
            new Pagination(pages, paginationMethodName , paginationContainer);
        }
    );
}

class BookshelfList {

    constructor(bookshelfList, container) {
        bookshelfList.forEach(bookshelf =>  {
            let bookshelfDiv = this.createHtml(bookshelf);
            container.append(bookshelfDiv);
        });
    }

    createHtml(bookshelf) {
        let userName = document.createElement('p');
        userName.append(document.createTextNode(bookshelf.user.name))
        let name = document.createElement('p');
        name.append(document.createTextNode(bookshelf.name));
        let createdAt = document.createElement('p');
        let createdAtText = formatDateTime(new Date(bookshelf.createdAt), 'datetime');
        createdAt.append(document.createTextNode(createdAtText));

        let bookshelfInfoDiv = document.createElement('div');
        bookshelfInfoDiv.append(userName);
        bookshelfInfoDiv.append(name);
        bookshelfInfoDiv.append(createdAt);
        bookshelfInfoDiv.setAttribute('class', 'bookshelfListInfo');

        let div = document.createElement('div');
        div.append(bookshelfInfoDiv);
        div.setAttribute('id', 'bookshelf'+bookshelf.id);
        div.setAttribute('class', 'bookshelfList');

        let anchor = document.createElement('a');
        anchor.append(div);
        anchor.setAttribute('href', '/bookshelf/show?id='+bookshelf.id);

        return anchor;
    }

}