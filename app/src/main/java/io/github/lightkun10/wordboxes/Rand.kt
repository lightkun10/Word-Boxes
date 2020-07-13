package io.github.lightkun10.wordboxes

import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList


class Rand(limit: Int) {
    private val randomInt: Int = ThreadLocalRandom.current().nextInt(limit)

    var searchTerms: ArrayList<String> =
        arrayListOf("compassion", "forgiveness", "determination", "healthy",
            "kindness", "knowledgable", "optimism", "genuine", "happy", "fun")

    // Return random of integer
    public fun returnRandomInt(): Int {
        return this.randomInt
    }

    public fun returnRandomTerm(): String {
        return searchTerms[returnRandomInt()]
    }
}