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
import com.codeborne.selenide.SelenideElement
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

    private fun parseAndConvertToBigDecimal(textElem: SelenideElement): BigDecimal {
        var bdValue = BigDecimal(0)
        try {
            bdValue = textElem.text().trim().substring(1).toBigDecimal()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Error: Could not parse the text element as a valid number. Check the format.")
        }
        return bdValue
    }

    @Test
    fun testEntireFlow() {
        val homePage = HomePage()
        val loginPage = LoginPage()
        val quickViewModal = QuickViewModal()
        val addedToCartModal = AddedToCartModal()
        val pageTitle = "PrestaShop Live Demo"
        val selectedMinPrice = 16
        val selectedMaxPrice = 41

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

        for (priceSpan in homePage.priceList) {
            val priceValue = parseAndConvertToBigDecimal(priceSpan)
            println("Current price: $priceValue is within range: $selectedMinPrice - $selectedMaxPrice")
            assertInRange(priceValue, BigDecimal(selectedMinPrice), BigDecimal(selectedMaxPrice), "Price out of range.")
        }

//        val itemRandom = Random.nextInt(0, homePage.itemCount)

//        val list = mutableListOf("one", "two", "three", "four", "five")
//        val randomElement = list.asSequence().shuffled().find { true }
//        val randomElements = list.asSequence().shuffled().take(numberOfElements).toList()
//        list.removeIf { i -> randomElements.contains(i) }

        var itemRandom = 0
        var itemCountToAdd = 0
        var expectedSubtotal = BigDecimal(0)
        var actualSubtotalValue = BigDecimal(0)
        var actualPrice = BigDecimal(0)

        // Add first item - WIP generate randomly
        itemRandom = 1
        itemCountToAdd = 2

        actualPrice = parseAndConvertToBigDecimal(homePage.openQuickViewAndSavePrice(itemRandom))

        quickViewModal.addToCart(itemCountToAdd)
        expectedSubtotal += actualPrice * BigDecimal(itemCountToAdd)
        println("Expected subtotal: $expectedSubtotal")

        val actualSubtotalItemOneText = addedToCartModal.actualSubtotalText.shouldBe(visible)
        actualSubtotalValue = parseAndConvertToBigDecimal(actualSubtotalItemOneText)
        println("Actual subtotal: $actualSubtotalValue")

        assertEquals(expectedSubtotal, actualSubtotalValue, "Expected: $expectedSubtotal, actual: $actualSubtotalValue")
        addedToCartModal.clickContinueShopping()

        // Add second item
        itemRandom = 0
        itemCountToAdd = 1

        actualPrice = parseAndConvertToBigDecimal(homePage.openQuickViewAndSavePrice(itemRandom))

        quickViewModal.addToCart(itemCountToAdd)
        expectedSubtotal += actualPrice * BigDecimal(itemCountToAdd)
        println("Expected subtotal: $expectedSubtotal")

        val actualSubtotalItemTwoText = addedToCartModal.actualSubtotalText.shouldBe(visible)
        actualSubtotalValue = parseAndConvertToBigDecimal(actualSubtotalItemTwoText)
        println("Actual subtotal: $actualSubtotalValue")

        assertEquals(expectedSubtotal, actualSubtotalValue, "Expected: $expectedSubtotal, actual: $actualSubtotalValue")
        addedToCartModal.clickProceedToCheckout()

        /*
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
