import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/body/home/Home.vue";
import Product from "../views/body/product/Product.vue";
import Board from "../views/body/board/Board.vue";
import Notice from "../views/body/notice/Notice.vue";
import FAQ from "../views/body/faq/FAQ.vue";
import Direction from "../views/body/direction/Direction.vue";
import Post from "../components/cud/Post.vue"
import Modify from "../components/cud/Modify.vue"
// 아래 샘플!!
import Sample from "../views/body/sample/Sample.vue";
import Login from "../views/body/member/Login.vue";

const routes = [
    {
        path: '/sample',
        name: 'Sample',
        component: Sample
    },
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
        component: Product,
    },
    {
        path: '/board',
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
        path: '/post',
        name: 'Post',
        component: Post
    },
    {
        path: '/modify',
        name: 'Modify',
        component: Modify
    }

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;