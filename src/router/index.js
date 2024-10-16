import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/body/home/Home.vue";
import Product from "../views/body/product/Product.vue";
import Board from "../views/body/board/Board.vue";
import Notice from "../views/body/notice/Notice.vue";
import FAQ from "../views/body/faq/FAQ.vue";
import Direction from "../views/body/direction/Direction.vue";
import SignUp from "../views/body/member/SignUp.vue";
import Login from "../views/body/member/Login.vue";
import FindId from "../views/body/member/FindId.vue";
import FindPwd from "../views/body/member/FindPwd.vue";
import Mypage from "../views/body/member/Mypage.vue";
import ModifyMypage from "../views/body/member/ModifyMypage.vue";
import Profile from "../views/body/member/Profile.vue";
import ModifyProfile from "../views/body/member/ModifyProfile.vue";

const routes = [
    {
        path: '/login',
        name: 'Login', 
        component: Login 
        
    },
    {
        path: '/',
        name: 'Home',
        component: Home,
    },
    {
        path: '/product/:category',
        name: 'Product',
        component: () => Product,
    },
    {
        path: '/board/:tag',
        name: 'Board',
        component: Board,
    },
    {
        path: '/notice',
        name: 'Notice',
        component: Notice,
    },
    {
        path: '/faq',
        name: 'Faq',
        component: FAQ,
    },
    {
        path: '/direction',
        name: 'Direction',
        component: Direction,
    },
    {
        path: '/signup',
        name: 'SignUp',
        component: SignUp,
    },
    {
        path: '/findId',
        name: 'FindId',
        component: FindId,
    },
    {
        path: '/findPwd',
        name: 'FindPwd',
        component: FindPwd
    },
    {
        path: '/mypage',
        name: 'Mypage',
        component: Mypage
    },
    {
        path: '/modifyMypage',
        name: 'ModifyMypage',
        component: ModifyMypage
    },
    {
        path: '/modifyProfile',
        name: 'ModifyProfile',
        component: ModifyProfile
    },
    {
        path: '/profile',
        name: 'Profile',
        component: Profile
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;