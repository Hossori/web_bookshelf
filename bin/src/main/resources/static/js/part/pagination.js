'use strict';

class Pagination {

    constructor(pages, container) {
        let div = this.createHtml(pages);
        container.append(div);
    }

    createHtml(pages) {
        let ol = document.createElement('ol');

        let pageCount = pages.totalPages === 0 ? 1 : pages.totalPages;
        for(let i = 1; i <= pageCount; i++) {
            let li = document.createElement('li');
            li.append(document.createTextNode(i));
            ol.append(li);
        }

        return ol;
    }
}