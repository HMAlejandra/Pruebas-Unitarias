package com.hotel.Hotel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas del Objeto de Valor RangoFechas")
class RangoFechasTest {

    @Test
    @DisplayName("PRUEBA #3: Debe calcular correctamente los días de estadía")
    void debeCalcularDiasCorrectamente() {
        // Arrange (Preparar) - Crear un rango de 5 días completos
        LocalDateTime inicio = LocalDateTime.of(2024, 10, 15, 14, 0); // 15 de octubre 14:00
        LocalDateTime fin = LocalDateTime.of(2024, 10, 20, 14, 0);    // 20 de octubre 14:00 (5 días después)
        
        // Act (Actuar) - Crear el rango de fechas
        RangoFechas rango = new RangoFechas(inicio, fin);

        // Assert (Verificar) - Debe calcular 5 días completos
        assertEquals(5, rango.getDias(), "Debe calcular 5 días completos de diferencia");
        assertEquals(inicio, rango.fechaInicio(), "La fecha de inicio debe coincidir");
        assertEquals(fin, rango.fechaFin(), "La fecha de fin debe coincidir");
    }

    @Test
    @DisplayName("PRUEBA #3 (Validación): Debe lanzar excepción si fecha fin es antes que fecha inicio")
    void debeLanzarExcepcionSiFechaFinEsAnterior() {
        // Arrange - Fechas invertidas (ERROR intencional)
        LocalDateTime inicio = LocalDateTime.of(2024, 10, 20, 14, 0);
        LocalDateTime fin = LocalDateTime.of(2024, 10, 15, 12, 0);
        
        // Act & Assert - Debe lanzar excepción
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new RangoFechas(inicio, fin),
            "Debe lanzar excepción cuando la fecha de fin es anterior al inicio"
        );
        
        assertTrue(exception.getMessage().contains("La fecha fin no puede ser anterior a la de inicio"),
                "El mensaje de error debe ser descriptivo");
    }
}
