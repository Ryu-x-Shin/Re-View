import React, { useContext, useEffect, useState } from 'react';
import styles from './EmailAuthInputField.module.scss';
import { useFormContext } from 'react-hook-form';
import EmailAuthContext from '../contexts/EmailAuthContext';
import { motion, AnimatePresence } from 'framer-motion';
import FormValue from '../types/FormValue';

interface Timer {
  isEmailCodeSent: boolean;
  // 타이머
  timerActive: boolean;
  timerSeconds: number; // 초 단위
  // 타이머 돌리기 위해 등록한 Interval의 id
  intervalId: NodeJS.Timeout | null;
}

const EmailAuthInputField = () => {
  const {
    reset,
    register,
    getValues,
    formState: { errors },
    trigger,
  } = useFormContext<FormValue>();

  const authContext = useContext(EmailAuthContext)!;

  const [timer, setTimer] = useState<Timer>({
    isEmailCodeSent: false,
    timerActive: false,
    timerSeconds: 330,
    intervalId: null,
  });

  const authTimer = () => {
    if (!timer.isEmailCodeSent) {
      const id = setInterval(() => {
        setTimer((before) => {
          if (before.timerSeconds == 0) {
            clearInterval(before.intervalId!);
            return {
              ...before,
              isEmailCodeSent: false,
              intervalId: null,
            };
          } else {
            return {
              ...before,
              timerSeconds: before.timerSeconds - 1,
            };
          }
        });
        // authContext.dispatch({ type: 'auth/tick' });
      }, 1000);

      setTimer((before) => {
        return {
          ...before,
          isEmailCodeSent: true,
          intervalId: id,
          timerSeconds: 330,
        };
      });
    }
  };

  // 타이머가 작동해서 timerSeconds가 0이 됐을 때 timer 세는 것을 멈추게 하기 위한 사이드 이펙트
  useEffect(() => {
    if (timer.intervalId && timer.timerSeconds == 0) {
      // authContext.dispatch({ type: 'auth/timeOver' });
      reset({
        ...getValues(),
        emailCode: '',
      });
    }
  }, [timer.timerSeconds, timer.intervalId, getValues, reset]);

  console.log('EmailAuthInputField 리렌더링');
  return (
    <div className={styles['field']}>
      <label htmlFor="email">Email</label>
      <div className={styles['email']}>
        <div>
          <input
            id="email"
            className={`${errors.email ? styles['email__input-error'] : styles['email__input']}`}
            disabled={authContext.isEmailVerified}
            // type="email"
            placeholder="이메일을 입력해주세요"
            {...register('email', {
              required: '이메일을 입력해주세요.',
              pattern: {
                value: /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
                message: '유효한 이메일 형식이 아닙니다.',
              },
            })}
          />
          {errors.email && (
            <p className={styles['error']}>{errors.email.message}</p>
          )}
        </div>
        <button
          className={`${styles['email__btn']}`}
          type="button"
          disabled={authContext.isEmailVerified}
          onClick={async () => {
            const isEmailValid = await trigger('email');
            if (isEmailValid) {
            }
            authTimer();
          }}
        >
          인증 코드 전송
        </button>
      </div>
      {/*  Animation을 위한 AnimationPresence */}
      <AnimatePresence mode="wait">
        {timer.isEmailCodeSent && (
          // 모션 관련 영역을 위한 부모 motion.div => 여기서 공간을 확장을 하고
          <motion.div
            // layout 속성은 컴포넌트가 나오고 사라질 때 주변 레이아웃 변화를 부드럽게 처리해주는 것
            layout
            initial={{ height: 0 }}
            animate={{ height: 'auto' }}
            exit={{ height: 0, transition: { duration: 0.4 } }}
            transition={{ duration: 0.4, ease: 'easeInOut' }}
            style={{ overflow: 'hidden' }} // 높이 transition 시 내용 잘림 방지
          >
            {/* 위에서 내려오는 것 관련 애니메이션을 위한 자식 motion.div
            부모 motion.div가 확보한 공간 아래에서 내려온다. */}
            <motion.div
              className={styles['auth-code']}
              initial={{ opacity: 0, y: -30 }} // 살짝 위에서
              animate={{ opacity: 1, y: 0, height: 'auto' }} // 아래로 내려오며 보이기
              exit={{
                opacity: 0,
                y: -30,
                height: 0,
                transition: { duration: 0.4 },
              }} // 사라질 때 다시 위로
              transition={{ duration: 0.4 }}
            >
              <div>
                <input
                  className={
                    errors.emailCode
                      ? styles['auth-code__input-error']
                      : styles['auth-code__input']
                  }
                  disabled={authContext.isEmailVerified}
                  placeholder="인증 번호를 입력해주세요"
                  {...register('emailCode', {
                    required: '인증을 진행해주세요.',
                  })}
                />
                {errors.emailCode && (
                  <p className={styles['error']}>{errors.emailCode.message}</p>
                )}
              </div>
              <button
                className={`${styles['auth-code__btn']}`}
                type="button"
                disabled={authContext.isEmailVerified}
                onClick={async () => {
                  // const isEmailCodeVaild = await trigger('emailCode');
                  await trigger('emailCode');
                }}
              >
                인증
              </button>

              <span className={`${styles['auth-code__timer']}`}>
                {String(Math.floor(timer.timerSeconds / 60)).padStart(2, '0')}:
                {String(timer.timerSeconds % 60).padStart(2, '0')}
              </span>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
};

export default React.memo(EmailAuthInputField);
