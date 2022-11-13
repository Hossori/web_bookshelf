'use strict';

var STATUS = {
    OK : getStatusCode('OK'),
    BAD_REQUEST : getStatusCode('BAD_REQUEST'),
    FORBIDDEN : getStatusCode('FORBIDDEN'),
    CONFLICT : getStatusCode('CONFLICT')
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

/**
    apply result of form validation to form html and css.
    @param targets
    'targets' is object like following.
    {
        value of name attribute of element : {
            messageWrapper : element added 'p' displaying error message by jquery selector,
            label : label element applied css by jquery selector,
            form : form element applied css by jquery selector
        }
    }
    @param errors
    'errors' is return value of ajax when occuring validation error.
*/
function applyFormValidationResult(targets, errors) {
    // reset validation result
    $('p.error').remove();
    for (let key in targets) {
        targets[key].header.css('color', 'white');
        targets[key].form.css('border-bottom', 'solid 1px white');
    }

    // apply validation result
    for (let key in errors) {
        if (targets[key]) {
            let errorMsg = $('<p class="error">').append(errors[key]);
            targets[key].messageWrapper.append(errorMsg);
            errorMsg.css('color', 'yellow');
            errorMsg.css('font-size', '16px');
            errorMsg.css('margin-top', '6px');
            targets[key].header.css('color', 'yellow');
            targets[key].form.css('border-bottom', 'solid 1px yellow');
        }
    }
}