import { useCallback, useReducer } from "react";
import LoginForm from "../components/LoginForm";
import { Link } from "react-router-dom";
import "./Login.scss"
import AuthFormLayout from "../components/AuthFormLayout";

type State = {
  email: string;
  password: string;
};

type Action = { type: "email" | "password"; value: string };

const reducer = (state: State, action: Action): State => ({
  ...state,
  [action.type]: action.value,
});

const Login = () => {
  const [state, dispatch] = useReducer(reducer, { email: "", password: "" });

  // 이메일 값이 바뀐 거면 Password Form 컴포넌트는 리렌더링이 일어나지 않도록 함
  // 패스워드 값이 바뀐 거면 Email Form 컴포넌트는 리렌더링이 일어나지 않도록 함
  // 뒤에 빈 배열은 최초 렌더링 시에만 함수를 생성하고 이후에 리렌더링이 일어나더라도 함수를 새로 만들지 않는다는 의미임
  const handleEmailChange = useCallback((value: string) => dispatch({type: "email", value}), []);
  const handlePasswordChange = useCallback((value: string) => dispatch({type: "password", value}), []);

  const components= 
    <>
      <LoginForm
        label="Email"
        value={state.email}
        onChange={handleEmailChange}
      />
      <LoginForm
        label="Password"
        value={state.password}
        onChange={handlePasswordChange}
      />

      <div>
        <button>로그인</button>
      </div>

      <div>
        <span className="button-description">혹시 처음이신가요?</span>
        <Link to="/signup"><button>회원가입</button></Link>
      </div>

      <div>
        <span className="button-description">비밀번호 찾기</span>
        <Link to="/findpassword"><button>비밀번호 찾기</button></Link>
      </div>
    </>


  
  return (
      <AuthFormLayout 
        label="Re:View"
        components={ components } 
      />
  )

}

export default Login;