'use strict';

function showBookList() {
    resetContainers();
    let bookContainer = $('#'+getProperty('book.list.container.id'));
    let bookshelfId = bookContainer.getAttribute('data-id');
    $.ajax({
        url : '/rest/book/getBookPages',
        type : 'get',
        data : {id : bookshelfId},
        dataType : 'json'
    })
    .done(function(res) {

    });
}

class BookList {

    constructor(book, container) {

    }

    createHtml() {

    }

}