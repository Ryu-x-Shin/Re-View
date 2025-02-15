import { useCallback, useReducer } from "react";
import LoginForm from "../components/LoginForm";
import { Link } from "react-router-dom";

type Props = {
  label: string;
  components: React.ReactNode
}

const AuthFormLayout = ({label, components}: Props) => {
  return (
    <div>
      <p>{label}</p>
      {components}
    </div>
  )
}

export default AuthFormLayout;