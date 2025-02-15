import React from "react"
type LoginFormProps = {
  label: string;
  value: string;
  onChange: (newValue: string) => void;
}

const LoginForm = (props: LoginFormProps) => {
  console.log("여기서 " + props.label + "이 리렌더링됨")
  return (
    <div>
      <span>{props.label}</span>
      <div>
        <input value={props.value} onChange={(e) => props.onChange(e.target.value)}/>
      </div>
    </div>
  )
}

export default React.memo(LoginForm);