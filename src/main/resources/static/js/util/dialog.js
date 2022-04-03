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
        return await this.confirm();
    }

    async confirm() {
        return new Promise((resolve, reject) => {
            super.show();
            for(let button of buttons) {
                let result;
                if(buttonClick === 'ok') {
                    result = true;
                } else if(buttonClick === 'cancel') {
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