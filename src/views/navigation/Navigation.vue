<template>
    <nav class="navigation">
        <div class="nav-left">
            <!-- 프로젝트 대표이미지 -->
            <RouterLink to="/">
                <img src="../../assets/icon/navigation/logo.png" alt="Project Logo" class="project-logo" />
            </RouterLink>

            <!-- 메뉴 리스트 -->
            <ul class="menu-list">
                <li>
                    <RouterLink to="/sample">피니아 샘플</RouterLink>
                </li>

                <!-- 공유 물품 드롭다운 -->
                <li class="dropdown" @mouseenter="toggleDropdown('product')" @mouseleave="hideDropdown">
                    <RouterLink to="/product">공유 물품</RouterLink>
                    <ul v-show="activeDropdown === 'product'" class="dropdown-menu">
                        <li class="dropdown-font">
                            <RouterLink to="/product/category1">생활품</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category2">주방용품</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category3">의류</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category4">놀이</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category5">전자기기</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/product/category6">기타</RouterLink>
                        </li>
                    </ul>
                </li>

                <!-- 게시글 드롭다운 -->
                <li class="dropdown" @mouseenter="toggleDropdown('board')" @mouseleave="hideDropdown">
                    <RouterLink to="/board">게시글</RouterLink>
                    <ul v-show="activeDropdown === 'board'" class="dropdown-menu">
                        <li class="dropdown-font">
                            <RouterLink to="/board/notice">가이드</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/board/event">프리마켓</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/board/faq">동행</RouterLink>
                        </li>
                        <li class="dropdown-font">
                            <RouterLink to="/board/faq">TIP</RouterLink>
                        </li>
                    </ul>
                </li>

                <li>
                    <RouterLink to="/notice">공지사항</RouterLink>
                </li>
                <li>
                    <RouterLink to="/faq">자주 묻는 질문</RouterLink>
                </li>
                <li>
                    <RouterLink to="/direction">오시는 길</RouterLink>
                </li>
            </ul>
        </div>

        <div class="nav-right">
            <span class="language">Language</span>
            <img src="../../assets/icon/navigation/alarm.png" class="icon-img" alt="alarm" />
            <img src="../../assets/icon/navigation/message.png" class="icon-img" alt="message" />
            <img src="../../assets/icon/navigation/profile.png" alt="Profile" class="profile-image" />
        </div>
    </nav>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const activeDropdown = ref(null)

// 드롭다운 열고 닫기
const toggleDropdown = (menu) => {
    activeDropdown.value = activeDropdown.value === menu ? null : menu
}

// 외부 클릭 감지
const handleClickOutside = (event) => {
    const dropdowns = document.querySelectorAll('.dropdown-menu, .dropdown > a')
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

// 드롭다운 마우스 떠날 때 닫기
const hideDropdown = () => {
    activeDropdown.value = null
}
</script>

<style scoped>
.navigation {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 80px;
    background-color: #ffffff;
    border-bottom: 1px solid #e0e0e0;
}

.nav-left {
    display: flex;
    align-items: center;
    text-align: center;
}

.project-logo {
    height: 40px;
    margin-right: 20px;
}

.menu-list {
    list-style: none;
    display: flex;
    gap: 15px;
    margin: 0;
    padding: 0;
    align-items: center;
}

.menu-list li {
    display: inline;
    position: relative;
}

.menu-list li a {
    text-decoration: none;
    color: #333;
    padding: 5px 10px;
}

.menu-list li a.router-link-exact-active {
    color: #6CB1FF;
}

.nav-right {
    display: flex;
    align-items: center;
    gap: 15px;
}

.language {
    color: #627086;
    font-weight: 200;
    cursor: pointer;
}

.profile-image {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    cursor: pointer;
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
    opacity: 0;
    transform: translateY(-10px);
    transition: opacity 0.2s, transform 0.2s;
    pointer-events: none;
}

.menu-list li:hover .dropdown-menu {
    opacity: 1;
    transform: translateY(0);
    pointer-events: auto;
}

.dropdown-menu li {
    padding: 10px 20px;
}

.dropdown-font :hover {
    color: #439AFF !important;
}
</style>