package org.example.pages

import java.time.Duration

import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.Selenide.actions
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement

public class HomePage {
    private val signInBtn = `$x`("//span[text()='Sign in']")
    private val signOutBtn = `$`("a.logout.hidden-sm-down")
    private val accessoriesBtn = `$`("a.dropdown-item[href*='/6-accessories']")
    private val homeAccessoriesBtn = `$x`("//a[text()='Home Accessories']")
    private val homeAccessoriesHeading = `$x`("//h1[text()='Home Accessories']")
    private val sliderParent = `$`(".faceted-slider")
    private val productList = `$$`(".js-product")
    val itemCount = productList.size()
    val priceList = `$$`("span.price")
    // WIP - retrieve min and max from attributes instead of these constants:
    private val defaultMinPrice: Int = 14
    private val defaultMaxPrice: Int = 42

    fun openQuickViewAndSavePrice(itemNumber: Int): SelenideElement {
        println("ItemNumber: $itemNumber")
        `$$`(".js-product").get(itemNumber).shouldBe(visible).hover()
        `$$`(".quick-view").get(itemNumber).shouldBe(visible, Duration.ofSeconds(50)).click()
        val priceCurrentItemTxt = `$`("span.current-price-value")
            .shouldBe(visible, Duration.ofSeconds(30))
        println("Price from HomePage class: $priceCurrentItemTxt")
        return priceCurrentItemTxt
    }

    //===========================================
    fun clickSignInBtn() {
        signInBtn.shouldBe(visible, Duration.ofSeconds(50)).click()
    }

    fun validateUserIsLoggedIn() {
        signOutBtn.shouldBe(visible, Duration.ofSeconds(20))
    }

    fun selectAccessories() {
        accessoriesBtn.shouldBe(visible).click()
    }

    fun selectHomeAccessories() {
        homeAccessoriesBtn.shouldBe(visible).click()
    }

    fun validateHomeAccessoriesDisplayed() {
        homeAccessoriesHeading.shouldBe(visible)
    }

    private fun calculateSliderOffset(): Float {
        val sliderWidth = sliderParent.find(".ui-slider").size.width
        val totalRange = defaultMaxPrice - defaultMinPrice
        return sliderWidth.toFloat() / totalRange.toFloat()
    }

    fun selectMinPrice(targetMinPrice: Int) {
        val leftOffset = (targetMinPrice - defaultMinPrice) * calculateSliderOffset()
        val leftSlider = sliderParent.`$x`(".//a[contains(@class, 'ui-slider-handle')][1]").scrollTo()
        actions().clickAndHold(leftSlider).moveByOffset(leftOffset.toInt(), 0).release().perform()
    }

    fun selectMaxPrice(targetMaxPrice: Int) {
        val rightOffset = (targetMaxPrice - defaultMaxPrice) * calculateSliderOffset()
        val rightSlider = sliderParent.`$x`(".//a[contains(@class, 'ui-slider-handle')][2]").scrollTo()
        actions().clickAndHold(rightSlider).moveByOffset(rightOffset.toInt(), 0).release().perform()
    }
}
