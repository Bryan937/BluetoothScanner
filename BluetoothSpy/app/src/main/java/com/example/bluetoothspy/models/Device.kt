package com.example.bluetoothspy.models

data class Device(
    val name: String  = "",
    val macAddress: String  = "",
    val btType: String  = "",
    val bondState: String  = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var isFavourite: Boolean = false
) {
    fun toJsonString(): String {
        return """
        {
            "name": "${name}",
            "mac Address": "${macAddress}",
            "bluetooth Type": "${btType}",
            "bond State": "${bondState}"
        }
    """.trimIndent()
    }
    fun infoForMap(): String {
        return "name : ${name}  \n mac Address: ${macAddress}\n bluetooth Type: ${btType}\n bond State: ${bondState}"


    }

    companion object {
        fun getBluetoothType(btTypeInt: Int) : String {
            val btTypeMap = mapOf(
                1 to "Bluetooth Class 1",
                2 to "Bluetooth Class 2",
                3 to "Bluetooth Class 3"
            )
            return btTypeMap[btTypeInt] ?: "Unknown"
        }

        fun getBondState(bondStateInt: Int): String {
            val bondStateMap = mapOf(
                10 to "Not paired",
                11 to "Pairing in progress",
                12 to "Paired"
            )
            return bondStateMap[bondStateInt] ?: "Unknown"
        }
    }
}
