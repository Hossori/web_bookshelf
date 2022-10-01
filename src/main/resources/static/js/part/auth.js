'use strict';

function login() {
    let formData = $('#'+getProperty('auth.login.form.id')).serializeArray();
    $.ajax({
        type : 'post',
        url : '/login',
        data : formData,
        dataType : 'json'
    }).done((result) => {
        if(result.code === STATUS.OK) {
            location.href = '/bookshelf/index?page=0&userId=-1';
        } else if(result.code === STATUS.BAD_REQUEST) {
            let errors = result.data;
            applyLoginValidation(errors);
        } else if(result.code === STATUS.FORBIDDEN) {
            console.log(result.data);
        }
    }).fail((XMLHttpRequest, textStatus, errorThrown) => {
        console.log(XMLHttpRequest);
        console.log(textStatus);
        console.log(errorThrown);
    });
}

function signup() {
    let formData = $('#'+getProperty('auth.signup.form.id')).serializeArray();
    $.ajax({
        type : 'put',
        url : '/rest/auth/signup',
        data : formData,
        dataType : 'json'
    })
    .done(function(result) {
        if(result.code === STATUS.OK) {
            location.href = '/login'
        } else if(result.code === STATUS.BAD_REQUEST) {
            let errors = result.data;
            applySignupValidation(errors);
        } else if(result.code === STATUS.FORBIDDEN) {

        }
    })
    .fail(function() {

    });
}

function applyLoginValidation(errors) {
    let trs = {
        email : $('#'+getProperty('auth.email.id')),
        password : $('#'+getProperty('auth.password.id')),
    };
    applyAuthValidation(trs, errors);
}

function applySignupValidation(errors) {
    let trs = {
        email : $('#'+getProperty('auth.email.id')),
        password : $('#'+getProperty('auth.password.id')),
        rePassword : $('#'+getProperty('auth.rePassword.id')),
        name : $('#'+getProperty('auth.name.id'))
    };
    applyAuthValidation(trs, errors);
}

function applyAuthValidation(trs, errors) {
    $('.errorMsg').remove();
    for(let trId in trs) {
        let label = trs[trId].find('label');
        let input = trs[trId].find('input');
        label.css({color : 'white'});
        input.css({borderBottom : 'solid 1px white'});
    }
    for(let errorName in errors) {
        let label = trs[errorName].find('label');
        let input = trs[errorName].find('input');
        let errorMsg = $('<p class="errorMsg">'+errors[errorName]+'</p>');
        errorMsg.css({
            color : 'yellow',
            padding : '6px 0'
        });
        label.css({color : 'yellow'});
        input.css({borderBottom : 'solid 1px yellow'});
        input.after(errorMsg);
    }
}