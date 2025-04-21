import { createContext } from 'react';
import NicknameContext from '../types/NicknameContext';

const NicknameCheckedContext = createContext<NicknameContext | null>(null);

export default NicknameCheckedContext;
