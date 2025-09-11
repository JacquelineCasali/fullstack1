import TaskForm from "../components/TaskForm";
import TaskList from "../components/TaskList";

export default function Home() {
  return (
    <div className="max-w-xl mx-auto mt-10">
      <h1 className="text-2xl font-bold mb-6">Gerenciador de Tarefas</h1>
      <TaskForm onTaskCreated={() => window.location.reload()} />
      <TaskList />
    </div>
  );
}
