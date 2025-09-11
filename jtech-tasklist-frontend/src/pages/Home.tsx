import { useState } from "react";
import TaskForm from "../components/TaskForm";
import TaskList from "../components/TaskList";

export default function Home() {
  const [reloadTrigger, setReloadTrigger] = useState(0);

  function onTaskCreated() {
    setReloadTrigger((s) => s + 1);
  }

  return (
    <div className="container">
      <h1 className="title">Gerenciador de Tarefas</h1>
      <TaskForm onTaskCreated={onTaskCreated} />
      <TaskList reloadTrigger={reloadTrigger} />
    </div>
  );
}
