'use strict';

/*
    spring bootのデフォルト設定によりpost通信はcsrf情報必要。
    updateはformにth:actionとpost="method"を追加することで自動処理にし、
    deleteはmetaタグに用意したcsrf情報をリクエストヘッダに追加する方法を採った。
*/
function updateBookshelf() {
    let editForm = $('#'+getProperty('bookshelf.edit.form.id'));
    let formData = editForm.serializeArray();

    $.ajax({
        type : 'put',
        url : '/rest/bookshelf/update',
        data : formData,
        dataType : 'json'
    }).done(function(result) {
        if(result.code === 0) {
            location.reload();
        }
    });
}
function deleteBookshelf() {
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
        if(result.code === 0) {
            location.href = '/bookshelf/index?page=0';
        }
    });
}