package org.example.pages

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`
import java.time.Duration

class OrderConfirmedPage {
    private val orderIsCompleteText = `$x`("//p[text()='Your order on PrestaShop is complete.']")
    private val paymentByChequeText = `$x`("//li[text()='Payment method: Payments by check']")
    private val logoutBtn = `$`("a.logout")
    private val signInBtn = `$x`("//span[text()='Sign in']")

    fun orderConfirmedPageDisplayed() {
        orderIsCompleteText.scrollTo()
    }

    fun paymentByChequeDisplayed() {
        paymentByChequeText.shouldBe(visible)
    }

    fun clickLogoutBtn() {
        logoutBtn.scrollTo().click()
    }

    fun validateUserIsLoggedOut() {
        signInBtn.shouldBe(visible, Duration.ofSeconds(20))
    }
}
