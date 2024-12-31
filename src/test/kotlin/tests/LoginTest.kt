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
        homePage.validateUserIsLoggedIn()
        orderConfirmedPage.clickLogoutBtn()
        orderConfirmedPage.validateUserIsLoggedOut()

        homePage.clickSignInBtn()
        loginPage.emailField.shouldBe(visible).clear()
        loginPage.emailField.sendKeys(loginPage.email)

        loginPage.pwdField.shouldBe(visible).clear()
        loginPage.pwdField.sendKeys("incorrectPassword")
        loginPage.clickSignInBtn()
        orderConfirmedPage.validateUserIsLoggedOut()
        loginPage.validateAuthFailed()

        loginPage.pwdField.shouldBe(visible).clear()
        loginPage.pwdField.sendKeys(loginPage.password)
        loginPage.clickSignInBtn()
        homePage.validateUserIsLoggedIn()

    }
}