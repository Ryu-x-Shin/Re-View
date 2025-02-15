module.exports = {
  extends: ['@commitlint/config-conventional'],
  parserPreset: {
    parserOpts: {
      /**
       * headerPattern:
       * - 커밋 메시지가 "✨feat: subject"와 같이, 선택적 emoji가 앞에 붙은 타입으로 시작하는 패턴을 잡습니다.
       * - 정규표현식 설명:
       *    ^                         : 문자열 시작
       *    ((?:\p{Emoji})?[a-z]+)     : (선택적 emoji 한 개 + 소문자 알파벳 하나 이상)을 캡처 (commit type)
       *    (?:\(([^)]+)\))?          : 선택적으로 괄호 안의 scope를 캡처 (없어도 됨)
       *    :\s                      : 콜론 뒤에 공백
       *    (.+)$                   : 나머지(커밋 메시지 본문)를 캡처
       * - **주의:** Unicode property escapes (\p{Emoji})는 Node.js 12 이상에서 u 플래그와 함께 사용 가능합니다.
       */
      headerPattern: /^((?:[\p{Emoji}][\uFE0F]?)?[a-z]+)(?:\(([^)]+)\))?:\s(.+)$/u,
      headerCorrespondence: ['type', 'scope', 'subject'],
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
  },
};
