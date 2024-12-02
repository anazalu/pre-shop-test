import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.openqa.selenium.By
import java.time.Duration
import org.openqa.selenium.interactions.Actions
import java.math.BigDecimal
import kotlin.random.Random
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UiTest {

    @BeforeAll
    fun setUp() {
        open("http://demo.prestashop.com")
        WebDriverRunner.getWebDriver().manage().window().maximize()
    }

    @Test
    fun testEntireFlow() {
        Assertions.assertTrue(title() == "PrestaShop Live Demo", "The title does not match")
        `$`("#buttons > a.btn.btn-download").shouldBe(visible)
        switchTo().frame("framelive")
//        `$`("span.hidden-sm-down").shouldHave(text("Sign in"), Duration.ofSeconds(50)).click()
        `$x`("//span[text()='Sign in']").shouldBe(visible, Duration.ofSeconds(50)).click()
//        `$`("#content > div > a").shouldBe(visible).click()
        `$`("a[data-link-action='display-register-form']").shouldBe(visible).click()

        `$`("#field-firstname").shouldBe(visible).clear()
        `$`("#field-firstname").sendKeys("A")
        `$`("#field-lastname").shouldBe(visible).clear()
        `$`("#field-lastname").sendKeys("B")
        `$`("#field-email").shouldBe(visible).clear()
        val randEmail = Random.nextInt(0, 1000000)
        `$`("#field-email").sendKeys("a$randEmail@b.com")
        `$`("#field-password").shouldBe(visible).clear()
        `$`("#field-password").shouldBe(visible).clear()
        `$`("#field-password").sendKeys("1userUser!")
        `$`("input[name='psgdpr']").scrollTo().click()
        `$`("input[name='customer_privacy']").scrollTo().click()
        `$`("#customer-form > footer > button").shouldBe(visible).click()
        `$`("a.logout.hidden-sm-down").shouldBe(visible, Duration.ofSeconds(20))
        `$`("a.dropdown-item[href*='/6-accessories']").shouldBe(visible).click()
        `$x`("//a[text()='Home Accessories']").shouldBe(visible).click()
        `$x`("//h1[text()='Home Accessories']").shouldBe(visible)

        val sliderParent = `$`(".faceted-slider")
        val leftSlider = sliderParent.find(By.xpath(".//a[contains(@class, 'ui-slider-handle')][1]")).scrollTo()
        val rightSlider = sliderParent.find(By.xpath(".//a[contains(@class, 'ui-slider-handle')][2]")).scrollTo()
        val sliderWidth = sliderParent.find(".ui-slider").size.width
        // WIP - retrieve min and max from attributes instead of these constants
        val totalRange = 42 - 14
        val offsetPerUnit = sliderWidth / totalRange
        // Range: 18-23
        val leftOffset = ((18 - 14) * offsetPerUnit).toInt()
        val rightOffset = ((23 - 42) * offsetPerUnit).toInt()

        val actions = Actions(WebDriverRunner.getWebDriver())
        actions.clickAndHold(leftSlider).moveByOffset(leftOffset, 0).release().perform()

        var priceRange = `$`("#js-active-search-filters .filter-block").scrollTo().text()
        println("Price range after left slider: $priceRange")

//        `$`(".faceted-slider").shouldBe(visible, Duration.ofSeconds(120))
        actions.clickAndHold(rightSlider).moveByOffset(rightOffset, 0).release().perform()
//        `$`(".faceted-slider").shouldBe(visible).scrollTo()

        priceRange = `$`("#js-active-search-filters .filter-block").scrollTo().shouldNotHave(Condition.text(priceRange)).text()
        println("Price range after right slider: $priceRange")

        val productList = `$$`(".js-product")
        val itemCount = productList.size()
        println("Total items: $itemCount")

        val priceList = `$$`("span.price")
        for (priceSpan in priceList) {
//            println("Price: ${price.text()}")
            val price = priceSpan.text().trim().substring(1).toBigDecimal()
            assertTrue(price >= BigDecimal(18) && price <= BigDecimal(23), "Price $price is out of range")
        }

        val itemOne = Random.nextInt(0, itemCount)
        println("ItemOne: $itemOne")

        val quickViewList = `$$`(".quick-view")
        for (quickView in quickViewList) {
            println(quickView)
        }

        `$$`(".js-product").get(itemOne).shouldBe(visible).hover()
        `$$`(".quick-view").get(itemOne).shouldBe(visible, Duration.ofSeconds(50)).click()
        val priceItemOne = `$`("span.current-price-value").shouldBe(visible, Duration.ofSeconds(30)).text().trim().substring(1).toBigDecimal()
        println("Price: $priceItemOne")
        `$`("i.touchspin-up").shouldBe(clickable).click()
//        `$`("input#quantity_wanted").shouldNotHave(Condition.text("1"))
//        sleep(3000)
        `$`("input#quantity_wanted").shouldNotHave(Condition.value("1"))

        screenshot("increaseItemCount")
        `$`("button.add-to-cart").shouldBe(visible).click()
        val priceItemOneSubtotal = `$`("span.subtotal.value").shouldBe(visible).text().trim().substring(1).toBigDecimal()
        println("Subtotal: $priceItemOneSubtotal")
        assertEquals(priceItemOne * BigDecimal(2), priceItemOneSubtotal)
        `$x`("//button[text()='Continue shopping']").shouldBe(visible).click()

        `$`("#js-active-search-filters .filter-block").shouldBe(visible)

        var itemTwo: Int
        do {
            itemTwo = Random.nextInt(0, itemCount)
        } while (itemTwo == itemOne)
        println("ItemTwo: $itemTwo")

        `$$`(".js-product").get(itemTwo).shouldBe(visible).hover()
        `$$`(".quick-view").get(itemTwo).shouldBe(visible).click()

        val priceItemTwo = `$`("span.current-price-value").shouldBe(visible, Duration.ofSeconds(30)).text().trim().substring(1).toBigDecimal()
        println("Price item two: $priceItemTwo")
        `$`("button.add-to-cart").shouldBe(visible).click()
        `$x`("//a[text()='Proceed to checkout']").shouldBe(visible).click()

        `$x`("//h1[text()='Shopping Cart']").shouldBe(visible)
        val totalValue = `$`(".cart-summary-line.cart-total .value").shouldBe(visible).text().trim().substring(1).toBigDecimal()
        println("Total: $totalValue")
        val expectedTotal = priceItemOneSubtotal.add(priceItemTwo)
        assertEquals(expectedTotal, totalValue)

        `$x`("//a[text()='Proceed to checkout']").shouldBe(visible).click()
        `$`("#field-address1").scrollTo().setValue("123 Main Street")
        `$`("#field-postcode").scrollTo().setValue("12345")
        `$`("#field-city").scrollTo().setValue("City")
        `$`("button[name='confirm-addresses']").scrollTo().shouldBe(visible).click()
        `$`("button[name='confirmDeliveryOption']").scrollTo().shouldBe(visible).click()
        `$x`("//span[text()='Pay by Check']").shouldBe(visible).click()

        val finalTotal = `$`(".cart-summary-line.cart-total .value").shouldBe(visible).text().trim().substring(1).toBigDecimal()
        assertEquals(expectedTotal, finalTotal)

        `$`("input[name='conditions_to_approve[terms-and-conditions]']").shouldBe(clickable).click()
        screenshot("my")
        `$`("#payment-confirmation .btn.btn-primary.center-block").scrollTo().shouldBe(clickable).click()
        `$x`("//p[text()='Your order on PrestaShop is complete.']").scrollTo()
        `$x`("//li[text()='Payment method: Payments by check']").shouldBe(visible)

        `$`("a.logout").scrollTo().click()

        `$`("span.hidden-sm-down").shouldHave(text("Sign in")).shouldBe(visible)

    }

    @AfterAll
    fun tearDown() {
        closeWebDriver()
    }
}
