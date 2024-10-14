import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080',  // 기본 URL 설정.
    headers: {
        'Content-Type': 'application/json',  // 기본 Content-Type 설정.
    }
});

export default instance;