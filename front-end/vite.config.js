import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(), // Vue.js 플러그인 등록
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)), // '@'를 './src'로 설정
    },
  },
  server: {
    proxy: {
      '/api': {
        // 백엔드 컨테이너 이름을 사용하여 프록시 설정
        target: 'http://springboot-app:8080/', // 여기서 백엔드 컨테이너의 포트 번호를 수정
        changeOrigin: true, // 원본 헤더를 변경하여 백엔드에 요청 전송
        rewrite: (path) => path.replace(/^\/api/, ''), // '/api' 경로를 제거
      }
    }
  }
});
