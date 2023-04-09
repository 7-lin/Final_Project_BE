# Final_Project_BE
회사 소개
더샤이니는  여행자를 유형별(연령대, 성별, 관심사 등등)로 그룹화하여 그룹 특성에 맞는 패키지 여행상품을 추천하는 서비스 플랫폼 고투게더의 MVP버전을 아임웹 템플릿으로 제작하여 운영하고 있습니다.


![image](https://user-images.githubusercontent.com/113900701/230722162-f8f1cd13-6e5d-4c67-9f21-39c7ae2af1af.png)

# 고투게더
https://www.gotogether-s.com/
 
**타깃층 및 서비스**

패키지 여행을 선호하는 시니어층을 대상으로 해외 패키지 여행 상품 추천 서비스를 시작하여, 향후 국내여행, 등산, 낚시, 골프 등의 취미활동을 나와 비슷한 유형의 시니어 사용자들끼리 함께 할 수 있도록 그룹을 만들어주는 서비스를 제공할 계획입니다.
 
**프로젝트의 목적**

1. 사용자가 입력한 키워드 정보를 기반으로 유사 정보를 입력한 사용자들끼리 그룹화 서비스 구축
2. 키워드 정보 기반 여행상품 등이 추천되는 서비스 구축이 목적이며,
3. 사용자관리, 상품관리, 예약관리 페이지 등의 제작이 목적입니다.

**프로젝트와 유사한 서비스**

- 트립스토어 https://www.tripstore.kr/ (반응형 웹) : 여러 여행사의 패키지 여행상품을 서비스 내에서 판매
- 여다 https://yodatrip.com/ (앱) : 국내 자유여행자 대상 사용자 입력정보 기반 여행일정 생성 및 추천 서비스


**프로젝트 상세 요구사항**

**0. 온보딩 애니메이션**
- 고투게더 기업 소개하는 일러스트 3개 슬라이더로 보여주는 기능

**1. 설문조사** 
- 챗봇 형태 등으로 설문조사 하는 기능
- 성별, 연령, 취미, 종교, 여행지, 호텔등급 등으로 분류

**2. 공통**
- 상단 Nav : 검색기능 , 로고 ,  알림 기능
- Footer : 각 페이지로 이동할 수 있는 네비게이션 기능 

**3. 마이페이지**
- 나와 비슷한 유형이 관심있는 여행상품 등 추천
- 로그인, 회원가입, 로그아웃, 예약내역 등 기능

**4. 메인 페이지**
- 메인배너 슬라이더
- 최근 본 여행상품 기능 구현
- 최근 상품 후기 기능 구현

**5. 알림 페이지**
- 예약이 완료되면 알림 내역에 뜨는 기능. 

**6. 위시리스트 페이지**
- 여행 상품 게시판 페이지에서 하트 버튼 클릭 시 위시리스트에 해당 항목 추가 됨
- 위시리스트에 담겨있는 상품들의 목록 > 상품 클릭 시 해당 상품 상세페이지로 이동
-위시리스트 > 하트버튼 클릭 시 해당 상품 위시리스트 목록에서 삭제 

**7. 여행상품 게시판 페이지**
- 여행상품 추천, 검색 페이지에서 상품 검색 시 결과물을 보여주는 페이지
- 여행상품들의 목록에서 상품 클릭 시 상세페이지로 이동, 
- 해당상품의 하트 버튼 클릭 시 위시리스트에 반영
- 예약하기 페이지로 이동 가능

**8. 여행 상품 검색 페이지**

- 여행 상품을 검색하는 페이지
- 키워드 검색이나 미리 정해놓은 태그로 검색 가능

**9. 여행 상품 상세 페이지**
- 여행 상품의 상세 내역을 보여주는 페이지
- 예약하기 버튼 클릭 시 예약하기 페이지로 이동

**10. 사용자 관리**
-사용자 리스트 열람 : 이름, 계정, 이메일, 연락처, 생년월일, 성별, 가입일
- 운영진 설정 : 관리자 페이지 사용 가능 설정 필요

**11. 상품 관리**
- 카테고리 관리 : 상품 카테고리 생성 및 편집 및 홈페이지 연동 기능 구현
- 상품 상세페이지 입력 기능 구현 : 썸네일 이미지, 상품명, 가격, 상품 요약설명, 여행정보, 상세일정, 포함안내, 예약방법, 여행후기

**12. 예약 관리**
- 예약자 이름, 예약일, 예약상품, 상품 가격, 예약인원, 관리자 메모, 결제내역

**13. 컨텐츠 관리**
- 여행후기 및 게시판 관리 : 기존 사용 중인 홈페이지의 여행후기 및 게시판의 글과 이미지를 옮길 수 있도록 입력폼 기능 구현

**14. 서버 구축 및 연동**
- aws 서버 구축

# 기술스택 
  <div align="center">
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
<img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white">
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<br>
  </div>
  
# 협업툴
<div align="center">
<img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<br>
</div>

# BE-조원
역할|이름|깃헙주소
---|---|---|
조장|이강훈|https://github.com/hoonii2
조원1|최병옥|https://github.com/ChoiByungok
조원2|차승준|https://github.com/Chaseungjun
