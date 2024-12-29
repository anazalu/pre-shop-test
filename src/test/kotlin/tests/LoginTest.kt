package tests

import base.BaseTest
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.switchTo
import org.junit.jupiter.api.Test

class LoginTest : BaseTest() {
    @Test
    fun logInAndOutTest() {
        switchTo().frame("framelive")
        homePage.clickSignInBtn()
        loginPage.startRegistration()
        loginPage.completeRegistration("MyFirstName", "MyLastName")
        println(loginPage.email)
        println(loginPage.password)
        homePage.validateUserIsLoggedIn()
        orderConfirmedPage.clickLogoutBtn()
        orderConfirmedPage.validateUserIsLoggedOut()

        homePage.clickSignInBtn()
        loginPage.emailField.shouldBe(visible).clear()
        loginPage.emailField.sendKeys(loginPage.email)
        loginPage.pwdField.shouldBe(visible).clear()
        loginPage.pwdField.sendKeys(loginPage.password)
        println(loginPage.email)
        println(loginPage.password)
        loginPage.clickSignInBtn()
        homePage.validateUserIsLoggedIn()

    }
}