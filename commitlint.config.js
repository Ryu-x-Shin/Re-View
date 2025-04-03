const taskIdPlugin = require('./commitlint-plugin-jira-custom-task-id');

module.exports = {
  extends: ['@commitlint/config-conventional'],
  plugins: [taskIdPlugin],
  parserPreset: {
    parserOpts: {
      /**
       * headerPattern:
       * - 커밋 메시지가 "✨feat: subject"와 같이, 선택적 emoji가 앞에 붙은 타입으로 시작하는 패턴을 잡습니다.
       * - 정규표현식 설명:
       *    ^                                : 문자열의 시작을 명시합니다.
       *    \[([A-Z]+-\d+)\]                 : 중괄호 내부에 대문자 알파벳 1개 이상이면서 -이 있고 숫자 한개 이상이 있는 문자열을 캡쳐
       *    \s                               : 공백 한칸
       *    ((?:[\p{Emoji}][\uFE0F]?)[a-z]+) : 이모지와 선택적 이모지(있을 수도 있고 없을 수도 있고)가 붙어있으면서 뒤에 소문자 알파벳 1개 이상인 문자열을 캡쳐
       *                                      (?:[\p{Emoji}][\uFE0F]?)가 궁금할 텐데
       *                                      ?:는 이 여기에 해당되는 정규식을 매칭에 사용은 하지만 일치한다고 해서 반환하지는 않음
       *                                      ()는 해당 내용에 일치하는 것이 있다면 반환하게 함
       *                                      ()안에는 이모지와 선택적 이모지를 찾는 정규식이 되어있음, 하지만 이모지 자체만 반환하고자 하는 것이 아니라 이모지와 feat 같은 것이 결합된 하나를 반환하고 싶은 거니까 ?:로 매칭할 때 사용하지만 반환하지는 않도록 함
       *                                      그리고 가장 바깥에 ()가 있어서 결론적으로 이모지+선택적이모지+소문자알파벳들이 하나로 모인 결과만 반환하는 것임 그리고 이게 type이 됨
       *    :\s(.+)$/u                       :  :는 말 그대로 :가 존재해야 한다는 것이고 \s는 공백 한칸을 의미함 또한 (.+)s는 줄바꿈을 제외한 한글자 이상이 있어야 하며 해당하는 모든 문자를 캡처한다는 것, 그리고 $는 문자열의 끝을 의미함 그리고 \u는 유니코드 관련해서 사용하기 때문에 필요
       * - **주의:** Unicode property escapes (\p{Emoji})는 Node.js 12 이상에서 u 플래그와 함께 사용 가능합니다.
       */
      headerPattern: /^\[([A-Z]+-\d+)\]\s((?:[\p{Emoji}][\uFE0F]?)[a-z]+):\s(.+)$/u,
      headerCorrespondence: ['task-id', 'type', 'subject'],
    },
  },
  rules: {
    // commit type은 아래 나열된 값들 중 하나여야 합니다.
    'type-enum': [2, 'always', [
      '✨feat',
      '🐛fix',
      '🚑fix',
      '🧪test',
      '♻️refactor',
      '✏️style',
      '🎨style',
      '💄style',
      '🔥chore',
      '📦chore',
      '🔧chore',
      '🚀release',
      '🔼deps',
      '🔽deps',
      '⚡perf',
      '🐎perf',
      '📝docs',
      '🚨chore',
      '🔒security',
      '🏗️refactor'
    ]],

    'task-id-empty': [2, 'never'], // Jira ID는 반드시 있어야 함
    'task-id-case': [2, 'always', 'uppercase'], // 대문자 강제
    'task-id-separator': [2, 'always', '-'], // 하이픈 사용 강제
    'task-id-format': [2, 'always', /^(REVIEW|SCRUM)-\d+$/], // 형식 검증
  },
};