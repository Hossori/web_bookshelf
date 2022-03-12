'use strict';

/*function createStarsOfEvaluation(evaluationInt) {
    let evaluationStars = '';

    if(evaluationInt) {
        let starCount = 5;
        for(let i = 0; i < evaluationInt; i++) {
            evaluationStars += '★';
            starCount--;
        }
        for(; 0 < starCount; starCount--) {
            evaluationStars += '☆';
        }
    } else {
        evaluationStars = '-';
    }

    return evaluationStars;
}*/

function createBook() {
    let formData = $('#'+getProperty('book.create.form.id')).serializeArray();
    $.ajax({
        type : 'put',
        url : '/rest/book/create',
        data : formData,
        dataType : 'json'
    })
    .done(function(result) {
        if(result.code === 0) {
            location.reload();
        } else {
            console.log('error '+result.code);
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
        if(result.code === 0) {
            location.reload();
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
        if(result.code === 0) {
            history.back();
        }
    });
}