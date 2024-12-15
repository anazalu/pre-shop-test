package org.example.pages

import java.time.Duration
import java.math.BigDecimal

import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.Selenide.actions
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Condition

public class HomePage {
    private val signInBtn = `$x`("//span[text()='Sign in']")
    private val signOutBtn = `$`("a.logout.hidden-sm-down")
    private val accessoriesBtn = `$`("a.dropdown-item[href*='/6-accessories']")
    private val homeAccessoriesBtn = `$x`("//a[text()='Home Accessories']")
    private val homeAccessoriesHeading = `$x`("//h1[text()='Home Accessories']")
    private val sliderParent = `$`(".faceted-slider")

//    private val productList = `$$`(".js-product")
//    private val itemCount = productList.size()

    // WIP - retrieve min and max from attributes instead of these constants
    private val defaultMinPrice: Int = 14
    private val defaultMaxPrice: Int = 42

    private fun calculateOffset(): Int {
        val sliderWidth = sliderParent.find(".ui-slider").size.width
        val totalRange = defaultMaxPrice - defaultMinPrice
        return (sliderWidth.toFloat() / totalRange.toFloat()).toInt()
    }

    fun selectMinPrice(targetMinPrice: Int) {
        val leftOffset = (targetMinPrice - defaultMinPrice) * calculateOffset()
        val leftSlider = sliderParent.`$x`(".//a[contains(@class, 'ui-slider-handle')][1]").scrollTo()
        actions().clickAndHold(leftSlider).moveByOffset(leftOffset, 0).release().perform()
    }

    fun selectMaxPrice(targetMaxPrice: Int) {
        val rightOffset = (targetMaxPrice - defaultMaxPrice) * calculateOffset()
        val rightSlider = sliderParent.`$x`(".//a[contains(@class, 'ui-slider-handle')][2]").scrollTo()
        actions().clickAndHold(rightSlider).moveByOffset(rightOffset, 0).release().perform()
    }

    // fun printItemCount() {
    //     println("Total items printed from HomePage class: $itemCount")
    // }

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
}
