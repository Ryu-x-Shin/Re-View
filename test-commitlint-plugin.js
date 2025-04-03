// test-commitlint-plugin.js
const jiraPlugin = require('./commitlint-plugin-jira-custom-task-id');
const rules = jiraPlugin.rules;

// 테스트 도우미 함수: 비동기/동기 모두 처리
async function testRule(ruleFn, header, when, value) {
  const result = ruleFn({ header }, when, value);
  // promise 여부 체크
  return result && typeof result.then === 'function' ? await result : result;
}

// 테스트 케이스 실행 함수
(async () => {
  console.log("==== task-id-empty 테스트 ====");
  // 정상: Jira ID가 있음
  console.log(await testRule(rules['task-id-empty'], "[REVIEW-123] commit message", "never"));
  // 에러: Jira ID 없음
  console.log(await testRule(rules['task-id-empty'], "commit message", "never"));

  console.log("\n==== task-id-case 테스트 (대문자 강제) ====");
  // 정상: 대문자
  console.log(await testRule(rules['task-id-case'], "[REVIEW-123] commit message", "always", "uppercase"));
  // 에러: 소문자
  console.log(await testRule(rules['task-id-case'], "[review-123] commit message", "always", "uppercase"));

  console.log("\n==== task-id-case 테스트 (소문자 강제) ====");
  // 정상: 소문자
  console.log(await testRule(rules['task-id-case'], "[review-123] commit message", "always", "lowercase"));
  // 에러: 대문자
  console.log(await testRule(rules['task-id-case'], "[REVIEW-123] commit message", "always", "lowercase"));

  console.log("\n==== task-id-separator 테스트 ====");
  // 정상: '-' 구분자 포함
  console.log(await testRule(rules['task-id-separator'], "[REVIEW-123] commit message", "always"));
  // 에러: '-' 구분자가 없음 (하이픈 사용)
  console.log(await testRule(rules['task-id-separator'], "[REVIEW_123] commit message", "always"));

  console.log("\n==== task-id-format 테스트 ====");
  // 정상: 정규식에 맞음 (REVIEW_123)
  console.log(await testRule(rules['task-id-format'], "[REVIEW_123] commit message", "always", /^(REVIEW|SCRUM)-\d+$/));
  // 에러: 정규식에 맞지 않음 (TASK_123)
  console.log(await testRule(rules['task-id-format'], "[TASK-123] commit message", "always", /^(REVIEW|SCRUM)-\d+$/));
})();
