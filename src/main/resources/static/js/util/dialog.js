'use strict';

/*
    arg 'def' is object has below properties
    {
        dialog : dialog by jquery selector,
        titleText : dialog title text,
        firstElement : first focus element in dialog div by jquery selector,
        buttons : array of object has button info {
            element : button by jquery selector,
            click : specify click event function by 'show' or 'hide' or function
        }
    }

*/
class Dialog {
    constructor(def) {
        this.isShowDialog = false;
        this.isFocusOnDialog = false;
        this.overlay = $('#overlay');

        this.dialog = def.dialog;
        this.firstElement = def.firstElement;

        this.createDialogTitle(def.titleText);
        this.initializeFocusControl();
    }

    createDialogTitle(titleText) {
        this.dialog.find('h3').remove();
        let dialogTitle = $('<h3 class="dialogTitle">');
        dialogTitle.text(titleText);
        this.dialog.prepend(dialogTitle);
    }

    /*
        アロー関数を用いることで関数内のthisをDialogに確定させる。
    */
    initializeFocusControl() {
        this.dialog.on('focusin', () => {
            this.isFocusOnDialog = true;
        });
        $(document.body).on('focusin', () => {
            if(this.isShowDialog && !this.isFocusOnDialog) {
                this.firstElement.focus();
            } else {
                this.isFocusOnDialog = false;
            }
        });
    }

    show() {
        this.overlay.show();
        this.dialog.show();
        this.firstElement.focus();
        this.isShowDialog = true;
    }

    hide() {
        this.overlay.hide();
        this.dialog.hide();
        this.isShowDialog = false;
    }
}

class CommonDialog extends Dialog {
    constructor(def) {
        super(def);
        this.initializeButtons(def.buttons);
    }

    /*
        button.click is 'show' or 'hide' or function literal
    */
    //this.show, this.hideはアロー関数の中で呼びだす形にすることで、
    //処理内のthisの参照をDialogに確定させる。
    initializeButtons(buttons) {
        for(let button of buttons) {
            let clickFunc = this.setClickFunc(button.click);
            button.element.on('click', clickFunc);
        }
    }
    setClickFunc(buttonClick) {
        switch(buttonClick) {
            case 'show':
                return () => { super.show(); };
            case 'hide':
                return () => { super.hide(); };
            default:
                return buttonClick;
        }
    }
}

class ConfirmDialog extends Dialog {
    constructor(def) {
        super(def);
        this.buttons = def.buttons;
        this.insertMessage(def.message, def.isDanger);
    }

    insertMessage(message, isDanger) {
        let pre = $('pre.confirmDialogMessage');
        let dialogTitle = $('h3.dialogTitle');
        let style = isDanger ? 'color: yellow;' : 'color: white;';
        pre.text(message);
        pre.attr('style', style);
        dialogTitle.attr('style', style);
    }

    confirm() {
        return new Promise((resolve, reject) => {
            super.show();
            for(let button of this.buttons) {
                let result;
                if(button.click === 'ok') {
                    result = true;
                } else if(button.click === 'cancel') {
                    result = false;
                }

                let clickFunc = () => {
                    super.hide();
                    resolve(result);
                };
                button.element.on('click', clickFunc);
            }
        });
    }
}

function displayConfirm(message, isDanger) {
    let confirmDialogOkButton = {
        element : $('#'+getProperty('confirm.dialog.buttons.ok.id')),
        click : 'ok'
    }
    let confirmDialogCancelButton = {
        element : $('#'+getProperty('confirm.dialog.buttons.cancel.id')),
        click : 'cancel'
    };
    let confirmDialogButtons = [
        confirmDialogOkButton,
        confirmDialogCancelButton
    ]
    let confirmDialogDef = {
        dialog : $('#'+getProperty('confirm.dialog.id')),
        titleText : getProperty('confirm.dialog.title'),
        message : message,
        isDanger : isDanger,
        firstElement : confirmDialogCancelButton.element,
        buttons : confirmDialogButtons
    }
    return new ConfirmDialog(confirmDialogDef).confirm();
}