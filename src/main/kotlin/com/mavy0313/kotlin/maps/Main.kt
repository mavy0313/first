package com.mavy0313.kotlin.maps

fun main() {
//    val association = Pair("Nail", "Hammer")
//    val association = "Nail".to("Hammer")
//    val association = "Nail" to "Hammer"
    val association: Pair<String, String> = "Nail" to "Hammer"
    println(association.first)
    println(association.second)

    val toolbox: Map<String, String> = mapOf(
        "Nail" to "Hammer",
        "Hex Nut" to "Wrench",
        "Hex Bolt" to "Wrench",
        "Slotted Screw" to "Slotted Screwdriver",
        "Phillips Screw" to "Phillips Screwdriver",
    )
    println(toolbox)

    val tool = toolbox.get("Nail")
    println(tool)

    val tool2 = toolbox["Nail"]
    println(tool2)

    val tool3 = toolbox.getValue("Nail")
    println(tool3) // Hammer

//    val anotherTool = toolbox.getValue("Wing Nut")

    val tool4 = toolbox.getOrDefault("Hanger Bolt", "Hand")
    println(tool4)

    val mutableToolbox = mutableMapOf(
        "Nail" to "Hammer",
        "Hex Nut" to "Wrench",
        "Hex Bolt" to "Wrench",
        "Slotted Screw" to "Slotted Screwdriver",
        "Phillips Screw" to "Phillips Screwdriver",
    )

//    mutableToolbox.put("Lumber", "Saw")
    mutableToolbox["Lumber"] = "Saw"

    mutableToolbox["Hex Bolt"] = "Nut Driver"
    mutableToolbox.remove("Lumber")

    mutableToolbox.remove("Phillips Screw")
    mutableToolbox["Cross Recess Screw"] = "Phillips Screwdriver"

    var immutableToolbox = mapOf(
        "Nail" to "Hammer",
        "Hex Nut" to "Wrench",
        "Hex Bolt" to "Wrench",
        "Slotted Screw" to "Slotted Screwdriver",
        "Phillips Screw" to "Phillips Screwdriver",
    )

    // Add an entry
    immutableToolbox = immutableToolbox + Pair("Lumber", "Saw")

// Update an entry
    immutableToolbox = immutableToolbox + Pair("Hex Bolt", "Nut Driver")

// Remove an entry
    immutableToolbox = immutableToolbox - "Lumber"

// Simulate changing a key
    immutableToolbox = immutableToolbox - "Phillips Screw"
    immutableToolbox = immutableToolbox + Pair("Cross Recess Screw", "Phillips Screwdriver")

    toolbox.forEach { entry ->
        println("Use a ${entry.value} on a ${entry.key}")
    }

    val screwdrivers = toolbox.filter { entry ->
        entry.value.contains("Screwdriver")
    }
    println(screwdrivers)

    val screwdriversByKeys = toolbox.filter { entry ->
        entry.key.contains("Screw")
    }
    println(screwdriversByKeys)

    val newToolbox = toolbox
        .mapKeys { entry -> entry.key.replace("Hex", "Flange") }
        .mapValues { entry -> entry.value.replace("Wrench", "Ratchet") }
    println(newToolbox)

    val tool5 = toolbox.getOrDefault("Hanger Bolt", "Hand")
    val anotherTool = toolbox.getOrDefault("Dowel Screw", "Hand")
    val oneMoreTool = toolbox.getOrDefault("Eye Bolt", "Hand")

    println(anotherTool)

    val toolboxWithDefault = toolbox.withDefault { key -> "Hand" }
    println(toolboxWithDefault.getValue("Hanger Bolt"))
    println(toolboxWithDefault.getValue("Dowel Screw"))
    println(toolboxWithDefault.getValue("Eye Bolt"))

    val tools = listOf(
        Tool("Hammer", 14, "Nail"),
        Tool("Wrench", 8, "Hex Nut"),
        Tool("Wrench", 8, "Hex Bolt"),
        Tool("Slotted Screwdriver", 5, "Slotted Screw"),
        Tool("Phillips Screwdriver", 5, "Phillips Screw"),
    )

    val toolbox3 = tools.associate { tool ->
        tool.correspondingHardware to tool.name
    }

    val toolsByName = tools.associateBy { tool -> tool.name }
    println(toolsByName)

    val toolWeightInPounds = tools.associateWith { tool ->
        tool.weightInOunces * 0.0625
    }
    println(toolWeightInPounds)

    val toolbox4 = tools.associateBy({ it.correspondingHardware }, { it.name })
    println(toolbox4)

    val toolsByWeight = tools.groupBy { tool ->
        tool.weightInOunces
    }
    println(toolsByWeight)

    val toolNamesByWeight = tools.groupBy(
        { tool -> tool.weightInOunces },
        { tool -> tool.name }
    )
    println(toolNamesByWeight)
}

data class Tool(
    val name: String,
    val weightInOunces: Int,
    val correspondingHardware: String,
)