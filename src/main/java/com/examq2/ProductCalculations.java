package com.examq2;

public class ProductCalculations implements IProductCalculations {

    // Constants
    private static final double VAT_RATE = 0.15;
    private static final double COMMISSION_RATE = 0.05;

    // Instance variable stores the base product price
    private final double productPrice;

     // Constructor initializes the product price

    public ProductCalculations(double productPrice) {
        if (productPrice < 0) {
            throw new IllegalArgumentException("Product price cannot be negative.");
        }
        this.productPrice = productPrice;
    }

     // Calculates 15% VAT on the product price

    @Override
    public double GetVat() {
        return productPrice * VAT_RATE;
    }

     // Calculates 5% Commission on the product price

    @Override
    public double GetCommission() {
        return productPrice * COMMISSION_RATE;
    }

     // Calculates the Total Sale Amount (Price + VAT + Commission)
    @Override
    public double GetTotal() {
        return productPrice + GetVat() + GetCommission();
    }
}
