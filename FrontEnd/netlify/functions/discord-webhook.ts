import { Handler } from "@netlify/functions";

const handler: Handler = async (event) => {
  try {
    const payload = JSON.parse(event.body || "{}");

    const discordMessage = {
      username: "Netlify Bot",
      content: `🚀 **배포 상태:** ${payload.state}\n🔗 [사이트 보기](${payload.admin_url})`,
    };

    const response = await fetch(process.env.DISCORD_WEBHOOK_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(discordMessage),
    });

    if (!response.ok) {
      throw new Error(`디스코드 메시지 전송 실패: ${response.statusText}`);
    }

    return { statusCode: 200, body: "OK" };
  } catch (error) {
    return { statusCode: 500, body: `에러 발생: ${error.message}` };
  }
};

export { handler };