package be.kdg.int5.storefront;

import be.kdg.int5.storefront.adapters.out.db.order.OrderJpaEntity;
import be.kdg.int5.storefront.adapters.out.db.order.OrderJpaRepository;
import be.kdg.int5.storefront.adapters.out.db.product.ProductProjectionJpaEntity;
import be.kdg.int5.storefront.adapters.out.db.product.ProductProjectionJpaRepository;
import be.kdg.int5.storefront.adapters.out.python.PythonSystemAdapter;
import be.kdg.int5.storefront.domain.OrderStatus;
import be.kdg.int5.storefront.domain.ProductId;
import be.kdg.int5.storefront.domain.ProductProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RecommendationUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderJpaRepository orderJpaRepository;

    @MockBean
    private ProductProjectionJpaRepository productProjectionJpaRepository;

    @MockBean
    private PythonSystemAdapter pythonSystemAdapter;

    private UUID newCustomerId;
    private UUID existingCustomerId;
    private UUID developerId;

    @BeforeEach
    void setup() {
        newCustomerId = Variables.PLAYER_ONE_ID;
        existingCustomerId = Variables.PLAYER_TWO_ID;
        developerId = Variables.DEVELOPER_ID;
    }

    @Test
    void shouldNotCallRecommenderServiceAndReturnFullListOfProductsForNewCustomer() throws Exception {
        // Arrange
        List<ProductProjectionJpaEntity> productsJpa = List.of(
                new ProductProjectionJpaEntity(UUID.randomUUID(), "TestProduct1", developerId, "Description1", BigDecimal.valueOf(10.00), "http://example.com/image1"),
                new ProductProjectionJpaEntity(UUID.randomUUID(), "TestProduct2", developerId, "Description2", BigDecimal.valueOf(12.00), "http://example.com/image2"),
                new ProductProjectionJpaEntity(UUID.randomUUID(), "TestProduct3", developerId, "Description3", BigDecimal.valueOf(4.00), "http://example.com/image3")
        );

        when(productProjectionJpaRepository.findAll()).thenReturn(productsJpa);

        // Act
        final ResultActions result = mockMvc.perform(get("/products/recommend")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(newCustomerId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].title").value("TestProduct1"))
                .andExpect(jsonPath("$[1].title").value("TestProduct2"))
                .andExpect(jsonPath("$[2].title").value("TestProduct3"));

        verify(pythonSystemAdapter, times(0)).getRecommendationsForCustomer(any(), any());
    }

    @Test
    void shouldCallRecommenderServiceAndReturnFullListOfProductsForCustomerWithOrders() throws Exception {
        // Arrange
        UUID orderedProductId = UUID.fromString("a0840b22-67a1-426a-95ac-0a27c601c38e");

        List<ProductProjectionJpaEntity> productsJpa = List.of(
                new ProductProjectionJpaEntity(orderedProductId, "TestProduct1", developerId, "Description1", BigDecimal.valueOf(10.00), "http://example.com/image1"),
                new ProductProjectionJpaEntity(UUID.randomUUID(), "TestProduct2", developerId, "Description2", BigDecimal.valueOf(12.00), "http://example.com/image2"),
                new ProductProjectionJpaEntity(UUID.randomUUID(), "TestProduct3", developerId, "Description3", BigDecimal.valueOf(4.00), "http://example.com/image3")
        );

        OrderJpaEntity orderJpa = new OrderJpaEntity(UUID.randomUUID(), "random_stripe_session_id", orderedProductId, existingCustomerId, LocalDateTime.of(2024, 12, 31, 23, 59), LocalDateTime.of(2025, 1, 1, 1, 1), OrderStatus.COMPLETED);

        when(productProjectionJpaRepository.findAll()).thenReturn(productsJpa);
        when(orderJpaRepository.findAllCompletedOrdersByCustomerId(existingCustomerId)).thenReturn(List.of(orderJpa));

        // Assert
        final ResultActions result = mockMvc.perform(get("/products/recommend")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(existingCustomerId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        result.andExpect((status().isOk()));

        verify(pythonSystemAdapter, times(1)).getRecommendationsForCustomer(any(), any());
    }



}