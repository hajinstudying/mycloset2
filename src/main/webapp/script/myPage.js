/**
 * myPage의 자바스크립트 파일
 * - 취소 버튼에 뒤로가기 부착
 * - 회원가입폼의 정규식 검증
 */
    // 이벤트 부착
    const submitBtn = document.getElementById('submitBtn');
    const form = document.getElementById('updateForm');
    submitBtn.addEventListener('click', (event) => {
        //정규식 검증
        const password = document.querySelector('#password').value;
        const password2 = document.querySelector('#password2').value;
        const email = document.querySelector('#email').value;

        //정규식
        const pwdRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{6,}$/;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        let valid = true;

        //비밀번호 검증
        if (!pwdRegex.test(password)) {
            document.querySelector('#pwdError').style.display = 'block';
            valid = false
        } else {
            document.querySelector('#pwdError').style.display = 'none';
        }

        //비밀번호 불일치 검증
        if (password !== password2) {
            document.querySelector('#pwdError2').style.display = 'block';
            valid = false;
        } else {
            document.querySelector('#pwdError2').style.display = 'none';
        }

        //이메일 검증
        if (!emailRegex.test(email)) {
            document.querySelector('#emailError').style.display = 'block';
            valid = false;
        } else {
            document.querySelector('#emailError').style.display = 'none';
        }

        if (valid) {
            form.submit();
        }
    });