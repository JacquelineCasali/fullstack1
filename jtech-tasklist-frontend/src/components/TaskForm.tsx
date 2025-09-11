import { useState } from "react";
import api from "../services/api";

interface TaskFormProps {
  onTaskCreated: () => void;
}

export default function TaskForm({ onTaskCreated }: TaskFormProps) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!title) return;

    await api.post("/tasks", {
      title,
      description,
      status: "pendente",
    });

    setTitle("");
    setDescription("");
    onTaskCreated();
  }

  return (
    <form onSubmit={handleSubmit} className="form">
      <input
        className="input"
        type="text"
        placeholder="Título da tarefa"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <textarea
        className="input"
        placeholder="Descrição"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />
      <button type="submit" className="btn">
        Criar Tarefa
      </button>
    </form>
  );
}
