'use strict';

class Bookshelf {

    constructor(bookshelf, container) {
        let html = this.createHtml(bookshelf);
        html.setAttribute('class', 'bookshelf');
        container.append(html);
    }

    createHtml(bookshelf) {
        let div = document.createElement('div');

        let userName = document.createElement('p');
        userName.append(document.createTextNode(bookshelf.user.name))
        let name = document.createElement('p');
        name.append(document.createTextNode(bookshelf.name));
        let createdAt = document.createElement('p');
        createdAt.append(document.createTextNode(bookshelf.createdAt));

        div.append(userName);
        div.append(name);
        div.append(createdAt);
        div.setAttribute('id', 'bookshelf'+bookshelf.id);

        return div;
    }

}