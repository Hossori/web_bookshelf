'use strict';

/*
    arg def is object has below properties
    {
        dialog : dialog by jquery selector,
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

        this.initializeButtons(def.buttons);
        this.initializeFocusControl();

    }

    /*
        this.show, this.hideはアロー関数の中で呼びだす形にすることで、
        処理内のthisの参照をDialogに確定させる。
    */
    initializeButtons(buttons) {
        for(let button of buttons) {
            let clickFunc;
            if(button.click === 'show') {
                clickFunc = () => { this.show(); };
            } else if(button.click === 'hide') {
                clickFunc = () => { this.hide(); };
            } else {
                clickFunc = button.click;
            }
            button.element.on('click', clickFunc);
        }
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