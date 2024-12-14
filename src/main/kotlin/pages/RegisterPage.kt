package org.example.pages

import java.time.Duration

import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`
import com.codeborne.selenide.Selenide.`$$`

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Condition

import kotlin.random.Random

public class RegisterPage {
    val firstnameField = `$`("#field-firstname")
    val lastnameField = `$`("#field-lastname")
    val emailField = `$`("#field-email")    
    val randEmail = Random.nextInt(0, 9999)
    val pwdField = `$`("#field-password")
    val gdprCheckbox = `$`("input[name='psgdpr']")
    val privacyCheckbox = `$`("input[name='customer_privacy']")

    fun insertFirstname(firstName: String) {
        firstnameField.shouldBe(visible).clear()
        firstnameField.sendKeys(firstName)
    }
        
    fun insertLastname(lastName: String) {
        lastnameField.shouldBe(visible).clear()
        lastnameField.sendKeys(lastName)
    }
        
    fun insertEmail() {
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
}
