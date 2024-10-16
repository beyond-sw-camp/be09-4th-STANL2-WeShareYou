<template>
    <nav class="navigation">
        <div class="nav-left">
            <!-- 프로젝트 대표이미지 -->
            <RouterLink to="/" @click="resetDropdown">
                <img src="../../assets/icon/navigation/logo.png" alt="Project Logo" class="project-logo" />
            </RouterLink>

            <!-- 메뉴 리스트 -->
            <ul class="menu-list">
                <li>
                    <RouterLink to="/sample" @click="resetDropdown">피니아 샘플</RouterLink>
                </li>

                <!-- 공유 물품 드롭다운 -->
                <li class="dropdown" @click="toggleDropdown('product')">
                    <span :class="{ active: activeMenu === 'product' }">공유 물품</span>
                    <ul v-show="activeDropdown === 'product'" class="dropdown-menu" @click.stop>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category1" @click="setActiveMenu('product')">생활품</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category2" @click="setActiveMenu('product')">주방용품</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category3" @click="setActiveMenu('product')">의류</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category4" @click="setActiveMenu('product')">놀이</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category5" @click="setActiveMenu('product')">전자기기</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category6" @click="setActiveMenu('product')">기타</RouterLink>
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
            <span class="language">Language</span>
            <img src="../../assets/icon/navigation/alarm.png" class="icon-img" alt="alarm" />
            <img src="../../assets/icon/navigation/message.png" class="icon-img" alt="message" />

            <!-- 프로필 이미지 -->
            <div class="profile-container" @click="toggleDropdown('profile')" @click.stop>
                <img src="../../assets/icon/navigation/profile.png" alt="Profile" class="profile-image" />
                <!-- 프로필 드롭다운 메뉴 -->
                <ul v-show="activeDropdown === 'profile'" class="dropdown-menu profile-dropdown" @click.stop>
                    <li  @click="resetDropdown">
                        <RouterLink to="/mypage">마이페이지</RouterLink>
                    </li>
                    <li  @click="resetDropdown">
                        <RouterLink to="/profile">내 프로필</RouterLink>
                    </li>
                    <li  @click="resetDropdown">
                        <RouterLink to="/logout">로그아웃</RouterLink>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const activeDropdown = ref(null)
const activeMenu = ref(null)

// 드롭다운 열고 닫기
const toggleDropdown = (menu) => {
    activeDropdown.value = activeDropdown.value === menu ? null : menu
}

// 메뉴 활성화
const setActiveMenu = (menu) => {
    activeMenu.value = menu
    activeDropdown.value = null // 드롭다운 닫기
}

// 드롭다운 및 메뉴 초기화
const resetDropdown = () => {
    activeDropdown.value = null
    activeMenu.value = null
}

// 외부 클릭 감지
const handleClickOutside = (event) => {
    const dropdowns = document.querySelectorAll('.dropdown-menu, .dropdown > a, .dropdown > span')
    const isClickInside = [...dropdowns].some(dropdown =>
        dropdown.contains(event.target)
    )
    if (!isClickInside) {
        activeDropdown.value = null
    }
}

// 이벤트 등록 및 해제
onMounted(() => {
    window.addEventListener('click', handleClickOutside)
})

onBeforeUnmount(() => {
    window.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
.navigation {
    font-size: 2rem;
    height: 9rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 8rem;
    background-color: #ffffff;
    border-bottom: 0.1rem solid #e0e0e0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 80px;
    background-color: #ffffff;
    border-bottom: 1px solid #e0e0e0;
}

.nav-left {
    display: flex;
    align-items: center;
    text-align: center;
}

.project-logo {
    height: 4.5rem;
    height: 4rem;
    margin-right: 2rem;
}

.menu-list {
    font-size:2rem;
    list-style: none;
    display: flex;
    gap: 1.5rem;
    margin: 0;
    padding: 0;
    align-items: center;
}

.menu-list li {
    font-size: 2rem;
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
    font-weight: 200;
    cursor: pointer;
}

.profile-container {
    position: relative;
    cursor: pointer;
}

.profile-image {
    width: 3rem;
    height: 3rem;
    border-radius: 50%;
}

.icon-img {
    width: 25px;
    height: 25px;
    cursor: pointer;
}

.dropdown-menu {
    position: absolute;
    top: 30px;
    left: 0;
    background-color: white;
    border: 1px solid #e0e0e0;
    border-radius: 5px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    min-width: 120px;
    padding: 10px 0;
    z-index: 1000;
}

.dropdown-menu li a {
    text-decoration: none;
    color: inherit;
}

.dropdown-menu li:hover {
    background-color: #f0f0f0;
}

.dropdown-menu li a:hover {
    color: #439AFF !important;
}

.dropdown-menu li {
    padding: 10px 20px;
}

.profile-dropdown {
    right: 0;
    top: 40px;
    left: auto;
}

.dropdown-font:hover {
    color: #439AFF !important;
}

.active {
    color: #6CB1FF; /* 활성화된 메뉴 색상 */
}
</style>
