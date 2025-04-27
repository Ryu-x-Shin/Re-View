import { FormProvider, SubmitHandler, useForm } from 'react-hook-form';
import EmailAuthInputField from './EmailAuthInputField';
import IdInputField from './IdInputField';
import NickNameInputField from './NickNameInputField';
import PasswordConfirmInputField from './PasswordConfirmInputField';
import PasswordInputField from './PasswordInputField';
import styles from './SignUpForm.module.scss';
import SubmitButton from './SubmitButton';
import EmailAuthContext from '../contexts/EmailAuthContext';
import AuthState from '../types/AuthState';
import { useMemo, useReducer } from 'react';
import NicknameCheckedState from '../types/NicknameCheckedState';
import IdCheckedState from '../types/IdCheckedState';
import signUpReducer from '../hooks/AuthReducer';
import IdCheckedContext from '../contexts/IdCheckedContext';
import NicknameCheckedContext from '../contexts/NicknameCheckedContext';
import idReducer from '../hooks/IdReducer';
import nicknameReducer from '../hooks/NicknameReducer';
import FormValue from '../types/FormValue';

const initialFormValue: FormValue = {
  email: '',
  emailCode: '',
  id: '',
  nickname: '',
  password: '',
  passwordConfirm: '',
};

const initialAuthStateValue: AuthState = {
  // isEmailCodeSent: false,
  isEmailVerified: false,
};

const initialNicknameCheckedValue: NicknameCheckedState = {
  isNicknameChecked: false,
};

const initialIdCheckedStateValue: IdCheckedState = {
  isIdChecked: false,
};

const SignUpForm = () => {
  const method = useForm<FormValue>({
    defaultValues: initialFormValue,
  });

  const [authState, authDispatch] = useReducer(
    signUpReducer,
    initialAuthStateValue,
  );

  const [idState, idDispatch] = useReducer(
    idReducer,
    initialIdCheckedStateValue,
  );

  const [nicknameState, nicknameDispatch] = useReducer(
    nicknameReducer,
    initialNicknameCheckedValue,
  );

  const emailAuthContextValue = useMemo(
    () => ({
      ...authState,
      dispatch: authDispatch,
    }),
    [authState],
  );

  const idContextValue = useMemo(
    () => ({
      ...idState,
      dispatch: idDispatch,
    }),
    [idState],
  );

  const nicknameContextValue = useMemo(() => {
    console.log('닉네임 context도 변경됨');
    return {
      ...nicknameState,
      dispatch: nicknameDispatch,
    };
  }, [nicknameState]);

  const onSubmit: SubmitHandler<FormValue> = (data) => console.log(data);
  console.log('SignUpForm 리렌더링');
  return (
    <>
      <p className={`${styles.label}`}>회원가입</p>
      <FormProvider {...method}>
        <form className={styles.form} onSubmit={method.handleSubmit(onSubmit)}>
          <EmailAuthContext.Provider value={emailAuthContextValue}>
            <EmailAuthInputField />
          </EmailAuthContext.Provider>
          <IdCheckedContext.Provider value={idContextValue}>
            <IdInputField />
          </IdCheckedContext.Provider>
          <PasswordInputField />
          <PasswordConfirmInputField />
          <NicknameCheckedContext.Provider value={nicknameContextValue}>
            <NickNameInputField />
          </NicknameCheckedContext.Provider>
          <SubmitButton />
        </form>
      </FormProvider>
    </>
  );
};

export default SignUpForm;
