/**
 * productDetail.jsp 파일의 자바스크립트입니다.
 */
 
 <!-- 자바스크립트 -->
const colorBox = document.querySelector('#colorBox');
const sizeBox = document.querySelector('#sizeBox');
const quantity = document.querySelector('#quantity');

//색상을 선택하면 사이즈 콤보박스가 초기화되도록
colorBox.addEventListener('change', (event) => {
    sizeBox.value = '';
});

//색상 선택 후 사이즈 선택하도록 알림
sizeBox.addEventListener('change', (event) => {
    const selectedColor = colorBox.value;
    if (selectedColor === '') {
        alert('색상을 먼저 선택해주세요.');
        sizeBox.value = '';
    } else {
        //오더 박스에 표시되도록
        
        const orderDetail = document.querySelector('#orderDetail');
        document.querySelector('.orderBox').style.display = 'block';
        orderDetail.innerText = colorBox.value + ' / ' + sizeBox.value;
    }
});

//orderBox에 가격표시
quantity.addEventListener('change', (event) => {
    const totalPrice = document.querySelector('#totalPrice');
    const calculatedPrice = quantity.value * 45000;
    totalPrice.innerText = calculatedPrice.toLocaleString() + '원';
});