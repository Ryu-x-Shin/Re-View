// import { useCallback, useReducer } from 'react';
// import LoginForm from '../components/LoginForm';
// import { Link } from 'react-router-dom';
// import styles from './Login.module.scss';
// import SignUpForm from '../features/signup/components/SignUpForm';

// type State = {
//   email: string;
//   password: string;
// };

// type Action = { type: 'email' | 'password'; value: string };

// const reducer = (state: State, action: Action): State => ({
//   ...state,
//   [action.type]: action.value,
// });

// const Login = () => {
//   const [state, dispatch] = useReducer(reducer, { email: '', password: '' });

//   // 이메일 값이 바뀐 거면 Password Form 컴포넌트는 리렌더링이 일어나지 않도록 함
//   // 패스워드 값이 바뀐 거면 Email Form 컴포넌트는 리렌더링이 일어나지 않도록 함
//   // 뒤에 빈 배열은 최초 렌더링 시에만 함수를 생성하고 이후에 리렌더링이 일어나더라도 함수를 새로 만들지 않는다는 의미임
//   const handleEmailChange = useCallback(
//     (value: string) => dispatch({ type: 'email', value }),
//     [],
//   );
//   const handlePasswordChange = useCallback(
//     (value: string) => dispatch({ type: 'password', value }),
//     [],
//   );

//   const components = (
//     <>
//       <LoginForm
//         className={styles.component}
//         label="Email"
//         value={state.email}
//         onChange={handleEmailChange}
//       />
//       <LoginForm
//         className={styles.component}
//         label="Password"
//         value={state.password}
//         onChange={handlePasswordChange}
//       />

//       <p className={styles['login-init']}>~이유로 로그인에 실패했습니다.</p>

//       <div className={`${styles.component} ${styles['button-container']}`}>
//         <button className={styles['btn']}>로그인</button>
//       </div>

//       <div className={`${styles.component} ${styles['button-container']}`}>
//         <span className={`${styles['button-description']}`}>
//           혹시 처음이신가요?
//         </span>
//         <Link
//           className={`${styles['btn-signup']} ${styles['btn-link']}`}
//           to="/signup"
//         >
//           <button className={styles['btn']}>회원가입</button>
//         </Link>
//       </div>

//       <div className={`${styles.component} ${styles['button-container']}`}>
//         <span className={styles['button-description']}>비밀번호 찾기</span>
//         <Link
//           className={`${styles['btn-findpw']} ${styles['btn-link']}`}
//           to="/findpassword"
//         >
//           <button className={styles['btn']}>비밀번호 찾기</button>
//         </Link>
//       </div>
//     </>
//   );

//   return <SignUpForm label="Re:View" components={components} />;
// };

// export default Login;
const Login = () => {};

export default Login;
