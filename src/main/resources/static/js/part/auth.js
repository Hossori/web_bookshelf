'use strict';

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

function applySignupValidation(errors) {
    let trs = {
        email : $('#'+getProperty('auth.email.id')),
        password : $('#'+getProperty('auth.password.id')),
        rePassword : $('#'+getProperty('auth.rePassword.id')),
        name : $('#'+getProperty('auth.name.id'))
    };
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