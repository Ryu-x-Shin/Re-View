// eslint.config.mjs
import { createRequire } from 'module';
const require = createRequire(import.meta.url);

// 각 플러그인은 require()를 통해 불러옵니다.
export default [
  {
    // 전역 환경 설정
    languageOptions: {
      // TypeScript를 사용하므로 @typescript-eslint/parser 사용
      parser: require('@typescript-eslint/parser'),
      parserOptions: {
        ecmaVersion: 2020,
        sourceType: 'module',
        ecmaFeatures: { jsx: true },
      },
    },
    // ESLint가 적용될 파일들 지정 (대기업에서는 일반적으로 src 디렉토리에 집중)
    files: ['src/**/*.{js,jsx,ts,tsx}', 'netlify/functions/*.{js,jsx,ts,tsx}'],
    plugins: {
      // 공식 플러그인들을 불러옵니다.
      '@typescript-eslint': require('@typescript-eslint/eslint-plugin'),
      react: require('eslint-plugin-react'),
      'react-hooks': require('eslint-plugin-react-hooks'),
      prettier: require('eslint-plugin-prettier'),
      'unused-imports': require('eslint-plugin-unused-imports'),
    },
    rules: {
      // Prettier와 통합하여 코드 포맷팅 오류를 ESLint 오류로 처리합니다.
      'prettier/prettier': 'error',
      // 사용하지 않는 import 제거
      'unused-imports/no-unused-imports': 'error',
      // TypeScript 관련 규칙 (프로젝트에 맞게 세밀하게 조정)
      '@typescript-eslint/explicit-function-return-type': 'off',
      '@typescript-eslint/no-unused-vars': [
        'error',
        { argsIgnorePattern: '^_' },
      ],

      // React 관련 규칙
      'react/react-in-jsx-scope': 'off', // React 17+에서 JSX 자동 변환 사용 시 불필요
      'react/prop-types': 'off', // TypeScript 사용 시 PropTypes 불필요
      'react-hooks/rules-of-hooks': 'error',
      'react-hooks/exhaustive-deps': 'warn',

      // 추가적인 기업 표준 규칙 (예: import 정렬, 코드 복잡도 제한 등)
      // "no-console": "warn", // 프로덕션 빌드 전 콘솔 제거 등
    },
    settings: {
      // React 버전 자동 감지
      react: { version: 'detect' },
    },
  },
];
