'use strict';

class Bookshelf {

    constructor(bookshelf, container) {
        let html = this.createHtml(bookshelf);
        container.append(html);
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
        bookshelfInfoDiv.setAttribute('class', 'bookshelfInfo');

		let div = document.createElement('div');
		div.append(bookshelfInfoDiv);
        div.setAttribute('id', 'bookshelf'+bookshelf.id);
        div.setAttribute('class', 'bookshelf');

        return div;
    }

}