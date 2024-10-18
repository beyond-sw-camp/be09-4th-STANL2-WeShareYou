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
import MemberIP from "../views/body/admin/MemberIP.vue";
import OtherProfile from "../views/body/member/OtherProfile.vue";
import Chat from "../views/body/chat/Chat.vue";
import BoardDetail from "@/views/body/board/BoardDetail.vue";

import NoticeDetail from "@/views/body/notice/components/NoticeDetail.vue";
import NoticePost from '@/views/body/notice/components/NoticePost.vue';
import NoticeModify from '@/views/body/notice/components/NoticeModify.vue';

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
        path: '/product',
        name: 'Product',
        component: () => import('@/views/body/product/Product.vue'),
        redirect: {path:"/product/:category"},
        children: [
            {
                path: ':category',
                name: 'ProductList',
                component: () => import('@/views/body/product/ProductList.vue'),
            },
            {
                path: ':category/:id', // :category 뒤에 :id 추가
                name: 'ProductDetail',
                component: () => import('@/views/body/product/ProductDetail.vue'),
            },
            {
                path: ':remove/:id', // :category 뒤에 :id 추가
                name: 'ProductRemove',
                component: () => import('@/components/cud/PostRemove.vue'),
            },
        ],
    },
    {
        path: '/product/regist', // 상품 등록 페이지
        name: 'ProductRegist',
        component: () => import('@/views/body/product/ProductRegist.vue'),
    },
    {
        path: '/product/:category/:id/modify',
        name: 'ProductModify',
        component: () => import('@/views/body/product/ProductModify.vue'),
    },
    {
        path: '/board/:tag',
        name: 'Board',
        component: () => Board,
    },
    {
        path: '/board/detal/:id',
        name: 'BoardDetail',
        component: () => BoardDetail,
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
    },
    {
        path: '/chat',
        name: 'Chat',
        component: Chat
    },
    {
        path: '/admin/memberIp',
        name: 'MemberIP',
        component: MemberIP
    },
    {
        path: '/otherprofile/:nickname', 
        name: 'OtherProfile',
        component: OtherProfile,
        props: true 
    }
    {
        path: '/notice/:id',
        name: 'NoitceDetail',
        component: NoticeDetail
    },
    {
        path: '/notice/post',
        name: 'NoticePost',
        component: NoticePost
    },
    {
        path: '/notice',
        name: 'NoticeModify',
        component: NoticeModify
    }

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;