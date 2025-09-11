import { useState } from "react";
import TaskForm from "../components/Task/TaskForm";
import TaskList from "../components/Task/TaskList";
import Title from "../components/Title/Title";
import { Head } from "../components/Head/Head";


export default function Home() {
  const [reloadTrigger, setReloadTrigger] = useState(0);

  function onTaskCreated() {
    setReloadTrigger((s) => s + 1);
  }

  return (
    <>
  
  <Head title={"Sistema de Tarefas"} />


    <main className="container">
    <Title text={`Gerenciador de Tarefas`}
        theme="h1"/>
    
      <TaskForm onTaskCreated={onTaskCreated} />
      <TaskList reloadTrigger={reloadTrigger} />
    </main>
      </>
  );
}
