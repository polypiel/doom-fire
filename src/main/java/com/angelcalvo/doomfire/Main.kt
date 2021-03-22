package com.angelcalvo.doomfire

import javafx.application.Application

fun main(args: Array<String>) {
    val arg = (args.getOrNull(0) ?: "processing").toLowerCase()
    when (arg) {
        "fx" -> Application.launch(DoomFx::class.java, *args)
        else -> Doom().run()
    }
}
