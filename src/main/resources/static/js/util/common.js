'use strict';

var STATUS = {
    OK : getStatusCode('OK'),
    BAD_REQUEST : getStatusCode('BAD_REQUEST'),
    FORBIDDEN : getStatusCode('FORBIDDEN')
};

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

function getStatusCode(status) {
    let res = $.ajax({
        url : '/rest/property/get/statusCode',
        type : 'get',
        data : {key : status},
        dataType : 'json',
        async : false
    }).responseText;
    res = JSON.parse(res);
    return Number(res);
}

// Date datetime, String formatType
function formatDateTime(datetime, formatType) {
    let year = datetime.getFullYear();
    let month = datetime.getMonth() + 1;
    let day = datetime.getDate();
    let hour = datetime.getHours();
    let minute = datetime.getMinutes();

    let text;
    /*if(formatType === 'date') {
        text = year+'/'+month+'/'+day;
    } else if(formatType === 'time') {
        text = hour+':'+minute+':'+second;
    } else */if(formatType === 'datetime') {
        text = year+'/'+month+'/'+day+' '+hour+':'+minute;
    }

    return text;
}


function isLoginUserId(userId) {
    let result = $.ajax({
        url : '/rest/authChecker/getLoginUserId',
        type : 'get',
        dataType : 'json',
        async : false
    }).responseText;
    let loginUserId = JSON.parse(result).data.loginUserId;
    return userId === loginUserId;
}

function showMain() {
    $('div#main').show();
}