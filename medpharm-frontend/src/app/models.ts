export interface AuthResponse { token: string; username: string; rol: string; }
export interface Medicamento { id: number; codigo: string; nombre: string; stock: number; precioUnitario: number; }
export interface DetalleReceta { medicamentoId: number; medicamento: string; cantidad: number; dosisIndicada: string; }
export interface Receta { id: number; codigoReceta: string; pacienteNombre: string; medico: string; estado: string; fechaEmision: string; detalles: DetalleReceta[]; }
