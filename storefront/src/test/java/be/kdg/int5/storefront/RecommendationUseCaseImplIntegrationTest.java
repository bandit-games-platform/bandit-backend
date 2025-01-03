package be.kdg.int5.storefront;

import be.kdg.int5.storefront.adapters.out.db.order.OrderJpaEntity;
import be.kdg.int5.storefront.adapters.out.db.order.OrderJpaRepository;
import be.kdg.int5.storefront.adapters.out.db.product.ProductProjectionJpaEntity;
import be.kdg.int5.storefront.adapters.out.db.product.ProductProjectionJpaRepository;
import be.kdg.int5.storefront.adapters.out.python.PythonSystemAdapter;
import be.kdg.int5.storefront.domain.OrderStatus;
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
import java.util.Optional;
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

        // Act
        final ResultActions result = mockMvc.perform(get("/products/recommend")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(existingCustomerId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect((status().isOk()));

        verify(pythonSystemAdapter, times(1)).getRecommendationsForCustomer(any(), any());
    }

    @Test
    void shouldReturnAllProductsIfNoOrdersExist() throws Exception {
        // Arrange
        List<ProductProjectionJpaEntity> productsJpa = List.of(
                new ProductProjectionJpaEntity(UUID.randomUUID(), "TestProduct1", developerId, "Description1", BigDecimal.valueOf(10.00), "http://example.com/image1"),
                new ProductProjectionJpaEntity(UUID.randomUUID(), "TestProduct2", developerId, "Description2", BigDecimal.valueOf(12.00), "http://example.com/image2"),
                new ProductProjectionJpaEntity(UUID.randomUUID(), "TestProduct3", developerId, "Description3", BigDecimal.valueOf(4.00), "http://example.com/image3")
        );

        when(productProjectionJpaRepository.findAll()).thenReturn(productsJpa);
        when(orderJpaRepository.findAllCompletedOrdersByCustomerId(newCustomerId)).thenReturn(List.of());

        // Act
        final ResultActions result = mockMvc.perform(get("/products/recommend")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(existingCustomerId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));

        verify(productProjectionJpaRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnMostPopularProducts() throws Exception {
        // Arrange
        UUID orderedProductId1 = UUID.fromString("a0840b22-67a1-426a-95ac-0a27c601c38e");
        UUID orderedProductId2 = UUID.fromString("43ac7f22-c5ea-42ad-9feb-a3d0c2a92fdd");
        UUID orderedProductId3 = UUID.fromString("ac707843-75fc-44fc-8f54-06cfb5bb47eb");
        UUID orderedProductId4 = UUID.fromString("2f43265c-5a77-416a-ad41-eb37c8561b98");
        UUID orderedProductId5 = UUID.fromString("61f1167d-4154-4cff-9029-179291772ea0");

        ProductProjectionJpaEntity productJpa1 = new ProductProjectionJpaEntity(orderedProductId1, "TestProduct1", developerId, "Description1", BigDecimal.valueOf(10.00), "http://example.com/image1");
        ProductProjectionJpaEntity productJpa2 = new ProductProjectionJpaEntity(orderedProductId2, "TestProduct2", developerId, "Description2", BigDecimal.valueOf(12.00), "http://example.com/image2");
        ProductProjectionJpaEntity productJpa3 = new ProductProjectionJpaEntity(orderedProductId3, "TestProduct3", developerId, "Description3", BigDecimal.valueOf(4.00), "http://example.com/image3");
        ProductProjectionJpaEntity productJpa4 = new ProductProjectionJpaEntity(orderedProductId4, "TestProduct4", developerId, "Description4", BigDecimal.valueOf(8.00), "http://example.com/image4");
        ProductProjectionJpaEntity productJpa5 = new ProductProjectionJpaEntity(orderedProductId5, "TestProduct5", developerId, "Description5", BigDecimal.valueOf(7.00), "http://example.com/image5");

        List<OrderJpaEntity> ordersJpa = List.of(
                new OrderJpaEntity(UUID.randomUUID(), "random_stripe_session_id1", orderedProductId1, existingCustomerId, LocalDateTime.of(2024, 12, 31, 23, 59), LocalDateTime.of(2025, 1, 1, 1, 1), OrderStatus.COMPLETED),
                new OrderJpaEntity(UUID.randomUUID(), "random_stripe_session_id2", orderedProductId2, existingCustomerId, LocalDateTime.of(2024, 12, 31, 23, 59), LocalDateTime.of(2025, 1, 1, 1, 1), OrderStatus.COMPLETED),
                new OrderJpaEntity(UUID.randomUUID(), "random_stripe_session_id3", orderedProductId3, existingCustomerId, LocalDateTime.of(2024, 12, 31, 23, 59), LocalDateTime.of(2025, 1, 1, 1, 1), OrderStatus.COMPLETED),
                new OrderJpaEntity(UUID.randomUUID(), "random_stripe_session_id4", orderedProductId4, existingCustomerId, LocalDateTime.of(2024, 12, 31, 23, 59), LocalDateTime.of(2025, 1, 1, 1, 1), OrderStatus.COMPLETED)
        );

        when(orderJpaRepository.findAllCompleteOrders()).thenReturn(ordersJpa);
        when(productProjectionJpaRepository.findById(orderedProductId1)).thenReturn(Optional.of(productJpa1));
        when(productProjectionJpaRepository.findById(orderedProductId2)).thenReturn(Optional.of(productJpa2));
        when(productProjectionJpaRepository.findById(orderedProductId3)).thenReturn(Optional.of(productJpa3));
        when(productProjectionJpaRepository.findById(orderedProductId4)).thenReturn(Optional.of(productJpa4));
        when(productProjectionJpaRepository.findById(orderedProductId5)).thenReturn(Optional.of(productJpa5));

        // Act
        final ResultActions result = mockMvc.perform(get("/products/trending")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(newCustomerId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));

        verify(productProjectionJpaRepository, times(4)).findById(any());
        verify(productProjectionJpaRepository, times(1)).findById(orderedProductId1);
        verify(productProjectionJpaRepository, times(1)).findById(orderedProductId2);
        verify(productProjectionJpaRepository, times(1)).findById(orderedProductId3);
        verify(productProjectionJpaRepository, times(1)).findById(orderedProductId4);
        verify(productProjectionJpaRepository, times(0)).findById(orderedProductId5);
    }
}