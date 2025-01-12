package tests

import java.math.BigDecimal

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.switchTo
import com.codeborne.selenide.Selenide.title
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import base.BaseTest
import org.example.util.Helpers

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddTwoItemsTest : BaseTest() {

    @Test
    fun testEntireFlow() {
        val selectedMinPrice = 18 // as per requirements
        val selectedMaxPrice = 23 // as per requirements
        val priceRangeElem = `$`("#js-active-search-filters .filter-block")

        assertEquals(pageTitle, title(), "Expected: $pageTitle, actual: ${title()}")
    
        switchTo().frame("framelive")

        homePage.clickSignInBtn()

        loginPage.startRegistration()
        loginPage.completeRegistration("MyFirstName", "MyLastName")

        homePage.validateUserIsLoggedIn()
        homePage.selectAccessories()
        homePage.selectHomeAccessories()
        homePage.validateHomeAccessoriesDisplayed()

        homePage.selectMinPrice(selectedMinPrice)
        var priceRange = priceRangeElem.scrollTo().text()

        homePage.selectMaxPrice(selectedMaxPrice)
        priceRange = priceRangeElem.scrollTo().shouldNotHave(Condition.text(priceRange)).text()

        var filteredItems = 0
        for (priceSpan in homePage.priceList) {
            val priceValue = Helpers.parseAndConvertToBigDecimal(priceSpan)
            assertInRange(
                priceValue,
                BigDecimal(selectedMinPrice),
                BigDecimal(selectedMaxPrice),
                "Price $priceValue out of range $selectedMinPrice to $selectedMaxPrice."
            )
            filteredItems++
        }

        var itemRandom = 0
        var itemCountToAdd = 0
        var expectedSubtotal = BigDecimal(0)
        var actualSubtotalValue = BigDecimal(0)
        var actualPrice = BigDecimal(0)

        // Generate 2 distinct numbers for selecting 2 items
        val randomNumbers = (0..filteredItems - 1).shuffled().take(2)

        // Add first item, two pieces
        itemRandom = randomNumbers[0]
        itemCountToAdd = 2 // as per task

        actualPrice = Helpers.parseAndConvertToBigDecimal(homePage.openQuickViewAndSavePrice(itemRandom))
        quickViewModal.addToCart(itemCountToAdd)
        expectedSubtotal += actualPrice * BigDecimal(itemCountToAdd)

        val actualSubtotalItemOneText = addedToCartModal.actualSubtotalText.shouldBe(visible)
        actualSubtotalValue = Helpers.parseAndConvertToBigDecimal(actualSubtotalItemOneText)
        assertEquals(expectedSubtotal, actualSubtotalValue, "Expected: $expectedSubtotal, actual: $actualSubtotalValue")
        addedToCartModal.clickContinueShopping()

        // Add second item, one piece
        itemRandom = randomNumbers[1]
        itemCountToAdd = 1 // as per task

        actualPrice = Helpers.parseAndConvertToBigDecimal(homePage.openQuickViewAndSavePrice(itemRandom))

        quickViewModal.addToCart(itemCountToAdd)
        expectedSubtotal += actualPrice * BigDecimal(itemCountToAdd)

        val actualSubtotalItemTwoText = addedToCartModal.actualSubtotalText.shouldBe(visible)
        actualSubtotalValue = Helpers.parseAndConvertToBigDecimal(actualSubtotalItemTwoText)

        assertEquals(expectedSubtotal, actualSubtotalValue, "Expected: $expectedSubtotal, actual: $actualSubtotalValue")
        addedToCartModal.clickProceedToCheckout()

        cartPage.shoppingCartPageDisplayed()
        val actualTotalValue = Helpers.parseAndConvertToBigDecimal(cartPage.totalValueText)
        assertEquals(expectedSubtotal, actualTotalValue, "Expected: $expectedSubtotal, actual: $actualTotalValue")
        cartPage.clickProceedToCheckout()

        deliveryPage.fillInDeliveryInfo(
            "France",
            "123 Main Street",
            "12345",
            "City"
            )

        val actualFinalTotal = Helpers.parseAndConvertToBigDecimal(deliveryPage.finalTotal)
        assertEquals(expectedSubtotal, actualFinalTotal, "Expected: $expectedSubtotal, actual: $actualFinalTotal")

        orderConfirmedPage.orderConfirmedPageDisplayed()
        orderConfirmedPage.paymentByChequeDisplayed()
        orderConfirmedPage.clickLogoutBtn()
        orderConfirmedPage.validateUserIsLoggedOut()
    }
}
