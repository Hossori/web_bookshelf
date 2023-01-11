'use strict';

function login() {
    let postLoginFunc = function() {
        let formData = $('#'+getProperty('auth.login.form.id')).serializeArray();
        return $.ajax({
            type : 'post',
            url : '/login',
            data : formData,
            dataType : 'json'
        });
    };
    let saveClientZoneIdToServerSessionFunc = function() {
        let zoneId = Intl.DateTimeFormat().resolvedOptions().timeZone;
        return $.ajax({
            type : 'put',
            url : '/rest/session/put/zoneId',
            data : {zoneId : zoneId},
            dataType : 'json'
        });
    };

    saveClientZoneIdToServerSessionFunc()
    .then((result) => {
        return postLoginFunc();
    })
    .then((result) => {
        if(result.code === STATUS.OK) {
            location.href = '/bookshelf/index?page=0';
        } else if(result.code === STATUS.BAD_REQUEST) {
            let errors = result.data;
            applyFormValidationResultOfAuth(errors, 'login');
        } else if(result.code === STATUS.FORBIDDEN) {
            console.log(result.data);
        }
    }).catch((jqXHR, textStatus, errorThrown) => {
        console.log(jqXHR);
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
            applyFormValidationResultOfAuth(errors, 'signup');
        } else if(result.code === STATUS.CONFLICT) {
            let errors = result.data;
            applyFormValidationResultOfAuth(errors, 'signup');
        } else if(result.code === STATUS.FORBIDDEN) {

        }
    })
    .fail(function() {

    });
}

function applyFormValidationResultOfAuth(errors, targetType) {
    let targets = {};
    let targetNames;
    if (targetType === 'login') {
        targetNames = ['email', 'password'];
    } else if (targetType === 'signup') {
        targetNames = ['email', 'password', 'rePassword', 'name'];
    }

    for (let targetName of targetNames) {
        let id = '#'+getProperty('auth.'+targetName+'.id');
        targets[targetName] = {
            messageWrapper : $(id+' td'),
            header : $(id+' th label'),
            form : $(id+' td input')
        };
    }
    applyFormValidationResult(targets, errors);
}