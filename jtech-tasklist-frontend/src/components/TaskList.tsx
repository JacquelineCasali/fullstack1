import { useEffect, useState } from "react";
import api from "../services/api";

interface Task {
  id: number;
  title: string;
  description: string;
  status: string;
}

export default function TaskList() {
  const [tasks, setTasks] = useState<Task[]>([]);

  async function loadTasks() {
    const res = await api.get<Task[]>("/tasks");
    setTasks(res.data);
  }

  async function deleteTask(id: number) {
    await api.delete(`/tasks/${id}`);
    loadTasks();
  }

  async function toggleStatus(task: Task) {
    const newStatus = task.status === "pendente" ? "concluída" : "pendente";
    await api.put(`/tasks/${task.id}`, { ...task, status: newStatus });
    loadTasks();
  }

  useEffect(() => {
    loadTasks();
  }, []);

  return (
    <section>


      <h2 className="h2">Lista de Tarefas</h2>
      <ul className="ul">
        {tasks.map((task) => (
          <li
            key={task.id}
            className="li"
          >
            <div>
              <h3 className={`font-semibold ${task.status === "concluída" ? "line-through text-gray-500" : ""}`}>
                {task.title}
              </h3>
              <p className="text-sm text-gray-600">{task.description}</p>
            </div>
            <div className="flex gap-2">
              <button
                className="px-2 py-1 bg-green-500 text-white rounded"
                onClick={() => toggleStatus(task)}
              >
                {task.status === "pendente" ? "Concluir" : "Reabrir"}
              </button>
              <button
                className="px-2 py-1 bg-red-500 text-white rounded"
                onClick={() => deleteTask(task.id)}
              >
                Excluir
              </button>
            </div>
          </li>
        ))}
      </ul>
       </section>
  );
}
