'use strict';

function resetContainers() {
	_containers.forEach(container => {
		container.empty();
	});
	_containers = [];
}

function getProperty(key) {
    let res = $.ajax({
        url : '/rest/property/get',
        type : 'get',
        data : {key : key},
        dataType : 'json',
        async : false
    }).responseText;

    res = JSON.parse(res);
    return res.data.value;
}