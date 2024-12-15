package org.example.pages

import java.time.Duration
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`
import com.codeborne.selenide.Selenide.`$$`
// import com.codeborne.selenide.Selenide.title
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Condition

public class HomePage {
    val signInBtn = `$x`("//span[text()='Sign in']")
    val signOutBtn = `$`("a.logout.hidden-sm-down")
    val accessoriesBtn = `$`("a.dropdown-item[href*='/6-accessories']")
    val homeAccessoriesBtn = `$x`("//a[text()='Home Accessories']")
    val homeAccessoriesHeading = `$x`("//h1[text()='Home Accessories']")
    // private val productList = `$$`(".js-product")
    // private val itemCount = productList.size()

    // Probably redundant:
    // `$`("#buttons > a.btn.btn-download").shouldBe(visible)

    fun clickSignInBtn() {
        println("Clicking sign in")
        signInBtn.shouldBe(visible, Duration.ofSeconds(50)).click()        
        println("Clicked on sign in")
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

    // fun printItemCount() {
    //     println("Total items printed from HomePage class: $itemCount")
    // }
}
