package org.example.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Condition.clickable
import com.codeborne.selenide.Selenide.`$`

public class QuickViewModal {
    private val addToCartBtn = `$`("button.add-to-cart")
    private val increaseItemCountSpinner = `$`("i.touchspin-up")
    private val itemCountTextDisplayed = `$`("input#quantity_wanted")

    fun addToCart(itemCountToAdd: Int) {
        if (itemCountToAdd > 1) {
            var counter = itemCountToAdd
            while (counter > 1) {
                increaseItemCount()
                counter --
            }
        }
        validateItemCountChanged(itemCountToAdd)
        addToCartBtn.shouldBe(visible).click()
    }

    private fun increaseItemCount() {
        increaseItemCountSpinner.shouldBe(clickable).click()
    }

//    fun validateItemCountChanged() {
//        itemCountTextDisplayed.shouldNotHave(Condition.value("1"))
//    }

    private fun validateItemCountChanged(expectedItemCount: Int) {
        itemCountTextDisplayed.shouldHave(Condition.value(expectedItemCount.toString()))
    }
}
