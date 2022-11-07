'use strict';

function createBookshelf() {
    let formData = $('#'+getProperty('bookshelf.create.form.id')).serializeArray();
    $.ajax({
        type : 'put',
        url : '/rest/bookshelf/create',
        data : formData,
        dataType : 'json'
    })
    .done(function(result) {
        if(result.code === STATUS.OK) {
            location.reload();
        } else if(result.code === STATUS.BAD_REQUEST) {
            applyBookshelfValidationMessages(result.data, 'create');
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
        } else if (result.code === STATUS.BAD_REQUEST) {
            applyBookshelfValidationMessages(result.data, 'edit');
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

function applyBookshelfValidationMessages(errors, targetType) {
    let target = {};
    let targetNames = ['name'];
    for (let targetName of targetNames) {
        let id = '#'+getProperty('bookshelf.'+targetType+'.'+targetName+'.id');
        target[targetName] = {
            messageWrapper : $(id),
            header : $(id+' p.caption'),
            form : $(id+' input')
        };
        target[targetName].header.css('color', 'white');
        target[targetName].form.css('border-bottom', 'solid 1px white');
    }

    $('p.error').remove();

    for(let key in errors) {
        if(errors[key]) {
            let errorMsg = $('<p class="error">').append(errors[key]);
            target[key].messageWrapper.append(errorMsg);
            errorMsg.css('color', 'yellow');
            errorMsg.css('font-size', '16px');
            errorMsg.css('margin-top', '6px');
            target[key].header.css('color', 'yellow');
            target[key].form.css('border-bottom', 'solid 1px yellow');
        }
    }
}