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
            let liChild;
            if(i === pages.number+1) {
				liChild = document.createTextNode(i);
			} else {
				liChild = document.createElement('a');
				liChild.setAttribute('onclick', this.methodName+'('+(i-1)+')');
				liChild.append(document.createTextNode(i));
			}
			li.append(liChild);
            ol.append(li);
        }
        
        ol.setAttribute('class', 'pagination');

        return ol;
    }
}