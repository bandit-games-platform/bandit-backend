package be.kdg.int5.storefront.port.in;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface CreateStripeSessionUseCase {
    Session createStripeSession(CreateStripeSessionCommand command) throws StripeException;
}
