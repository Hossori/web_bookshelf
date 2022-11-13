'use strict';

async function updateUser() {
    let confirmMessage = getProperty('user.edit.confirm.update.message');
    if(!await displayConfirm(confirmMessage, false)) {
        return;
    }

    let editForm = $('#'+getProperty('user.edit.form.id'));
    let formData = editForm.serializeArray();

    $.ajax({
        type : 'put',
        url : '/rest/user/update',
        data : formData,
        dataType : 'json'
    }).done(function(result) {
        if(result.code === STATUS.OK) {
            location.reload();
        } else if(result.code === STATUS.BAD_REQUEST) {
            applyFormValidationResultOfUser(result.data, 'edit');
        } else if(result.code === STATUS.FORBIDDEN) {
            console.log(result.data);
        }
    });
}

function applyFormValidationResultOfUser(errors, targetType) {
    let targets = {};
    let targetNames = ['email', 'name', 'password', 'rePassword'];
    for (let targetName of targetNames) {
        let id = '#'+getProperty('user.'+targetType+'.'+targetName+'.id');
        targets[targetName] = {
            messageWrapper : $(id+' td'),
            header : $(id+' th label'),
            form : $(id+' td input')
        };
    }
    applyFormValidationResult(targets, errors);
}

function controlGenderRadiosView(genderRadios) {
    genderRadios.each((idx, radio) => {
        if($(radio).attr('checked')) {
            changeRadioView($(radio), true);
        }
    });
    $(genderRadios).change(function(e) {
        let checkedRadio = e.currentTarget;
        genderRadios.each((idx, radio) => {
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