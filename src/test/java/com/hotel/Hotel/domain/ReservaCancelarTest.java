package com.hotel.Hotel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de Cancelación de Reserva")
class ReservaCancelarTest {

    @Test
    @DisplayName("PRUEBA #4: Al cancelar una reserva, la habitación debe quedar disponible")
    void alCancelarReservaLaHabitacionDebeQuedarDisponible() {
        // Arrange (Preparar) - Crear una reserva confirmada
        Cliente cliente = new Cliente("Ana Torres", "ana@email.com");
        Habitacion habitacion = new HabitacionEstandar("305", 2, 150.0, 2);
        
        RangoFechas periodo = new RangoFechas(
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(8)
        );
        
        Reserva reserva = new Reserva(cliente, habitacion, periodo);
        
        // Confirmar la reserva primero (esto ocupará la habitación)
        reserva.confirmar();
        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado(), "Debe estar confirmada");
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado(), "La habitación debe estar ocupada");

        // Act (Actuar) - Cancelar la reserva
        reserva.cancelar();

        // Assert (Verificar) - La habitación debe quedar disponible
        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado(), 
                "El estado de la reserva debe ser CANCELADA");
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado(), 
                "La habitación debe volver a estar DISPONIBLE");
    }

    @Test
    @DisplayName("PRUEBA #4 (Extra): Se puede cancelar una reserva pendiente")
    void sePuedeCancelarReservaPendiente() {
        // Arrange
        Cliente cliente = new Cliente("Luis Ramírez", "luis@email.com");
        Habitacion habitacion = new HabitacionEstandar("202", 1, 100.0, 1);
        
        RangoFechas periodo = new RangoFechas(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3)
        );
        
        Reserva reserva = new Reserva(cliente, habitacion, periodo);
        
        // Verificar que está pendiente
        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado());

        // Act - Cancelar sin confirmar
        reserva.cancelar();

        // Assert
        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado(), 
                "La habitación debe seguir disponible");
    }
}
