<template>
    <!-- <nav class="navigation"> -->
    <nav :class="['navigation', { 'hidden-nav': isHidden }]">
        <div class="nav-left">
            <!-- 프로젝트 대표이미지 -->
            <RouterLink to="/" @click="resetDropdown">
                <img src="../../assets/icon/navigation/logo.png" alt="Project Logo" class="project-logo" />
            </RouterLink>

            <ul class="menu-list">
                <!-- 드롭다운 -->
                <li class="dropdown" @click="toggleDropdown('product')">
                    <span :class="{ active: activeMenu === 'product' }">{{ translatedMenu.product }}</span>
                    <ul v-show="activeDropdown === 'product'" class="dropdown-menu" @click.stop>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/NECESSITIES'"
                                @click="handleCategoryClick('product', 'NECESSITIES')">{{
                                    translatedCategories.necessities }}</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/KITCHENWARES'"
                                @click="handleCategoryClick('product', 'KITCHENWARES')">{{
                                    translatedCategories.kitchenwares }}</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/CLOTHES'" @click="handleCategoryClick('product', 'CLOTHES')">{{
                                translatedCategories.clothes }}
                            </RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/TOY'" @click="handleCategoryClick('product', 'TOY')">{{
                                translatedCategories.toy }}
                            </RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/DEVICE'" @click="handleCategoryClick('product', 'DEVICE')">{{
                                translatedCategories.device }}
                            </RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink :to="'/product/ETC'" @click="handleCategoryClick('product', 'ETC')">{{
                                translatedCategories.etc }}
                            </RouterLink>
                        </li>
                    </ul>
                </li>


                <!-- 게시글 드롭다운 -->
                <li class="dropdown" @click="toggleDropdown('board')">
                    <span :class="{ active: activeMenu === 'board' }">{{ translatedMenu.board }}</span>
                    <ul v-show="activeDropdown === 'board'" class="dropdown-menu" @click.stop>
                        <li class="dropdown-font">
                            <RouterLink to="/board/GUIDE" @click="setActiveMenu('board')">{{ translatedMenu.guide }}
                            </RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/board/FREEMARKET" @click="setActiveMenu('board')">{{
                                translatedMenu.freemarket }}</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/board/ACCOMPANY" @click="setActiveMenu('board')">{{
                                translatedMenu.companion }}</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/board/TIP" @click="setActiveMenu('board')">{{ translatedMenu.tip }}
                            </RouterLink>
                        </li>
                    </ul>
                </li>

                <li>
                    <RouterLink to="/notice" @click="resetDropdown">{{ translatedMenu.notice }}</RouterLink>
                </li>
                <li>
                    <RouterLink to="/faq" @click="resetDropdown">{{ translatedMenu.faq }}</RouterLink>
                </li>
                <li>
                    <RouterLink to="/direction" @click="resetDropdown">{{ translatedMenu.direction }}</RouterLink>
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



            <img src="../../assets/icon/navigation/alarm.png" class="icon-img" alt="alarm" @click="toggleModal" />
            <div v-show="isModalVisible" class="alarm-modal">
                <div v-if="alarms.length === 0" class="alarm-item">
                    알림이 없습니다.
                </div>
                <div v-else>
                    <div v-for="(item, index) in alarms" :key="index" class="alarm-item">
                        {{ item.message }}
                    </div>
                </div>
            </div>

            <img src="../../assets/icon/navigation/message.png" class="icon-img" alt="message" />

            <!-- 프로필 이미지 -->
            <div class="profile-container" @click="toggleDropdown('profile')" @click.stop>
                <img :src="profileImage ? profileImage : defaultProfileImage" alt="Profile" class="profile-image" />
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
import { ref, onMounted, onBeforeUnmount, inject, watch } from 'vue';
import { useRouter } from 'vue-router';
import { translateText } from '@/assets/language/deepl';
import defaultProfileImage from '../../assets/icon/navigation/profile.png';
import axios from 'axios';

// inject로 전역 언어 상태와 변경 함수 받아오기
const currentLang = inject('currentLang');
const changeLanguage = inject('changeLanguage');

const alarms = ref([]);
const cursorId = ref(''); // cursorId를 빈 문자열로 초기화
const hasNext = ref(true); // 다음 페이지 여부를 서버 응답으로 관리
const loading = ref(false); // 로딩 중인지 여부를 관리

const translatedMenu = ref({
    product: '공유 물품',
    board: '게시글',
    guide: '가이드',
    freemarket: '프리마켓',
    companion: '동행',
    tip: 'TIP',
    notice: '공지사항',
    faq: '자주 묻는 질문',
    direction: '오시는 길',
});

const translatedCategories = ref({
    necessities: '생활품',
    kitchenwares: '주방용품',
    clothes: '의류',
    toy: '놀이',
    device: '전자기기',
    etc: '기타',
});

const translateMenu = async (lang) => {
    translatedMenu.value.product = await translateText('공유 물품', lang);
    translatedMenu.value.board = await translateText('게시글', lang);
    translatedMenu.value.guide = await translateText('가이드', lang);
    translatedMenu.value.freemarket = await translateText('프리마켓', lang);
    translatedMenu.value.companion = await translateText('동행', lang);
    translatedMenu.value.tip = await translateText('TIP', lang);
    translatedMenu.value.notice = await translateText('공지사항', lang);
    translatedMenu.value.faq = await translateText('자주 묻는 질문', lang);
    translatedMenu.value.direction = await translateText('오시는 길', lang);

    translatedCategories.value.necessities = await translateText('생활품', lang);
    translatedCategories.value.kitchenwares = await translateText('주방용품', lang);
    translatedCategories.value.clothes = await translateText('의류', lang);
    translatedCategories.value.toy = await translateText('놀이', lang);
    translatedCategories.value.device = await translateText('전자기기', lang);
    translatedCategories.value.etc = await translateText('기타', lang);
};
// 전역 언어 상태를 감시하고 언어에 따라 메뉴를 DeepL로 번역



const activeDropdown = ref(null);
const activeMenu = ref(null);
// 
const isHidden = ref(false);
const router = useRouter();
const isLoggedIn = ref(false);
const profileImage = ref('');

const isModalVisible = ref(false);

const toggleModal = async () => {
    if (!isModalVisible.value) {
        await fetchAlarmItems(true); // 알림 모달을 열 때 API 호출 (초기화)
    }
    isModalVisible.value = !isModalVisible.value; // 모달 열기/닫기
};

const fetchAlarmItems = async (reset = false) => {
    if (loading.value || (!reset && !hasNext.value)) return; // 로딩 중이거나 더 불러올 데이터가 없으면 중단

    loading.value = true;

    if (reset) {
        alarms.value = []; // 알림 초기화
        cursorId.value = ''; // 커서 초기화
        hasNext.value = true;
    }

    const jwtToken = localStorage.getItem('jwtToken');

    try {
        const response = await axios.get(
            `http://localhost:8080/api/v1/alarm`,
            {
                headers: { Authorization: `Bearer ${jwtToken}` },
                params: { cursor: cursorId.value || '', size: 5 }
            }
        );

        console.log("API Response:", response.data);

        let data = response.data;
        let newContents = data.result?.contents || [];

        // 새로운 알림 추가
        alarms.value = [...alarms.value, ...newContents];
        cursorId.value = data.result?.cursorId || ''; // 다음 커서 업데이트
        hasNext.value = data.result?.hasNext; // 다음 페이지 여부 업데이트

        console.log("Alarms after assignment:", alarms.value);
    } catch (error) {
        console.error("API 호출 에러:", error.response?.data || error.message);
    } finally {
        loading.value = false; // 로딩 종료
    }
};

// 로그인 여부 확인 함수 (JWT와 userInfo 체크)
function checkLoginStatus() {
    const token = localStorage.getItem('jwtToken');
    const userInfo = localStorage.getItem('userInfo');

    if (token && userInfo) {
        isLoggedIn.value = true;
        profileImage.value = JSON.parse(userInfo).profile || ''; // 프로필 이미지 설정
    } else {
        isLoggedIn.value = false;
        profileImage.value = ''; // 기본 이미지로 초기화
    }
}
function logOut() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('userInfo');
    alert('로그아웃되었습니다.');
    isLoggedIn.value = false;
    profileImage.value = ''; // 이미지 초기화
    router.push(`/`);
}
function loGin() {
    router.push(`/login`);
}

watch(currentLang, (newLang) => {
    translateMenu(newLang);
});

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

// 
const handleScroll = () => {
    if (window.scrollY < lastScrollY) {
        isHidden.value = false; // 스크롤 위로 시 헤더 표시
    } else if (window.scrollY > 50) {
        isHidden.value = true; // 스크롤 아래로 시 헤더 숨김
    }
    lastScrollY = window.scrollY;

    const gridElement = document.querySelector('.product-grid');
    if (gridElement.scrollTop + gridElement.clientHeight >= gridElement.scrollHeight) {
        fetchProductItems(); // 페이지 끝에 도달하면 데이터 요청
    }
};

// 이벤트 등록 및 해제
onMounted(() => {
    window.addEventListener('scroll', handleScroll);
    window.addEventListener('click', handleClickOutside);
    checkLoginStatus();
    fetchAlarmItems();
    const gridElement = document.querySelector('.alarm-modal');
    gridElement.addEventListener('scroll', handleScroll);
});

onBeforeUnmount(() => {
    window.removeEventListener('scroll', handleScroll);
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

.hidden-nav {
    transform: translateY(-100%);
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

.language-setting {
    margin-top: 6px;
    font-size: 4rem;
}

.dropdown-language-menu {
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

.dropdown-language {
    text-align: center;
    margin-bottom: 1rem;
    font-size: 1.5rem;
}

.dropdown-language1 {
    text-align: center;
    font-size: 1.5rem;
}

.dropdown-language:hover {
    color: #439AFF !important;
}

.dropdown-language1:hover {
    color: #439AFF !important;
}

.alarm-modal {
    position: absolute;
    top: 8rem;
    right: 2rem;
    background-color: white;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    width: 300px;
    z-index: 1000;
    padding: 1rem;
    height: 50vh;
    overflow-y: auto;
}

.alarm-item {
    padding: 1rem;
    font-size: 1.6rem;
    border-bottom: 1px solid #f0f0f0;
    cursor: pointer;
}

.alarm-item:last-child {
    border-bottom: none;
}

.alarm-item:hover {
    background-color: #f0f8ff;
}

.new-message {
    background-color: #e8f4ff;
}
</style>