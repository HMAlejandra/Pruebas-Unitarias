package com.hotel.Hotel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas del Dominio de Habitación")
class HabitacionTest {

    @Test
    @DisplayName("PRUEBA #5: Debe lanzar excepción al asignar una habitación ya ocupada")
    void debeLanzarExcepcionAlAsignarHabitacionYaOcupada() {
        // Arrange (Preparar) - Crear una habitación y ocuparla
        Habitacion habitacion = new HabitacionEstandar("101", 2, 120.0, 1);
        
        // Primera asignación - debe funcionar bien
        habitacion.asignarAReserva();
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado(), 
                "La habitación debe estar OCUPADA después de la primera asignación");

        // Act & Assert - Intentar asignar nuevamente debe lanzar excepción
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> habitacion.asignarAReserva(),
                "Debe lanzar IllegalStateException al intentar asignar una habitación ocupada"
        );

        // Verificar el mensaje de error
        assertEquals("La habitación no está disponible", exception.getMessage(),
                "El mensaje de error debe ser claro");
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado(), 
                "La habitación debe permanecer OCUPADA");
    }
}
