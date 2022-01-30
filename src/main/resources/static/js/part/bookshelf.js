'use strict';

class Bookshelf {

    constructor(bookshelf, container) {
        let div = this.createHtml(bookshelf);
        container.append(div);
    }
<<<<<<< HEAD
    
=======

>>>>>>> c8558f2ac81a11889874c56e9c267ccef67bb33d
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