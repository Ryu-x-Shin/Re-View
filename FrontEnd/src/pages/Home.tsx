import { Link } from "react-router-dom";

const Home = () => {
  return (
    <ul>
      <li>
        <Link to="/login">로그인</Link>
      </li>
      <li>
        <Link to="/register">회원가입</Link>
      </li>
    </ul>
  )
}

export default Home;