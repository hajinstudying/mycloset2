<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="now" value="<%= new java.util.Date()%>"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY CLOSET</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>?v=${now}" />
</head>
<body>
<%-- header --%>
<jsp:include page="/common/header.jsp" />
<%-- main --%>
<main>
        <div class="slider">
            <div class="slides">
                <div class="pic">
                    <a href="./productlist.html">
                        <img src="./img/main1.jpg" alt="main1">
                        <div class="text">여름맞이<br>BIG SALE</div>
                    </a>
                </div>
                <div class="pic">
                    <a href="./productlist.html">
                        <img src="./img/main2.jpg" alt="main2">
                        <div class="text">반팔티 모음<br>SALE</div>
                    </a>
                </div>
                <div class="pic">
                    <a href="./productlist.html">
                        <img src="./img/main3.jpg" alt="main3">
                        <div class="text">여름을 맞이한<br>MYCLOSET</div>
                    </a>
                </div>
                <div class="pic">
                    <a href="./productlist.html">
                        <img src="./img/main4.jpg" alt="main4">
                        <div class="text">SummerWear<br>Jacket</div>
                    </a>
                </div>
                <div class="pic">
                    <a href="./productlist.html">
                        <img src="./img/main5.jpg" alt="main5">
                        <div class="text">SummerWear<br>OPS</div>
                    </a>
                </div>
            </div>
            <button class="prev" onclick="prevSlide()">&#10094;</button>
            <button class="next" onclick="nextSlide()">&#10095;</button>
        </div>
    </main>
    <div class="container">
        <div class="news">
            <h2><a href="notice.html" id="news-link">NEWS</a></h2>
            <ul>
                <li><a href="./content1.html">[안내] 개인정보보호법 개정 (휴면계정 정책 폐지) 에 따른 휴면 정책 변경 안내</a></li>
                <li><a href="./content2.html">[안내] 적립금 이용약관 개정 안내</a></li>
                <li><a href="./content3.html">[안내] 서비스 안정화를 위한 PLUS 배송 일시 중단 안내</a></li>
                <li><a href="./content4.html">[안내] 후기 사이즈 추천 서비스 종료 안내</a></li>
                <li><a href="./content5.html">[안내] MYCLOSET 소비자 가격 변경 안내</a></li>
            </ul>
        </div>
        <div class="customer-center">
            <h2>고객센터</h2>
            <div class="phone-number">1566-5027</div>
            <div class="hours">운영시간 : 평일 09:00~18:00 (점심시간 : 12:30~13:30)</div>
            <div class="email">cs_help@mycloset.co.kr</div>
            <div class="buttons">
                <button>FAQ</button>
                <button>1:1 문의</button>
            </div>
            <div class="social-icons">
                <a href="#">Facebook</a>
                <a href="#">Instagram</a>
                <a href="#">Pinterest</a>
                <a href="#">YouTube</a>
            </div>
        </div>
    </div>

    <%-- footer --%>
	<jsp:include page="/common/footer.jsp" />
    <script>
        let currentSlide = 1;
        let isAnimating = false;
    
        document.addEventListener('DOMContentLoaded', () => {
            const slides = document.querySelector('.slides');
            const slideElements = Array.from(slides.children);
            const totalSlides = slideElements.length;
    
            // 첫 번째와 마지막 슬라이드를 복제하여 무한 루프 효과를 만듭니다.
            const firstSlide = slideElements[0].cloneNode(true);
            const lastSlide = slideElements[totalSlides - 1].cloneNode(true);
    
            slides.appendChild(firstSlide);
            slides.insertBefore(lastSlide, slideElements[0]);
    
            // 슬라이드를 이동시키는 함수
            function showSlide(index) {
                if (isAnimating) return;
                isAnimating = true;
    
                const totalSlidesWithClones = slides.children.length;
                slides.style.transition = 'transform 0.5s ease-in-out';
                slides.style.transform = `translateX(-${index * 100}%)`;
    
                currentSlide = index;
    
                // 애니메이션 끝난 후에 무한 루프 효과를 위한 슬라이드 위치 조정
                slides.addEventListener('transitionend', () => {
                    isAnimating = false;
                    if (currentSlide === 0) {
                        slides.style.transition = 'none';
                        currentSlide = totalSlides - 2;
                        slides.style.transform = `translateX(-${currentSlide * 100}%)`;
                    } else if (currentSlide === totalSlides - 1) {
                        slides.style.transition = 'none';
                        currentSlide = 1;
                        slides.style.transform = `translateX(-${currentSlide * 100}%)`;
                    }
                }, { once: true });
            }
    
            function nextSlide() {
                showSlide(currentSlide + 1);
            }
    
            function prevSlide() {
                showSlide(currentSlide - 1);
            }
    
            // 첫 번째 슬라이드로 자연스럽게 이동
            setTimeout(() => {
                showSlide(1);
            }, 0);
    
            document.querySelector('.next').addEventListener('click', nextSlide);
            document.querySelector('.prev').addEventListener('click', prevSlide);
        });
    </script>
    </body>
</html>