package org.example.pages

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`

public class AddedToCartModal {
    private val continueShoppingBtn = `$x`("//button[text()='Continue shopping']")
    private val proceedToCheckoutBtn = `$x`("//a[text()='Proceed to checkout']")
    val actualSubtotalText = `$`("span.subtotal.value")

    fun clickContinueShopping() {
        continueShoppingBtn.shouldBe(visible).click()
    }

    fun clickProceedToCheckout() {
        proceedToCheckoutBtn.shouldBe(visible).click()
    }
}
