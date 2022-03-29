'use strict';

var _bookEvaluationValue;

function createBook() {
    let formData = $('#'+getProperty('book.create.form.id')).serializeArray();
    $.ajax({
        type : 'put',
        url : '/rest/book/create',
        data : formData,
        dataType : 'json'
    })
    .done(function(result) {
        if(result.code === STATUS.OK) {
            location.reload();
        } else if(result.code === STATUS.BAD_REQUEST) {
            appendValidationMessages(result.data);
        } else if(result.code === STATUS.FORBIDDEN) {
            console.log(result.data);
        }
    })
    .fail(function() {

    });
}

/*
spring bootのデフォルト設定によりpost通信はcsrf情報必要。
updateはformにth:actionとpost="method"を追加することで自動処理にし、
deleteはmetaタグに用意したcsrf情報をリクエストヘッダに追加する方法を採った。
*/
function updateBook() {
    let editForm = $('#'+getProperty('book.edit.form.id'));
    let formData = editForm.serializeArray();

    $.ajax({
        type : 'put',
        url : '/rest/book/update',
        data : formData,
        dataType : 'json'
    }).done(function(result) {
        if(result.code === STATUS.OK) {
            location.reload();
        } else if(result.code === STATUS.BAD_REQUEST) {
            appendValidationMessages(result.data);
        } else if(result.code === STATUS.FORBIDDEN) {
            console.log(result.data);
        }
    });
}
function deleteBook() {
    let csrfToken = $('#'+getProperty('meta.csrf.token.id')).attr('content');
    let csrfHeader = $('#'+getProperty('meta.csrf.header.id')).attr('content');
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
    });

    let bookId = $('#'+getProperty('book.edit.id.id')).attr('value');
    $.ajax({
        type : 'put',
        url : '/rest/book/delete',
        data : {bookId : bookId},
        dataType : 'json'
    }).done(function(result) {
        if(result.code === STATUS.OK) {
            history.back();
        }
    });
}

function appendValidationMessages(errors) {
    let forms = {
        name : $('#'+getProperty('book.create.name.id')),
        /*
        state : $('#'+getProperty('book.create.state.id')+' td'),
        evaluation : $('#'+getProperty('book.create.evaluation.id')+' td'),
        memo : $('#'+getProperty('book.create.memo.id')+' td')
        */
    };

    let style = $('<style type="text/css">');
    style.append(
        '#createBookName.error th {color: yellow;}' +
        '#createBookName.error td p {color: yellow; margin-top: 6px;}' +
        '#createBookName.error td input {border-bottom: solid 1px yellow;}'
    );
    $('body').append(style);

    for(let key in errors) {
        if(errors[key]) {
            let errorMsg = $('<p class="error">').append(errors[key]);
            forms[key].addClass('error');
            forms[key].find('td').append(errorMsg);
        }
    }
}

function controlBookStateRadioView(bookStateRadios) {
    bookStateRadios.each((idx, radio) => {
        if($(radio).attr('checked')) {
            changeRadioView($(radio), true);
        }
    });
    $(bookStateRadios).change(function(e) {
        let checkedRadio = e.currentTarget;
        bookStateRadios.each((idx, radio) => {
            if(radio === checkedRadio) {
                changeRadioView($(radio), true);
            } else {
                changeRadioView($(radio), false);
            }
        });
    });
    function changeRadioView(radio, checked) {
        let view = checked ?
            {textColor:'sienna', bgColor:'white', fontWeight:'bold'} :
            {textColor:'white', bgColor:'sienna', fontWeight:'normal'};
        radio.parent().children('span').css('color', view.textColor);
        radio.parent().css('backgroundColor', view.bgColor);
        radio.parent().children('span').css('font-weight', view.fontWeight);
    }
}

function controlBookEvaluationRadioView(bookEvaluationRadios) {
    _bookEvaluationValue = bookEvaluationRadios.filter('[checked=checked]').val();
    changeRadioView(_bookEvaluationValue);
    bookEvaluationRadios.each((idx, radio) => {
        $(radio).parent().mouseover((e) => {
            changeRadioView($(e.currentTarget).children('input').val());
        });
        $(radio).parent().mouseout(() => {
            changeRadioView(_bookEvaluationValue);
        });
        $(radio).parent().click((e) => {
            _bookEvaluationValue = $(e.currentTarget).children('input').val();
            changeRadioView(_bookEvaluationValue);
        })
    });
    function changeRadioView(currentValue) {
        bookEvaluationRadios.each((idx, radio) => {
            if($(radio).val() <= currentValue) {
                $(radio).parent().children('span').text('★');
            } else {
                $(radio).parent().children('span').text('☆');
            }
        });
    }
}