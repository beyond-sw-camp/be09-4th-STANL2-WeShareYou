<template>
    <nav class="navigation">
        <div class="nav-left">
            <!-- 프로젝트 대표이미지 -->
            <RouterLink to="/" @click="resetDropdown">
                <img src="../../assets/icon/navigation/logo.png" alt="Project Logo" class="project-logo" />
            </RouterLink>

            <!-- 메뉴 리스트 -->
            <ul class="menu-list">
                <!-- 공유 물품 드롭다운 -->
                <li class="dropdown" @click="toggleDropdown('product')">
                    <span :class="{ active: activeMenu === 'product' }">공유 물품</span>
                    <ul v-show="activeDropdown === 'product'" class="dropdown-menu" @click.stop>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/NECESSITIES'"
                                @click="handleCategoryClick('product', 'NECESSITIES')">생활품</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/KITCHENWARES'"
                                @click="handleCategoryClick('product', 'KITCHENWARES')">주방용품</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/CLOTHES'" @click="handleCategoryClick('product', 'CLOTHES')">의류
                            </RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/TOY'" @click="handleCategoryClick('product', 'TOY')">놀이
                            </RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/DEVICE'" @click="handleCategoryClick('product', 'DEVICE')">전자기기
                            </RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/ETC'" @click="handleCategoryClick('product', 'ETC')">기타
                            </RouterLink>
                        </li>
                    </ul>
                </li>


                <!-- 게시글 드롭다운 -->
                <li class="dropdown" @click="toggleDropdown('board')">
                    <span :class="{ active: activeMenu === 'board' }">게시글</span>
                    <ul v-show="activeDropdown === 'board'" class="dropdown-menu" @click.stop>
                        <li class="dropdown-font">
                            <RouterLink to="/board/notice" @click="setActiveMenu('board')">가이드</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/board/event" @click="setActiveMenu('board')">프리마켓</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/board/faq" @click="setActiveMenu('board')">동행</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/board/tip" @click="setActiveMenu('board')">TIP</RouterLink>
                        </li>
                    </ul>
                </li>

                <li>
                    <RouterLink to="/notice" @click="resetDropdown">공지사항</RouterLink>
                </li>
                <li>
                    <RouterLink to="/faq" @click="resetDropdown">자주 묻는 질문</RouterLink>
                </li>
                <li>
                    <RouterLink to="/direction" @click="resetDropdown">오시는 길</RouterLink>
                </li>
            </ul>
        </div>

        

        <div class="nav-right">
            <ul class="language-setting">
                <li class="dropdown" @click="toggleDropdown('language')">
                    <span class="language-font" :class="{ active: activeMenu === 'language' }">Language</span>
                    <ul v-show="activeDropdown === 'language'" class="dropdown-language-menu" @click.stop>
                        <li class="dropdown-language" @click="changeLang('EN')">English</li>
                        <li class="dropdown-language" @click="changeLang('KO')">한국어</li>
                        <li class="dropdown-language" @click="changeLang('JA')">日本語</li>
                        <li class="dropdown-language1" @click="changeLang('ZH')">中文</li>
                    </ul>
                </li>
            </ul>
            
                

            <img src="../../assets/icon/navigation/alarm.png" class="icon-img" alt="alarm" />
            <img src="../../assets/icon/navigation/message.png" class="icon-img" alt="message" />

            <!-- 프로필 이미지 -->
            <div class="profile-container" @click="toggleDropdown('profile')" @click.stop>
                <img src="../../assets/icon/navigation/profile.png" alt="Profile" class="profile-image" />
                <!-- 프로필 드롭다운 메뉴 -->
                <ul v-show="activeDropdown === 'profile'" class="dropdown-menu profile-dropdown" @click.stop>
                    <li @click="resetDropdown" class="dropdown-font">
                        <RouterLink to="/mypage">마이페이지</RouterLink>
                    </li>
                    <li @click="resetDropdown" class="dropdown-font">
                        <RouterLink to="/profile">내 프로필</RouterLink>
                    </li>
                    <li v-if="isLoggedIn" @click="logOut" class="dropdown-font">
                        <RouterLink to="/login">로그아웃</RouterLink>
                    </li>
                    <li v-else @click="loGin" class="dropdown-font">
                        <RouterLink to="/">로그인</RouterLink>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, inject } from 'vue';
import { useRouter } from 'vue-router';

// inject로 전역 언어 상태와 변경 함수 받아오기
const currentLang = inject('currentLang');
const changeLanguage = inject('changeLanguage');

const activeDropdown = ref(null);
const activeMenu = ref(null);
const router = useRouter();
const isLoggedIn = ref(false);

// 로그인 여부 확인 함수 (JWT와 userInfo 체크)
function checkLoginStatus() {
    const token = localStorage.getItem('jwtToken');
    const userInfo = localStorage.getItem('userInfo');
    isLoggedIn.value = !!token && !!userInfo; // 둘 다 존재해야 true
}
function logOut() {
    // activeDropdown.value = null;
    // activeMenu.value = null;
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('userInfo');
    alert('로그아웃되었습니다.');
    isLoggedIn.value = false;
    router.push(`/`);
}
function loGin() {
    router.push(`/login`);
}

// 드롭다운 열고 닫기
const changeLang = (lang) => {
  changeLanguage(lang); // 전역 언어 상태 변경
};

const toggleDropdown = (menu) => {
  activeDropdown.value = activeDropdown.value === menu ? null : menu;
};

const resetDropdown = () => {
  activeDropdown.value = null;
  activeMenu.value = null;
};

// 메뉴 클릭 시 처리
const handleCategoryClick = (menu, category) => {
    setActiveMenu(menu); // 메뉴 활성화
    activeDropdown.value = null; // 드롭다운 닫기
    router.push(`/product/${category}`); // 선택된 카테고리로 이동
};

// 메뉴 활성화
const setActiveMenu = (menu) => {
    activeMenu.value = menu;
};

// 외부 클릭 감지
const handleClickOutside = (event) => {
    const dropdowns = document.querySelectorAll('.dropdown-menu, .dropdown > a, .dropdown > span');
    const isClickInside = [...dropdowns].some((dropdown) =>
        dropdown.contains(event.target)
    );
    if (!isClickInside) {
        activeDropdown.value = null;
    }
};

// 이벤트 등록 및 해제
onMounted(() => {
    window.addEventListener('click', handleClickOutside);
    checkLoginStatus();
});

onBeforeUnmount(() => {
    window.removeEventListener('click', handleClickOutside);
});
</script>

<style scoped>
.navigation {
    font-size: 2rem;
    height: 10vh;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #ffffff;
    border-bottom: 0.1rem solid #e0e0e0;
}

.nav-left {
    display: flex;
    align-items: center;
    text-align: center;
}

.project-logo {
    height: 10vh;
    margin-right: 2rem;
}

.menu-list {
    white-space: nowrap;
    font-size: 3.8rem;
    list-style: none;
    display: flex;
    gap: 1.5rem;
    margin: 0;
    padding: 0;
    align-items: center;
}

.menu-list li {
    font-size: 2.8rem;
    color: #090F16;
    display: inline;
    position: relative;
    padding-left: 2rem;
}

.menu-list li span {
    cursor: pointer;
    color: #333;
    text-decoration: none;
}

.menu-list li a {
    text-decoration: none;
    color: inherit;
}

.menu-list li a.router-link-exact-active {
    color: #6CB1FF;
}

.menu-list li .active {
    color: #6CB1FF;
}

.nav-right {
    display: flex;
    align-items: center;
    gap: 1.5rem;
}

.language {
    color: #627086;
    cursor: pointer;
    font-size: 1.8rem;
}

.profile-container {
    position: relative;
    cursor: pointer;
}

.profile-image {
    width: 3vw;
    border-radius: 50%;
    margin-bottom: -0.5vh;
}

.icon-img {
    width: 2.3vw;
    cursor: pointer;
}

.dropdown-menu {
    position: absolute;
    top: 4rem;
    left: 0;
    background-color: white;
    border: 0.1rem solid #e0e0e0;
    border-radius: 0.5rem;
    box-shadow: 0 0.2rem 1rem rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    min-width: 12rem;
    padding: 1rem 0;
    z-index: 1000;
}

.dropdown-menu li a {
    text-decoration: none;
    color: inherit;
}

.dropdown-menu li a:hover {
    color: #439AFF !important;
}

.dropdown-menu li {
    padding: 0.4rem 2rem;
}

.profile-dropdown {
    right: 0;
    top: 4rem;
    left: auto;
}

.dropdown-font {
    font-size: 2.3rem;
}

.dropdown-font:hover {
    color: #439AFF !important;
}

.active {
    color: #6CB1FF;
    /* 활성화된 메뉴 색상 */
}
.language-setting{
    margin-top: 6px;
    font-size: 4rem;
}
.dropdown-language-menu{
    z-index: 1000;
    position: absolute;
    top: 4rem;
    left: 0;
    background-color: white;
    border: 0.1rem solid #e0e0e0;
    border-radius: 0.5rem;
    box-shadow: 0 0.2rem 1rem rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    min-width: 8rem;
    padding: 0.5rem 0;
}
.dropdown-language{
    text-align: center;
    margin-bottom: 1rem;
    font-size:1.5rem;
}
.dropdown-language1{
    text-align: center;
    font-size:1.5rem;
}
.dropdown-language:hover {
    color: #439AFF !important;
}
.dropdown-language1:hover {
    color: #439AFF !important;
}
</style>
