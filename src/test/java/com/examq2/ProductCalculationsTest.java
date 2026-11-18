package com.examq2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductCalculationsTest {

     // Verifies if returns 15% of the product price
    @Test
    public void GetVat_WithValidData_ReturnsCorrectValue() {
        // Arrange
        double samplePrice = 3500.0;
        ProductCalculations calculator = new ProductCalculations(samplePrice);
        double expectedVat = 525.0;

        // Act
        double actualVat = calculator.GetVat();

        // Assert
        assertEquals(expectedVat, actualVat, 0.001, "GetVat should return 15% of the product price.");
    }

     // Verifies if returns Price + VAT + Commission

    @Test
    public void GetTotal_WithValidData_ReturnsCorrectValue() {
        // Arrange
        double samplePrice = 3500.0;
        ProductCalculations calculator = new ProductCalculations(samplePrice);
        double expectedTotal = 4200.0;

        // Act
        double actualTotal = calculator.GetTotal();

        // Assert
        assertEquals(expectedTotal, actualTotal, 0.001, "GetTotal should return the sum of price, VAT, and commission.");
    }
}