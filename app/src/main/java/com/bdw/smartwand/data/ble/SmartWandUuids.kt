package com.bdw.smartwand.data.ble

import java.util.UUID

object SmartWandUuids {
    // The main service for the SmartWand hardware
    val SERVICE_UUID_SMARTWAND: UUID = UUID.fromString("6a80b7d8-8255-42f2-9576-903b145b23e1")

    // Characteristic for controlling the LED (on/off)
    val CHARACTERISTIC_UUID_LED_CONTROL: UUID = UUID.fromString("6a80b7d8-8255-42f2-9576-903b145b23e2")

    // Characteristic for activating the horn (momentary)
    val CHARACTERISTIC_UUID_HORN_CONTROL: UUID = UUID.fromString("6a80b7d8-8255-42f2-9576-903b145b23e3")
}
