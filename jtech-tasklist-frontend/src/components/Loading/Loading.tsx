import React from 'react';
import { BiLoaderAlt } from 'react-icons/bi';
import './Loading.css';

interface LoadingProps {
  type?: 'spinner' | 'text';
  mensagem?: string;
}

const Loading: React.FC<LoadingProps> = ({ type = 'spinner', mensagem = 'Carregando...' }) => {
  return (
    <div className="loading-container">
      {type === 'spinner' ? (
        <BiLoaderAlt className="spinner" />
      ) : (
        <strong className="Carregando">{mensagem}</strong>
      )}
    </div>
  );
};

export default Loading;
