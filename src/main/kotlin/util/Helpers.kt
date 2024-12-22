package org.example.util

import com.codeborne.selenide.SelenideElement
import java.math.BigDecimal
import kotlin.random.Random

class Helpers {
    companion object {
        fun parseAndConvertToBigDecimal(textElem: SelenideElement): BigDecimal {
            var bdValue = BigDecimal(0)
            try {
                bdValue = textElem.text().trim().substring(1).toBigDecimal()
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Error: Could not parse the text element $textElem as a valid number. Check the format.")
            }
            return bdValue
        }

        fun selectNextRandomItem(itemListSize: Int, itemAlreadyUsed: Int): Int {
            val randomList = mutableListOf(0)

            for (itemNum in 1..itemListSize - 1) {
                randomList.add(itemNum)
            }

            randomList.remove(itemAlreadyUsed)

            val randomIndex = Random.nextInt(randomList.size)
            val randomElement = randomList[randomIndex]
            return randomElement
        }
    }
}
