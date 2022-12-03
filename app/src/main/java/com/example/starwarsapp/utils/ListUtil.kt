package com.example.starwarsapp.utils

object ListUtil {

    private const val SEPARATOR = "@"

    /**
     * Returns a string containing only IDs (using a SEPARATOR) from a list of
     * urls.
     */
    fun urlListToJoinedIdString(urlList: List<String>): String {
        val ids = urlList.map {
            return@map splitToGetIdFromUrl(it)
        }
        return ids.joinToString(SEPARATOR)
    }

    /**
     * Returns the id from a url that has the id at the end https://....../id/.
     */
    fun splitToGetIdFromUrl(url: String): String {
        val splitUrl = url.split("/")
        return splitUrl[splitUrl.size - 2]
    }

    /**
     * Returns an array of ids from a string of values separated by SEPARATOR
     */
    fun joinedIdStringToArray(idString: String): List<String> {
        return idString.split(SEPARATOR)
    }
}
