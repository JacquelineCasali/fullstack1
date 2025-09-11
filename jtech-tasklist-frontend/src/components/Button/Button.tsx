import React from 'react';
import './Button.css';

interface ButtonProps {
  text: React.ReactNode;
  theme?: string;
  onClick?: () => void;
  type?: 'button' | 'submit' | 'reset';
  disabled?: boolean;
  style?: React.CSSProperties;
}

const Button: React.FC<ButtonProps> = ({
  text,
  theme = "primary",
  onClick = () => {},
  type = "button",
  disabled = false,
  style = {},
}) => {
  return (
    <button
      className={`btn ${theme}`}
      onClick={onClick}
      type={type}
      disabled={disabled}
      style={style}
    >
      {text}
    </button>
  );
};

export default Button;
