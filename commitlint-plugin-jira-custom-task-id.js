module.exports = {
  rules: {
    /**
     * 1️⃣ Jira ID가 비어있는지 확인 (반드시 존재해야 함)
     */
    'task-id-empty': ({ header }, when) => {
      if (when === 'ignore') return [true]; // 규칙 무시
      const hasTaskId = /^\[([A-Za-z0-9-]+)\]/.test(header);
      return hasTaskId ? [true] : [false, '✖  Jira ID가 없습니다. 예: [REVIEW-123]'];
    },

    /**
     * 2️⃣ Jira ID 대소문자 규칙 적용
     */
    'task-id-case': ({ header }, when, value) => {
      if (when === 'ignore' || !value) return [true];
      const match = header.match(/^\[([A-Za-z0-9-]+)\]/);
      if (!match) return [true];
      
      const id = match[1];
      const isUpperCase = id === id.toUpperCase();
      const isLowerCase = id === id.toLowerCase();

      if (value === 'uppercase' && !isUpperCase) {
        return [false, '✖  Jira ID는 대문자로 작성해야 합니다. 예: [REVIEW-123]'];
      }
      if (value === 'lowercase' && !isLowerCase) {
        return [false, '✖  Jira ID는 소문자로 작성해야 합니다. 예: [review-123]'];
      }
      return [true];
    },

    /**
     * 3️⃣ Jira ID의 구분자(separator) 하이픈('-')으로 강제
     */
    'task-id-separator': ({ header }, when) => {
    if (when === 'ignore') return [true];
    
    // 대괄호 내에서 오직 대소문자, 숫자, dash('-')만 허용하도록 매칭합니다.
    const match = header.match(/^\[([A-Za-z0-9-]+)\]/);
    
    // 매칭이 안 되면 올바른 형식이 아니라 에러 반환
    if (!match) {
      return [false, "✖  Jira ID 형식이 잘못되었습니다. 예: [REVIEW-123]"];
    }
    
    const id = match[1];
    
    // 올바른 형식: 한글자 이상의 영문(대소문자) + dash + 한글자 이상의 숫자만 허용
    if (!/^([A-Za-z]+-[0-9]+)$/.test(id)) {
      return [false, "✖  Jira ID는 하이픈('-')을 포함해야 하며 올바른 형식이어야 합니다. 예: [REVIEW-123]"];
    }
    return [true];
  },


    /**
     * 4️⃣ Jira ID 형식 검증 (유동적인 정규식)
     */
    'task-id-format': ({ header }, when, regex) => {
      if (when === 'ignore' || !(regex instanceof RegExp)) return [true];
      const match = header.match(/^\[([A-Za-z0-9-]+)\]/);
      if (!match) return [true];
      
      return regex.test(match[1])
        ? [true]
        : [false, `✖  Jira ID 형식이 올바르지 않습니다. 예: [REVIEW-123] 또는 [SCRUM-456]`];
    },
  },
};
