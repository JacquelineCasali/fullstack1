import Button from "../Button/Button";

interface Props {
  open: boolean;
  title?: string;
  message?: string;
  onConfirm: () => void;
  onCancel: () => void;
}

export default function ConfirmModal({
  open,
  title = "Confirmação",
  message,
  onConfirm,
  onCancel,
}: Props) {
  if (!open) return null;

  return (
    <div className="modal-backdrop" role="dialog" aria-modal="true">
      <div className="modal">
        <h3 className="modal-title">{title}</h3>
        <p className="modal-message">
          {message ?? "Tem certeza que deseja continuar?"}
        </p>
        <div className="modal-actions">
          <Button text={"Cancelar"} theme={"btn"} onClick={onCancel} />
          <Button text={"Sim, Deletar"} theme={"danger"} onClick={onConfirm} />
        </div>
      </div>
    </div>
  );
}
