import axios from 'axios';

export async function modifyProfile(nickname, introduction, language, file) {
    try {
        const formData = new FormData();
        formData.append('file', file); // 이미지 파일 추가
        formData.append(
            'vo',
            JSON.stringify({
                nickname: nickname,
                introduction: introduction,
                language: language,
            })
        );

        const jwtToken = localStorage.getItem('jwtToken');

        const response = await axios.put(
            'http://localhost:8080/api/v1/member/profile',
            formData,
            {
                headers: {
                    Authorization: `Bearer ${jwtToken}`,
                    'Content-Type': 'multipart/form-data',
                },
            }
        );

        console.log('프로필 수정 성공:', response.data);
        alert('프로필이 성공적으로 수정되었습니다!');
        return response.data.result;
    } catch (error) {
        console.error('프로필 수정 실패:', error);
        alert('프로필 수정에 실패했습니다. 다시 시도해 주세요.');
        throw error;
    }
}
