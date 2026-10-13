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

    @Test
    @DisplayName("PRUEBA #5 (Extra): Una habitación disponible puede ser asignada")
    void habitacionDisponiblePuedeSerAsignada() {
        // Arrange
        Habitacion habitacion = new HabitacionEstandar("205", 2, 180.0, 2);
        
        // Verificar estado inicial
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado());

        // Act - Asignar la habitación
        habitacion.asignarAReserva();

        // Assert
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado(),
                "Debe cambiar a OCUPADA después de asignar");
    }

    @Test
    @DisplayName("PRUEBA #5 (Extra): Una habitación puede ser habilitada después de ocupada")
    void habitacionPuedeSerHabilitadaDespuesDeOcupada() {
        // Arrange
        Habitacion habitacion = new HabitacionEstandar("310", 3, 200.0, 2);
        habitacion.asignarAReserva(); // Ocuparla primero
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado());

        // Act - Habilitar la habitación
        habitacion.habilitar();

        // Assert
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado(),
                "Debe volver a estar DISPONIBLE después de habilitar");
    }
}
