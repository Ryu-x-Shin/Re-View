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
      import: require('eslint-plugin-import')
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
      'prettier/prettier': [
        'error',
        {
          endOfLine: 'auto',
        }
      ],

      // 추가적인 기업 표준 규칙 (예: import 정렬, 코드 복잡도 제한 등)
      // "no-console": "warn", // 프로덕션 빌드 전 콘솔 제거 등
      // React Bullet-Proof에 따라서 feature 간 교차 import 등을 방지하기 위한 규칙
      // 
      'import/no-restricted-paths': [
        'error',
        {
            basePath: './',
            zones: [
                // disables cross-feature imports:
                // eg. src/features/discussions should not import from src/features/comments, etc.
                // Feature 폴더 내에서 교차 import를 방지하는 규칙
                // ex : target에서 from에 해당하는 경로의 파일들을 import할 수 없지만, except는 가능
                {
                  target: './src/features/logins',
                  from: './src/features',
                  except: ['./login'],
                },
                {
                    target: './src/features/signup',
                    from: './src/features',
                    except: ['./signup'],
                },
                {
                    target: './src/features/comments',
                    from: './src/features',
                    except: ['./comments'],
                },
                {
                    target: './src/features/discussions',
                    from: './src/features',
                    except: ['./discussions'],
                },
                {
                    target: './src/features/teams',
                    from: './src/features',
                    except: ['./teams'],
                },
                {
                    target: './src/features/users',
                    from: './src/features',
                    except: ['./users'],
                },
                {
                    target: './src/features/home',
                    from: './src/features',
                    except: ['./home'],
                },
                // 마찬가지로 features에서 app에 있는 내용을 import 할 수는 없음
                {
                  target: './src/features',
                  from: './src/app',
                },
        
                // e.g src/features and src/app can import from these shared modules but not the other way around
                // component 등 공용으로 사용되어져야 하는 곳에서 features, app에 있는 것을 import 할 수 없음
                {
                    target: [
                        './src/components',
                        './src/hooks',
                        './src/lib',
                        './src/types',
                        './src/utils',
                    ],
                    from: ['./src/features', './src/app'],
                },

                // More restrictions...
            ],
        },
      ],
    },
    settings: {
      // React 버전 자동 감지
      react: { version: 'detect' },
      'import/resolver': {
        'node': {
          'extensions': [
            '.ts',
            '.tsx'
          ]
        }
      }
    },
  },
];
