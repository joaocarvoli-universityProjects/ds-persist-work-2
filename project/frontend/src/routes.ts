import Home from "./views/Home.vue";
import Category from "./views/Category.vue";
import Manufacturer from "./views/Manufacturer.vue";
import Product from "./views/Product.vue";
import Stock from "./views/Stock.vue";
import {createRouter, createWebHistory} from "vue-router";


const routes = [
    {
        path: "/",
        component: Home,
    },
    {
        path: "/category",
        component: Category,
    },
    {
        path: "/manufacturer",
        component: Manufacturer,
    },
    {
        path: "/product",
        component: Product,
    },
    {
        path: "/stock",
        component: Stock,
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes
})