import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import Antd from 'ant-design-vue';
import {createPinia} from "pinia";
import {router} from "./routes.ts";

const app = createApp(App)
app.use(createPinia())
app.use(Antd)
app.use(router)

app.mount('#app')
