package org.example.pages

import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Condition.visible

class LoginPage {
    private val startRegistrationBtn = `$`("a[data-link-action='display-register-form']")
    private val firstnameField = `$`("#field-firstname")
    private val lastnameField = `$`("#field-lastname")
    private val emailField = `$`("#field-email")
    private val pwdField = `$`("#field-password")
    private val gdprCheckbox = `$`("input[name='psgdpr']")
    private val privacyCheckbox = `$`("input[name='customer_privacy']")
    private val confirmRegisterBtn = `$`("#customer-form > footer > button")
    private val randNumList = (1111..9999).shuffled().take(2)
    private val randNum1 = randNumList[0]
    private val randNum2 = randNumList[1]
    val email = "user$randNum1@example.com"
    val password = "!xf#Tsw!$randNum2"

    fun startRegistration() {
        startRegistrationBtn.shouldBe(visible).click()
    }

    fun completeRegistration(firstName: String, lastName: String) {
        firstnameField.shouldBe(visible).clear()
        firstnameField.sendKeys(firstName)

        lastnameField.shouldBe(visible).clear()
        lastnameField.sendKeys(lastName)

        emailField.shouldBe(visible).clear()
        emailField.sendKeys(email)

        pwdField.shouldBe(visible).clear()
        pwdField.sendKeys(password)

        gdprCheckbox.scrollTo().click()
        privacyCheckbox.scrollTo().click()

        confirmRegisterBtn.shouldBe(visible).click()
    }
}
