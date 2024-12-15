package org.example.pages

import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Condition.visible

import kotlin.random.Random

public class LoginPage {
    val startRegistrationBtn = `$`("a[data-link-action='display-register-form']")
    val firstnameField = `$`("#field-firstname")
    val lastnameField = `$`("#field-lastname")
    val emailField = `$`("#field-email")
    val pwdField = `$`("#field-password")
    val gdprCheckbox = `$`("input[name='psgdpr']")
    val privacyCheckbox = `$`("input[name='customer_privacy']")
    val confirmRegisterBtn = `$`("#customer-form > footer > button")

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

    fun insertFirstname(firstName: String) {
        firstnameField.shouldBe(visible).clear()
        firstnameField.sendKeys(firstName)
    }
        
    fun insertLastname(lastName: String) {
        lastnameField.shouldBe(visible).clear()
        lastnameField.sendKeys(lastName)
    }
        
    fun insertEmail() {
        val randEmail = Random.nextInt(1111, 9999)
        emailField.shouldBe(visible).clear()
        emailField.sendKeys("user$randEmail@example.com")
    }
        
    fun insertPassword() {
        pwdField.shouldBe(visible).clear()
        pwdField.sendKeys("1userUser!")
    }

    fun agreeToConditions() {
        gdprCheckbox.scrollTo().click()
        privacyCheckbox.scrollTo().click()
    }

    fun clickConfirmRegistration() {
        confirmRegisterBtn.shouldBe(visible).click()
    }
}
