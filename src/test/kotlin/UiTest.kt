import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.*
import org.openqa.selenium.By
import java.time.Duration
import org.openqa.selenium.interactions.Actions

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UiTest {

    @BeforeAll
    fun setUp() {
        open("http://demo.prestashop.com")
    }

    @Test
    fun testEntireFlow() {
        Assertions.assertTrue(title() == "PrestaShop Live Demo", "The title does not match")
        `$`("#buttons > a.btn.btn-download").shouldBe(visible)
        switchTo().frame("framelive")
        `$`("span.hidden-sm-down").shouldHave(text("Sign in"), Duration.ofSeconds(50)).click()
        `$`("#content > div > a").shouldBe(visible).click()
        `$`("#field-firstname").shouldBe(visible).clear()
        `$`("#field-firstname").sendKeys("Nome")
        `$`("#field-lastname").shouldBe(visible).clear()
        `$`("#field-lastname").sendKeys("Cognome")
        `$`("#field-email").shouldBe(visible).clear()
        `$`("#field-email").sendKeys("user@example.com")
        `$`("#field-password").shouldBe(visible).clear()
        `$`("#field-password").shouldBe(visible).clear()
        `$`("#field-password").sendKeys("1userUser!")
        `$`("input[name='psgdpr'").scrollTo().click()
        `$`("input[name='customer_privacy'").scrollTo().click()
        `$`("#customer-form > footer > button").shouldBe(visible).click()
        `$`("a.logout.hidden-sm-down").shouldBe(visible, Duration.ofSeconds(20))
        `$`("a.dropdown-item[href*='/6-accessories']").shouldBe(visible).click()
        `$x`("//a[text()='Home Accessories']").shouldBe(visible).click()
        `$`("p.h6.facet-title.hidden-sm-down").shouldBe(visible)

        val sliderParent = `$`(".faceted-slider")
        val leftSlider = sliderParent.find(By.xpath(".//a[contains(@class, 'ui-slider-handle')][1]")).scrollTo()
        val rightSlider = sliderParent.find(By.xpath(".//a[contains(@class, 'ui-slider-handle')][2]")).scrollTo()
        val sliderWidth = sliderParent.find(".ui-slider").size.width
        // WIP - retrieve min and max from attributes
        val totalRange = 42 - 14
        val offsetPerUnit = sliderWidth / totalRange
        val leftOffset = ((18 - 14) * offsetPerUnit).toInt()
        val rightOffset = ((23 - 42) * offsetPerUnit).toInt()
        val actions = Actions(WebDriverRunner.getWebDriver())
        actions.clickAndHold(leftSlider).moveByOffset(leftOffset, 0).release().perform()
        `$`("p.h6.facet-title.hidden-sm-down").shouldBe(visible)
        actions.clickAndHold(rightSlider).moveByOffset(rightOffset, 0).release().perform()
        `$`("p.h6.facet-title.hidden-sm-down").shouldBe(visible)

        val productCount = `$$x`("//div[@class='products row']/div[@class='js-product product col-xs-12 col-sm-6 col-xl-4']/div[@class='thumbnail-container reviews-loaded']").size()
        println("Number of products: $productCount")
    }

    @AfterAll
    fun tearDown() {
        closeWebDriver()
    }
}
