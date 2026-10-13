package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.mapper.ClienteMapper;
import com.hotel.Hotel.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del Servicio de Cliente")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("PRUEBA #1: Debe crear un cliente exitosamente")
    void debeCrearClienteExitosamente() {
        // Arrange (Preparar) - Configurar datos de prueba
        CrearClienteRequest request = new CrearClienteRequest("Juan Pérez", "juan@email.com");
        Cliente clienteGuardado = new Cliente("Juan Pérez", "juan@email.com");
        UUID clienteId = UUID.randomUUID();
        ClienteResponse responseEsperado = new ClienteResponse(
                clienteId, 
                "Juan Pérez", 
                "juan@email.com", 
                true, 
                0
        );

        // Configurar el comportamiento de los mocks
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteGuardado);
        when(clienteMapper.toResponse(clienteGuardado)).thenReturn(responseEsperado);

        // Act (Actuar) - Ejecutar el método que queremos probar
        ClienteResponse resultado = clienteService.crear(request);

        // Assert (Verificar) - Comprobar que los resultados son los esperados
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals("Juan Pérez", resultado.nombre(), "El nombre debe coincidir");
        assertEquals("juan@email.com", resultado.email(), "El email debe coincidir");
        assertTrue(resultado.activo(), "El cliente debe estar activo");
        assertEquals(0, resultado.penalizaciones(), "Debe tener 0 penalizaciones al inicio");

        // Verificar que se llamaron los métodos correctos
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(clienteMapper, times(1)).toResponse(any(Cliente.class));
    }
}
