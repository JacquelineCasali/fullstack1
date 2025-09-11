import "./styles.css";

interface TitleProps {
  text: string;
  theme?: "h1" | "h2";
  style?: React.CSSProperties;
}
const Title: React.FC<TitleProps> = ({ text, theme = "h1", style = {} }) => {
  return (
    <h1 className={`title ${theme === "h1" ? "h1" : "h2"}`} style={style}>
      {text}
    </h1>
  );
};

export default Title;