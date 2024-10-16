import axios from 'axios';

export async function fetchProfileData() {
    const jwtToken = localStorage.getItem('jwtToken');
    if (!jwtToken) {
        window.location.href = '/login';
        return;
    }

    try {
        const [profileResponse, likeResponse, myBoardResponse, myCommentResponse] = await Promise.all([
            axios.get('http://localhost:8080/api/v1/member/profile', {
                headers: { Authorization: `Bearer ${jwtToken}` },
            }),
            axios.get('http://localhost:8080/api/v1/member/likeboard', {
                headers: { Authorization: `Bearer ${jwtToken}` },
            }),
            axios.get('http://localhost:8080/api/v1/member/myboard', {
                headers: { Authorization: `Bearer ${jwtToken}` },
            }),
            axios.get('http://localhost:8080/api/v1/member/mycomment', {
                headers: { Authorization: `Bearer ${jwtToken}` },
            }),
        ]);

        const profileData = profileResponse.data.result;

        return {
            nickname: profileData.nickname || 'Unknown',
            profileUrl: profileData.profileUrl || '',
            introduction: profileData.introduction || '',
            language: profileData.language || '미설정',
            boardLike: likeResponse.data.result.boardLike || [],
            board: myBoardResponse.data.result.board || [],
            boardComment: myCommentResponse.data.result.boardComment || [],
        };
    } catch (error) {
        console.error('Failed to fetch profile data:', error);
        throw error;
    }
}
