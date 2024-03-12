package portfolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// START:class
class ANonEmptyPortfolio {
    Portfolio portfolio = new Portfolio();
    int initialSize;

    @BeforeEach
    void purchaseASymbol() {
        portfolio.purchase("LSFT", 20);
        initialSize = portfolio.size();
    }

    @Nested
    class WhenPurchasingAnotherSymbol {
        @BeforeEach
        void purchaseAnotherSymbol() {
            portfolio.purchase("AAPL", 10);
        }

        @Test
        void increasesSize() {
            assertEquals(initialSize + 1, portfolio.size());
        }
    }
}
// END:class
