import { createContext } from 'react';
import IdContext from '../types/IdContext';

const IdCheckedContext = createContext<IdContext | null>(null);

export default IdCheckedContext;
