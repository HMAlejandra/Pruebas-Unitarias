package com.hotel.Hotel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas del Dominio de Cliente")
class ClienteTest {

    @Test
    @DisplayName("PRUEBA #2: Cliente debe quedar inactivo después de 3 penalizaciones")
    void debeQuedarInactivoDespuesDe3Penalizaciones() {
        // Arrange (Preparar) - Crear un cliente activo
        Cliente cliente = new Cliente("María López", "maria@email.com");
        
        // Verificar estado inicial
        assertTrue(cliente.isActivo(), "El cliente debe estar activo al inicio");
        assertEquals(0, cliente.getPenalizaciones(), "Debe empezar con 0 penalizaciones");

        // Act (Actuar) - Aplicar penalizaciones
        cliente.registrarPenalizacion(); // Primera penalización
        assertEquals(1, cliente.getPenalizaciones(), "Debe tener 1 penalización");
        assertTrue(cliente.isActivo(), "Debe seguir activo con 1 penalización");

        cliente.registrarPenalizacion(); // Segunda penalización
        assertEquals(2, cliente.getPenalizaciones(), "Debe tener 2 penalizaciones");
        assertTrue(cliente.isActivo(), "Debe seguir activo con 2 penalizaciones");

        cliente.registrarPenalizacion(); // Tercera penalización
        
        // Assert (Verificar) - Debe quedar inactivo
        assertEquals(3, cliente.getPenalizaciones(), "Debe tener 3 penalizaciones");
        assertFalse(cliente.isActivo(), "El cliente debe quedar INACTIVO después de 3 penalizaciones");
        assertFalse(cliente.puedeRealizarReservas(), "No debe poder realizar reservas");
    }

    @Test
    @DisplayName("PRUEBA #2 (Extra): Cliente puede ser reactivado")
    void clientePuedeSerReactivado() {
        // Arrange
        Cliente cliente = new Cliente("Pedro Sánchez", "pedro@email.com");
        
        // Penalizar 3 veces para que quede inactivo
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        assertFalse(cliente.isActivo(), "Debe estar inactivo");

        // Act - Reactivar cliente
        cliente.reactivar();

        // Assert
        assertTrue(cliente.isActivo(), "Debe estar activo después de reactivar");
        assertEquals(0, cliente.getPenalizaciones(), "Las penalizaciones deben reiniciarse a 0");
        assertTrue(cliente.puedeRealizarReservas(), "Debe poder realizar reservas nuevamente");
    }
}
