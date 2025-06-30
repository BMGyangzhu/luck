import { createApp } from 'vue'
import './style.css'
import ElementPlus from 'element-plus'
import router from './router'
import App from './App.vue'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia'
const pinia = createPinia()
const app = createApp(App)
app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.mount('#app')
