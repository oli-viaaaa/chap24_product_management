function productCheck() {
	if (isNaN(document.frm.price.value)) {
		alert("숫자를 입력해야 합니다");
		frm.price.focus();
		return false;
	}
	return true;
}
function workerLoginCheck() {
	var id = document.login_form.id.value;
	var pwd = document.login_form.pwd.value;

	if(id == ""){
		alert("아이디를 입력하세요.");
		login_form.id.focus();
		return false;
	}
	if(pwd == ""){
		alert("비밀번호를 입력하세요.");
		login_form.pwd.focus();
		return false;
	}
	
	login_form.submit();
}