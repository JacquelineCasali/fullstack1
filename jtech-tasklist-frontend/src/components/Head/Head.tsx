import { useEffect } from "react";

interface HeadProps {
  title: string;
  description?: string;
}

export function Head({ title, description = "" }: HeadProps): null {
  useEffect(() => {
    document.title = `${title}`;
    document.querySelector("meta[name='description']")?.setAttribute("content", description);
  }, [title, description]);

  return null;
}