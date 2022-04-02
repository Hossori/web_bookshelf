'use strict';

var STATUS = {
    OK : Number(getProperty('statusCode.ok')),
    BAD_REQUEST : Number(getProperty('statusCode.badRequest')),
    FORBIDDEN : Number(getProperty('statusCode.forbidden'))
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

function initializeConfirmDialog(message) {
    let bookCreateDialogShowButton = {
        element : $('#'+getProperty('bookshelf.detail.button.bookCreate.id')),
        click : 'show'
    }
    let bookCreateDialogRegisterButton = {
        element : $('#'+getProperty('book.create.dialog.buttons.register.id')),
        click : createBook
    };
    let bookCreateDialogCancelButton = {
        element : $('#'+getProperty('book.create.dialog.buttons.cancel.id')),
        click : 'hide'
    };
    let bookCreateDialogButtons = [
        bookCreateDialogShowButton,
        bookCreateDialogRegisterButton,
        bookCreateDialogCancelButton
    ]
    let confirmDialogDef = {
        dialog : $('#'+getProperty('confirm.dialog.id')),
        titleText : getProperty('confirm.dialog.title'),
        firstElement : $('#'+getProperty('book.create.name.id')+' td input'),
        buttons : bookCreateDialogButtons
    }
    new Dialog(confirmDialogDef);
}