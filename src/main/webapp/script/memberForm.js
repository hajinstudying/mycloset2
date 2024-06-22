/**
 * memberForm의 자바스크립트 파일
 * - 취소 버튼에 뒤로가기 부착
 * - 회원가입폼의 정규식 검증
 */
    //취소버튼에 뒤로가기 이벤트 부착
    const cancleBtn = document.querySelector('#cancleBtn');
    cancleBtn.addEventListener('click', () => window.history.back());

    //정규식 검증
    const form = document.querySelector('#regForm');
    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const memberId = document.querySelector('#memberId').value;
        const password = document.querySelector('#password').value;
        const password2 = document.querySelector('#password2').value;
        const name = document.querySelector('#name').value;
        const email = document.querySelector('#email').value;

        //정규식
        const idRegex = /^[a-z]+[a-z0-9]{5,19}$/g;
        const pwdRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{6,}$/;
        const nameRegex = /^[가-힣]{2,5}$/;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        let valid = true;
        //아이디 검증
        if (!idRegex.test(memberId)) {
            document.querySelector('#idError').style.display = 'block';
            valid = false;
        } else {
            document.querySelector('#idError').style.display = 'none';
        }

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

        //이름 검증
        if (!nameRegex.test(name)) {
            document.querySelector('#nameError').style.display = 'block';
            valid = false;
        } else {
            document.querySelector('#nameError').style.display = 'none';
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