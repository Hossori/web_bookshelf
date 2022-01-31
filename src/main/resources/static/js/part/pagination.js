'use strict';

class Pagination {

    constructor(pages, methodName, container) {
		this.methodName = methodName;
        let div = this.createHtml(pages);
        container.append(div);
    }

    createHtml(pages) {
        let ol = document.createElement('ol');

        let pageCount = pages.totalPages === 0 ? 1 : pages.totalPages;
        for(let i = 1; i <= pageCount; i++) {
            let li = document.createElement('li');
            li.append(document.createTextNode(i));
            if(i !== pages.number+1) {
				li.setAttribute('onclick', this.methodName+'('+(i-1)+')');
				li.setAttribute('class', 'anchor');
			}
			
            ol.append(li);
        }
        
        ol.setAttribute('class', 'pagination');

        return ol;
    }
}