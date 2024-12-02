package org.example.pages

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.WebDriverRunner
import org.openqa.selenium.By
import java.time.Duration
import org.openqa.selenium.interactions.Actions
import java.math.BigDecimal
import kotlin.random.Random
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$$`

public class HomePage {
    private val productList = `$$`(".js-product")
    private val itemCount = productList.size()
    private val signInBtn = `$`("span.hidden-sm-down").shouldHave(text("Sign in"), Duration.ofSeconds(50))


    fun printItemCount() {
        println("Total items printed from HomePage class: $itemCount")
    }

    fun clickSignInBtn() {
        println("Clicking sign in")
        signInBtn.click()
        println("Clicked on sign in")
    }
}
