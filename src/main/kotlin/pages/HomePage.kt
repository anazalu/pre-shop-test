package org.example.pages

import java.time.Duration
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$x`
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.Selenide.title
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Condition

public class HomePage {
    val signInBtn = `$x`("//span[text()='Sign in']")
    // private val productList = `$$`(".js-product")
    // private val itemCount = productList.size()


    // Probably redundant:
    // `$`("#buttons > a.btn.btn-download").shouldBe(visible)

    fun clickSignInBtn() {
        println("Clicking sign in")
        signInBtn.shouldBe(visible, Duration.ofSeconds(50)).click()        
        println("Clicked on sign in")
    }

    // fun printItemCount() {
    //     println("Total items printed from HomePage class: $itemCount")
    // }
}
