package base

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.fail
import java.math.BigDecimal

open class BaseTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
            // Configuration.startMaximized = true
            // Configuration.baseUrl = "http://demo.prestashop.com"
            open("http://demo.prestashop.com")
            WebDriverRunner.getWebDriver().manage().window().maximize()
        }

        fun assertInRange(actual: BigDecimal, min: BigDecimal, max: BigDecimal, message: String) {
            if (actual < min || actual > max) {
                fail("$message Actual value is $actual, which is not between $min and $max.")
            }
        }
    }
}
