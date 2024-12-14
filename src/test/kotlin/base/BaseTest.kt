package base

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.closeWebDriver
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

open class BaseTest {

    companion object {
        @JvmStatic
        @BeforeAll
        public fun setUp(): Unit {
            // Configuration.startMaximized = true
            // Configuration.baseUrl = "http://demo.prestashop.com"
            open("http://demo.prestashop.com")
            WebDriverRunner.getWebDriver().manage().window().maximize()
        }
    }
}
