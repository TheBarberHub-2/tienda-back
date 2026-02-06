package com.fpmislata.daw.tienda.controller.webModel.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservaInsertRequest(
                Long clienteId,
                Long peluqueriaId,
                List<Long> productoIds,
                LocalDate fechaReserva,
                LocalTime horaInicio) {

}
