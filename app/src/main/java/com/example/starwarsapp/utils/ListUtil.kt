package com.example.starwarsapp.utils

object ListUtil {

    private const val SEPARATOR = "@"

    /**
     * Returns a string containing only IDs (using a SEPARATOR) from a list of
     * urls that have the id at the end of it http://......./id/.
     */
    fun urlListToJoinedIdString(urlList: List<String>): String {
        val ids = urlList.map {
            val splitUrl = it.split("/")
            return@map splitUrl[splitUrl.size - 2]
        }
        return ids.joinToString(SEPARATOR)
    }

    /**
     * Returns an array of ids from a string of values separated by SEPARATOR
     */
    fun joinedIdStringToArray(idString: String): List<String> {
        return idString.split(SEPARATOR)
    }
}
