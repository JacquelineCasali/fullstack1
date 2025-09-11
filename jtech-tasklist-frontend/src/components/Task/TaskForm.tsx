import React, { useState } from "react";
import api from "../../services/api";
import Button from "../Button/Button";
import Loading from "../Loading/Loading";

import { getBackendError } from "../../utils/getBackendError";

interface Props {
  onTaskCreated: () => void;
}

export default function TaskForm({ onTaskCreated }: Props) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!title.trim()) {
      setError("Título é obrigatório");
      return;
    }
    setError("");
    setLoading(true);
    try {
      await api.post("/tasks", {
        title: title.trim(),
        description: description.trim(),
        status: "pendente",
      });
      setTitle("");
      setDescription("");
      onTaskCreated();
    } catch (err) {
      console.error(err);
      setError(getBackendError(err));
    } finally {
      setLoading(false);
    }
  }

  return (
    <form className="task-form" onSubmit={handleSubmit}>
      <div className="form-row">
        <input
          className="input"
          placeholder="Título da tarefa"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <Button
          text={loading ? <Loading type="spinner" /> : "Criar"}
          theme={"primary"}
          type="submit"
          disabled={loading}
        />
      </div>
      <textarea
        className="textarea"
        placeholder="Descrição (opcional)"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        rows={3}
      />
      {error && <div className="error">{error}</div>}
    </form>
  );
}
