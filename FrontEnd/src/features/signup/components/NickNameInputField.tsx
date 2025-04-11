import { useDispatch, useSelector } from 'react-redux';
import DuplicateCheckInputField from '../../../components/DuplicateCheckInputField';
import { setNickname } from '../../../store/slices/signUpSlice';
import { RootState } from '../../../store/store';

const NickNameInputField = () => {
  const dispatch = useDispatch();
  const nickname = useSelector((state: RootState) => state.signUp.nickname);

  const handleCheckNickname = () => {
    if (!nickname) {
      alert('닉네임을 입력하세요.');
      return;
    }
  };

  return (
    <>
      <DuplicateCheckInputField
        label="NickName"
        value={nickname}
        onChange={(e) => dispatch(setNickname(e.target.value))}
        placeholder="NickName"
        buttonText="중복체크"
        onButtonClick={handleCheckNickname}
      />
    </>
  );
};

export default NickNameInputField;
