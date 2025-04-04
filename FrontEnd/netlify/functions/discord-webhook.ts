import { Handler } from '@netlify/functions';

const handler: Handler = async (event) => {
  try {
    const payload = JSON.parse(event.body || '{}');

    const discordMessage = {
      username: 'Netlify Bot',
      embeds: [
        {
          title: '🚀 Netlify 배포 알림',
          color: payload.state === 'success' ? 0x00ff00 : 0xff0000, // 성공이면 초록, 실패면 빨강
          fields: [
            {
              name: '📌 배포 상태',
              value: `\`${payload.state}\``,
              inline: true,
            },
            {
              name: '🔗 사이트 URL',
              value: `[바로가기](${payload.admin_url})`,
              inline: true,
            },
            {
              name: '⏳ 배포 시간',
              value: `<t:${Math.floor(payload.deploy_time / 1000)}:F>`,
              inline: false,
            },
            {
              name: '📝 커밋 메시지',
              value: `\`${payload.commit_message || '정보 없음'}\``,
              inline: false,
            },
            {
              name: '👤 배포한 사람',
              value: `\`${payload.commit_author || '알 수 없음'}\``,
              inline: true,
            },
          ],
          footer: {
            text: 'Netlify 자동 배포 시스템',
          },
          timestamp: new Date().toISOString(),
        },
      ],
    };

    const response = await fetch(process.env.DISCORD_WEBHOOK_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(discordMessage),
    });

    if (!response.ok) {
      throw new Error(`디스코드 메시지 전송 실패: ${response.statusText}`);
    }

    return { statusCode: 200, body: 'OK' };
  } catch (error) {
    return { statusCode: 500, body: `에러 발생: ${error.message}` };
  }
};

export { handler };
