if (!atrs) var atrs = {};

//ベースURL
//baseUrlを事前定義するが必要
if (!atrs.baseUrl) atrs.baseUrl = "/atrs";

// 二重送信防止用フォームが送信済みフラグ（送信済み：true、未送信：false）
atrs.isSubmitted = false;

/**
 * 二重送信チェックし指定フォームをサブミット。
 * 
 * @param form フォーム名（必須）
 * @param path formのpath設定
 * @param method formのmethod設定
 */
atrs.checkSubmitAction = function(form, path , method) {
    if(this.isSubmitted){
      alert("送信済です。\n「OK」をクリックしてしばらくお待ちください。");
    } else {
      this.isSubmitted = true;
      this.submitAction(form, path, method);
    }
};

/**
 * 指定フォームをサブミット。
 * 
 * @param form フォーム名（必須）
 * @param path formのpath設定
 * @param method formのmethod設定
 */
atrs.submitAction = function(form, path , method){
    if (path != undefined) {
        document.forms[form].action = this.baseUrl + path;
    }
    if (method != undefined) {
        document.forms[form].method = method;
    }
    document.forms[form].submit();
};


/**
 * 指定URLに遷移。
 * 
 * @param path 遷移先URL
 */
atrs.moveTo = function(path) {
    window.location.href= this.baseUrl + path;
};


/**
 * フォームのアクションとHTTPメソッドを設定する。
 * 
 * @param form フォーム名（必須）
 * @param path formのpath設定
 * @param method formのmethod設定
 */
atrs.setFormActionAndHttpMethod = function(form, path , method){
    if (path != undefined) {
        document.forms[form].action = this.baseUrl + path;
    }
    if (method != undefined) {
        document.forms[form].method = method;
    }
};
