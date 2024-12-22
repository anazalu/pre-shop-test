package org.example.pages

import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Condition.visible

import kotlin.random.Random

class LoginPage {
    private val startRegistrationBtn = `$`("a[data-link-action='display-register-form']")
    private val firstnameField = `$`("#field-firstname")
    private val lastnameField = `$`("#field-lastname")
    private val emailField = `$`("#field-email")
    private val pwdField = `$`("#field-password")
    private val gdprCheckbox = `$`("input[name='psgdpr']")
    private val privacyCheckbox = `$`("input[name='customer_privacy']")
    private val confirmRegisterBtn = `$`("#customer-form > footer > button")

    fun startRegistration() {
        startRegistrationBtn.shouldBe(visible).click()
    }

    fun completeRegistration(firstName: String, lastName: String) {
        insertFirstname(firstName)
        insertLastname(lastName)
        insertEmail()
        insertPassword()
        agreeToConditions()
        clickConfirmRegistration()
    }

    private fun insertFirstname(firstName: String) {
        firstnameField.shouldBe(visible).clear()
        firstnameField.sendKeys(firstName)
    }
        
    private fun insertLastname(lastName: String) {
        lastnameField.shouldBe(visible).clear()
        lastnameField.sendKeys(lastName)
    }
        
    private fun insertEmail() {
        val randEmail = Random.nextInt(1111, 9999)
        emailField.shouldBe(visible).clear()
        emailField.sendKeys("user$randEmail@example.com")
    }
        
    private fun insertPassword() {
        pwdField.shouldBe(visible).clear()
        pwdField.sendKeys("1userUser!")
    }

    private fun agreeToConditions() {
        gdprCheckbox.scrollTo().click()
        privacyCheckbox.scrollTo().click()
    }

    private fun clickConfirmRegistration() {
        confirmRegisterBtn.shouldBe(visible).click()
    }
}
