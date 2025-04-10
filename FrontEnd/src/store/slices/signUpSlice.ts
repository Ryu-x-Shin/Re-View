// /src/store/signUpSlice.ts
import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface SignUpState {
  // 이메일 관련
  email: string;
  emailCode: string;
  isEmailCodeSent: boolean;
  isEmailVerified: boolean;
  // 타이머
  timerActive: boolean;
  timerSeconds: number; // 초 단위
  // ID 관련
  id: string;
  isIdChecked: boolean;
  // 비밀번호
  password: string;
  passwordConfirm: string;
  // 닉네임 관련
  nickname: string;
  isNicknameChecked: boolean;
  // 상태
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
}

const initialState: SignUpState = {
  email: '',
  emailCode: '',
  isEmailCodeSent: false,
  isEmailVerified: false,
  timerActive: false,
  timerSeconds: 330, // 5분 30초
  id: '',
  isIdChecked: false,
  password: '',
  passwordConfirm: '',
  nickname: '',
  isNicknameChecked: false,
  status: 'idle',
  error: null,
};

const signUpSlice = createSlice({
  name: 'signUp',
  initialState,
  reducers: {
    setEmail: (state, action: PayloadAction<string>) => {
      state.email = action.payload;
    },
    setEmailCode: (state, action: PayloadAction<string>) => {
      state.emailCode = action.payload;
    },
    setTimerActive: (state, action: PayloadAction<boolean>) => {
      state.timerActive = action.payload;
    },
    setTimerSeconds: (state, action: PayloadAction<number>) => {
      state.timerSeconds = action.payload;
    },
    resetTimer: (state) => {
      state.timerSeconds = 330;
      state.timerActive = false;
    },
    setId: (state, action: PayloadAction<string>) => {
      state.id = action.payload;
    },
    setPassword: (state, action: PayloadAction<string>) => {
      state.password = action.payload;
    },
    setPasswordConfirm: (state, action: PayloadAction<string>) => {
      state.passwordConfirm = action.payload;
    },
    setNickname: (state, action: PayloadAction<string>) => {
      state.nickname = action.payload;
    },
  },
});

export const {
  setEmail,
  setEmailCode,
  setTimerActive,
  setTimerSeconds,
  resetTimer,
  setId,
  setPassword,
  setPasswordConfirm,
  setNickname,
} = signUpSlice.actions;

export default signUpSlice.reducer;
