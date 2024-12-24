package org.example.pages

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Condition.clickable
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`

class DeliveryPage {
    val finalTotal = `$`(".cart-summary-line.cart-total .value")
    private val countryNameField = `$`("#field-id_country")
    private val address1Field = `$`("#field-address1")
    private val postCodeField = `$`("#field-postcode")
    private val cityField = `$`("#field-city")
    private val confirmAddressBtn = `$`("button[name='confirm-addresses']")
    private val confDelOptBtn = `$`("button[name='confirmDeliveryOption']")
    private val payByChequeBtn = `$x`("//span[text()='Pay by Check']")
    private val termsCheckbox = `$`("input[name='conditions_to_approve[terms-and-conditions]']")
    private val confirmPaymentBtn = `$`("#payment-confirmation .btn.btn-primary.center-block")

    fun fillInDeliveryInfo(country: String, streetAddress: String, postcode: String, city: String) {
        countryNameField.shouldBe(visible).selectOption(country)
//    `$`("#field-id_country option[value='8']").shouldBe(Condition.selected)
        address1Field.scrollTo().setValue(streetAddress)
        postCodeField.scrollTo().setValue(postcode)
        cityField.scrollTo().setValue(city)
        confirmAddressBtn.scrollTo().shouldBe(visible).click()
        confDelOptBtn.scrollTo().shouldBe(visible).click()
        payByChequeBtn.shouldBe(visible).click()
        termsCheckbox.shouldBe(clickable).click()
        confirmPaymentBtn.scrollTo().shouldBe(clickable).click()
    }
}
