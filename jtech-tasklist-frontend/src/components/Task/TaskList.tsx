import { useEffect, useState } from "react";
import api from "../../services/api";
import ConfirmModal from "../Modal/ConfirmModal";
import Title from "../Title/Title";
import Button from "../Button/Button";
import Loading from "../Loading/Loading";
import { getBackendError } from "../../utils/getBackendError";
import capitalizeFirstLetter from "../../utils/capitalizeFirstLetter";

export interface Task {
  id: number;
  title: string;
  description?: string;
  status: "PENDENTE" | "CONCLUIDA";
}

interface Props {
  reloadTrigger: number;
}

export default function TaskList({ reloadTrigger }: Props) {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(false);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editTitle, setEditTitle] = useState("");
  const [editDescription, setEditDescription] = useState("");
  const [confirmOpen, setConfirmOpen] = useState(false);
  const [toDeleteId, setToDeleteId] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  async function loadTasks() {
    setLoading(true);
    setError(null);
    try {
      const res = await api.get<Task[]>("/tasks");
      const ordered = res.data.sort((a, b) => {
        if (a.status === b.status) return a.id - b.id;
        return a.status === "PENDENTE" ? -1 : 1;
      });
      setTasks(ordered);
    } catch (err) {
      console.error(err);
      setError("Erro ao carregar tarefas");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadTasks();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [reloadTrigger]);

  function startEdit(task: Task) {
    setEditingId(task.id);
    setEditTitle(task.title || "");
    setEditDescription(task.description || "");
  }

  function cancelEdit() {
    setEditingId(null);
    setEditTitle("");
    setEditDescription("");
  }

  async function saveEdit(id: number) {
    if (!editTitle.trim()) {
      setError("O título é obrigatório.");
      return;
    }
    setLoading(true);
    setError(null);
    try {
      await api.put(`/tasks/${id}`, {
        title: editTitle.trim(),
        description: editDescription.trim(),
        status: tasks.find((t) => t.id === id)?.status ?? "pendente",
      });
      await loadTasks();
      cancelEdit();
    } catch (err) {
      console.error(err);
      setError(getBackendError(err));
    } finally {
      setLoading(false);
    }
  }

  async function toggleStatus(task: Task) {
    try {
      const newStatus = task.status === "PENDENTE" ? "CONCLUIDA" : "PENDENTE";
      await api.put(`/tasks/${task.id}`, { ...task, status: newStatus });
      await loadTasks();
    } catch (err) {
      console.error(err);
      setError(getBackendError(err));
    }
  }
  function renderStatus(status: Task["status"]) {
    if (status === "CONCLUIDA") {
      return <span className="badge badge-green">Concluída</span>;
    }
    return <span className="badge badge-red">Pendente</span>;
  }
  function confirmDelete(id: number) {
    setToDeleteId(id);
    setConfirmOpen(true);
  }

  async function doDelete() {
    if (toDeleteId == null) return;
    try {
      await api.delete(`/tasks/${toDeleteId}`);
      setConfirmOpen(false);
      setToDeleteId(null);
      await loadTasks();
    } catch (err) {
      console.error(err);
      setError(getBackendError(err));
    }
  }

  return (
    <div className="task-list">
      <Title text={`Lista de Tarefas`} theme="h2" />
      {loading && <Loading type="text" />}

      {error && <div className="error">{error}</div>}
      <ul>
        {tasks.map((task) => (
          <li key={task.id} className="task-row">
            <div className="task-main">
              <input
                type="checkbox"
                checked={task.status === "CONCLUIDA"}
                onChange={() => toggleStatus(task)}
                aria-label={`Marcar ${task.title}`}
              />
              {editingId === task.id ? (
                <div className="edit-area">
                  <input
                    className="input-inline"
                    value={editTitle}
                    onChange={(e) => setEditTitle(e.target.value)}
                  />
                  <textarea
                    className="textarea-inline"
                    value={editDescription}
                    onChange={(e) => setEditDescription(e.target.value)}
                    rows={2}
                  />
                </div>
              ) : (
                <div className="task-info">
                  <div
                    className={`task-title ${
                      task.status === "CONCLUIDA" ? "done" : ""
                    }`}
                  >
                   {capitalizeFirstLetter(task.title)}
                  </div>
                  {task.description && (
                    <div className="task-desc">{capitalizeFirstLetter(task.description)}</div>
                  )}
                  {renderStatus(task.status)}
                </div>
              )}
            </div>

            <div className="task-actions">
              {editingId === task.id ? (
                <>
                  <Button
                    text={loading ? <Loading type="spinner" /> : "Salvar"}
                    theme={"green"}
                    onClick={() => saveEdit(task.id)}
                    disabled={loading}
                  />
                  <Button
                    text={"Cancelar"}
                    theme={"btn"}
                    onClick={cancelEdit}
                  />
                </>
              ) : (
                <>
                  <Button
                    text={"Editar"}
                    theme={"btn"}
                    onClick={() => startEdit(task)}
                  />
                  <Button
                    text={loading ? <Loading type="spinner" /> : "Excluir"}
                    theme={"danger"}
                    onClick={() => confirmDelete(task.id)}
                    disabled={loading}
                  />
                </>
              )}
            </div>
          </li>
        ))}
      </ul>

      <ConfirmModal
        open={confirmOpen}
        title="Apagar tarefa"
        message="Tem certeza que deseja apagar esta tarefa? Esta ação não pode ser desfeita."
        onConfirm={doDelete}
        onCancel={() => {
          setConfirmOpen(false);
          setToDeleteId(null);
        }}
      />
    </div>
  );
}
