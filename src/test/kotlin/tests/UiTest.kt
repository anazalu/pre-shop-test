package tests

import java.math.BigDecimal

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.title
import com.codeborne.selenide.Selenide.switchTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.fail
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import base.BaseTest
import org.example.pages.AddedToCartModal
import org.example.pages.HomePage
import org.example.pages.LoginPage
import org.example.pages.QuickViewModal
import kotlin.random.Random

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UiTest : BaseTest() {

    private fun assertInRange(actual: BigDecimal, min: BigDecimal, max: BigDecimal, message: String) {
        if (actual < min || actual > max) {
            fail("$message Actual value is $actual, which is not between $min and $max.")
        }
    }

    @Test
    fun testEntireFlow() {
        val homePage = HomePage()
        val loginPage = LoginPage()
        val quickViewModal = QuickViewModal()
        val addedToCartModal = AddedToCartModal()
        val pageTitle = "PrestaShop Live Demo"
        val selectedMinPrice = 18
        val selectedMaxPrice = 23

        assertEquals(pageTitle, title(), "The webpage title does not match")
    
        switchTo().frame("framelive")

        homePage.clickSignInBtn()

        loginPage.startRegistration()
        loginPage.completeRegistration("MyFirstName", "MyLastName")

        homePage.validateUserIsLoggedIn()
        homePage.selectAccessories()
        homePage.selectHomeAccessories()
        homePage.validateHomeAccessoriesDisplayed()

        homePage.selectMinPrice(selectedMinPrice)
        var priceRange = `$`("#js-active-search-filters .filter-block").scrollTo().text()
        println("Price range after left slider: $priceRange")

        homePage.selectMaxPrice(selectedMaxPrice)
        priceRange = `$`("#js-active-search-filters .filter-block")
            .scrollTo()
            .shouldNotHave(Condition.text(priceRange))
            .text()
        println("Price range after right slider: $priceRange")

        fun validatePricesAreWithinRange(targetMinPrice: BigDecimal, targetMaxPrice: BigDecimal) {
            try {
                for (priceSpan in homePage.priceList) {
                    val price = priceSpan.text().trim().substring(1).toBigDecimal()
                    println("Current price: $price is within range: $targetMinPrice - $targetMaxPrice")
                    assertInRange(price, targetMinPrice, targetMaxPrice, "Price out of range.")
                }
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Error: Could not parse the price as a valid number. Check the format.")
            }
        }

        validatePricesAreWithinRange(BigDecimal(selectedMinPrice), BigDecimal(selectedMaxPrice))

//        val itemRandom = Random.nextInt(0, homePage.itemCount)
        val itemRandom = 0
        var expectedSubtotal = BigDecimal(0)

        val actualPrice = homePage.openQuickViewAndSavePrice(itemRandom)
        var itemCountToAdd = 3

        quickViewModal.addToCart(itemCountToAdd)
        expectedSubtotal += actualPrice * BigDecimal(itemCountToAdd)
        println("Expected subtotal: $expectedSubtotal")

        var actualSubtotal = BigDecimal(0)

        try {
            actualSubtotal = addedToCartModal.actualSubtotalText.shouldBe(visible)
                .text().trim().substring(1).toBigDecimal()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Error: Could not parse the subtotal as a valid number. Check the format.")
        }
        println("Actual subtotal: $actualSubtotal")

        assertEquals(expectedSubtotal, actualSubtotal, "Expected: $expectedSubtotal, actual: $actualSubtotal")
        addedToCartModal.clickContinueShopping()

        /*
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
