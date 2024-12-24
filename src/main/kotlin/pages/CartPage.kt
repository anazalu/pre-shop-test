package org.example.pages

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`

class CartPage {
    private val shoppingCartText = `$x`("//h1[text()='Shopping Cart']")
    val totalValueText = `$`(".cart-summary-line.cart-total .value")
    private val proceedToCheckoutBtn = `$x`("//a[text()='Proceed to checkout']")

    fun shoppingCartPageDisplayed() {
        shoppingCartText.shouldBe(visible)
    }

    fun clickProceedToCheckout() {
        proceedToCheckoutBtn.shouldBe(visible).click()
    }
}
