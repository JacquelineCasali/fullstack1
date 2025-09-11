import { AxiosError } from "axios";

interface BackendError {
  erros?: string[];
  mensagem?: string;
}

export function getBackendError(error: unknown): string {
  const err = error as AxiosError<BackendError>;
  return (
    err.response?.data?.erros?.[0] ||
    err.response?.data?.mensagem ||
    "Erro desconhecido ao processar requisição"
  );
}
