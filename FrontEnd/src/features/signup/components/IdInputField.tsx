import { useDispatch, useSelector } from 'react-redux';
import DuplicateCheckInputField from '../../../components/DuplicateCheckInputField';
import { setId } from '../../../store/slices/signUpSlice';
import { RootState } from '../../../store/store';

const IdInputField = () => {
  const dispatch = useDispatch();
  const id = useSelector((state: RootState) => state.signUp.id);

  const handleCheckId = () => {
    if (!id) {
      alert('사용하실 아이디를 입력하세요.');
      return;
    }
  };

  return (
    <>
      <DuplicateCheckInputField
        label="ID"
        value={id}
        onChange={(e) => dispatch(setId(e.target.value))}
        placeholder="ID"
        buttonText="중복체크"
        onButtonClick={handleCheckId}
      />
    </>
  );
};

export default IdInputField;
