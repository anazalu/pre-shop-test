package tests

import java.time.Duration
import java.math.BigDecimal

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.Selenide.switchTo
import com.codeborne.selenide.Selenide.title
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.By
// import com.codeborne.selenide.Selenide.*
// import com.codeborne.selenide.Condition.*

import base.BaseTest
import org.example.pages.HomePage
import org.example.pages.RegisterPage


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UiTest : BaseTest() {

    @Test
    fun testEntireFlow() {
        val homePage = HomePage()
        val registerPage = RegisterPage()

        val pageTitle = "PrestaShop Live Demo"
        assertEquals(pageTitle, title(), "The webpage title does not match")
    
        switchTo().frame("framelive")

        homePage.clickSignInBtn()

        val registerBtn = `$`("a[data-link-action='display-register-form']")
        registerBtn.shouldBe(visible).click()

        registerPage.insertFirstname("Name01")
        registerPage.insertLastname("Surname01")
        registerPage.insertEmail()
        registerPage.insertPassword()
        registerPage.agreeToConditions()

        `$`("#customer-form > footer > button").shouldBe(visible).click()
        /*       
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
        val offsetPerUnit = sliderWidth.toFloat() / totalRange.toFloat()
        // Range: 18-23
        val leftOffset = ((18 - 14) * offsetPerUnit).toInt()
        val rightOffset = ((23 - 42) * offsetPerUnit).toInt()

        actions().clickAndHold(leftSlider).moveByOffset(leftOffset, 0).release().perform()

        var priceRange = `$`("#js-active-search-filters .filter-block").scrollTo().text()
        println("Price range after left slider: $priceRange")

        actions().clickAndHold(rightSlider).moveByOffset(rightOffset, 0).release().perform()

        priceRange = `$`("#js-active-search-filters .filter-block").scrollTo().shouldNotHave(Condition.text(priceRange)).text()
        println("Price range after right slider: $priceRange")

        val productList = `$$`(".js-product")
        val itemCount = productList.size()
        println("Total items: $itemCount")

//        WIP
        // homePage.printItemCount()

        val priceList = `$$`("span.price")
        for (priceSpan in priceList) {
            val price = priceSpan.text().trim().substring(1).toBigDecimal()
            assertTrue(price >= BigDecimal(18) && price <= BigDecimal(23), "Price $price is out of range")
        }

        val itemOne = Random.nextInt(0, itemCount)
        println("ItemOne: $itemOne")

        `$$`(".js-product").get(itemOne).shouldBe(visible).hover()
        `$$`(".quick-view").get(itemOne).shouldBe(visible, Duration.ofSeconds(50)).click()
        val priceItemOne = `$`("span.current-price-value").shouldBe(visible, Duration.ofSeconds(30)).text().trim().substring(1).toBigDecimal()
        println("Price: $priceItemOne")
        `$`("i.touchspin-up").shouldBe(clickable).click()
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

        `$`("#field-id_country").selectOption("France")
        `$`("#field-id_country option[value='8']").shouldBe(Condition.selected)

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

        `$x`("//span[text()='Sign in']").shouldBe(visible, Duration.ofSeconds(20))
        */
    }
}
