package org.example.pages

import com.codeborne.selenide.Condition.clickable
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`

class LoginPage {
    private val startRegistrationBtn = `$`("a[data-link-action='display-register-form']")
    private val firstnameField = `$`("#field-firstname")
    private val lastnameField = `$`("#field-lastname")
    val emailField = `$`("#field-email")
    val pwdField = `$`("#field-password")
    private val gdprCheckbox = `$`("input[name='psgdpr']")
    private val privacyCheckbox = `$`("input[name='customer_privacy']")
    private val confirmRegisterBtn = `$`("#customer-form > footer > button")
    private val signInButton = `$`("#submit-login")
    private val randNumList = (1111..9999).shuffled().take(2)
    private val randNum1 = randNumList[0]
    private val randNum2 = randNumList[1]
    val email = "user$randNum1@example.com"
    val password = "!xf#Tsw!$randNum2"
    private val authFailedAlert = `$x`("//li[text()='Authentication failed.']")


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

    fun clickSignInBtn() {
        signInButton.shouldBe(clickable).click()
    }

    fun validateAuthFailed() {
        authFailedAlert.shouldBe(visible)
    }
}
