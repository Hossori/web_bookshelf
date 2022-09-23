'use strict';

/*
    spring bootのデフォルト設定によりpost通信はcsrf情報必要。
    updateはformにth:actionとpost="method"を追加することで自動処理にし、
    deleteはmetaタグに用意したcsrf情報をリクエストヘッダに追加する方法を採った。
*/
async function updateBookshelf() {
    let confirmMessage = getProperty('bookshelf.edit.confirm.update.message');
    if(!await displayConfirm(confirmMessage, false)) {
        return;
    }

    let editForm = $('#'+getProperty('bookshelf.edit.form.id'));
    let formData = editForm.serializeArray();

    $.ajax({
        type : 'put',
        url : '/rest/bookshelf/update',
        data : formData,
        dataType : 'json'
    }).done(function(result) {
        if(result.code === STATUS.OK) {
            location.reload();
        }
    });
}
async function deleteBookshelf() {
    let confirmMessage = getProperty('bookshelf.edit.confirm.delete.message');
    if(!await displayConfirm(confirmMessage, true)) {
        return;
    }

    let csrfToken = $('#'+getProperty('meta.csrf.token.id')).attr('content');
    let csrfHeader = $('#'+getProperty('meta.csrf.header.id')).attr('content');
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
    });

    let bookshelfId = $('#'+getProperty('bookshelf.edit.id.id')).attr('value');
    $.ajax({
        type : 'put',
        url : '/rest/bookshelf/delete',
        data : {bookshelfId : bookshelfId},
        dataType : 'json'
    }).done(function(result) {
        if(result.code === STATUS.OK) {
            location.href = '/bookshelf/index?page=0&user=-1';
        }
    });
}