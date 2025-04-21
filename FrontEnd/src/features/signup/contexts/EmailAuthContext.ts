import { createContext } from 'react';
import AuthContext from '../types/AuthContext';

const EmailAuthContext = createContext<AuthContext | null>(null);

export default EmailAuthContext;
