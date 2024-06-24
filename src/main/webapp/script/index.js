/**
 * index 슬라이드 자바스트립트
 * - 왼쪽, 오른쪽 버튼 누르면 넘어감
 */
 
let currentIndex = 0;
const slides = document.querySelectorAll('.slide');
const numSlides = slides.length;

function showSlide(index) {
    slides.forEach((slide, i) => {
        slide.style.display = i === index ? 'block' : 'none';
    });
}

function prevSlide() {
    currentIndex = (currentIndex - 1 + numSlides) % numSlides;
    showSlide(currentIndex);
}

function nextSlide() {
    currentIndex = (currentIndex + 1) % numSlides;
    showSlide(currentIndex);
}

showSlide(currentIndex);

