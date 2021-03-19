package com.angelcalvo.doomfire

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import kotlin.math.max
import kotlin.math.roundToInt

fun main(args: Array<String>) {
    Application.launch(DoomFx::class.java, *args)
}

/* Based on http://fabiensanglard.net/doom_fire_psx/ */
class DoomFx: Application() {
    private var nextUpdate = 0L

    private val colors: List<Color> = PALETTE.map { Color(it[0] / 255.0, it[1] / 255.0, it[2] / 255.0, it[3] / 255.0) }
    private val fire: Array<Byte> = Array(WIDTH * HEIGHT) {
        when {
            it / WIDTH == HEIGHT - 1 -> WHITE
            else -> BLACK
        }
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Doom Fire"

        val canvas = Canvas()
        canvas.width = EFFECTIVE_WIDTH.toDouble()
        canvas.height = EFFECTIVE_HEIGHT.toDouble()

        val pane = Pane(canvas)
        primaryStage.scene = Scene(pane)

        val frameRateMeter: AnimationTimer = object : AnimationTimer() {
            override fun handle(now: Long) {
                if (now > nextUpdate) {
                    render(canvas.graphicsContext2D)
                    nextUpdate = now + FPS * 1000000
                }
            }
        }

        frameRateMeter.start()
        primaryStage.show()
    }

    private fun render(gc: GraphicsContext) {
        doFire()

        gc.fill = Color.BLACK
        gc.fillRect(0.0, 0.0, EFFECTIVE_WIDTH.toDouble(), EFFECTIVE_HEIGHT.toDouble())

        for (x in 0 until WIDTH) {
            for (y in 0 until HEIGHT) {
                gc.fill = colors[fire[x + WIDTH * y].toInt()]
                gc.fillRect(
                    x.toDouble() * PIXEL_WIDTH,
                    y.toDouble() * PIXEL_HEIGHT,
                    PIXEL_WIDTH.toDouble(),
                    PIXEL_HEIGHT.toDouble())
            }
        }
    }

    private fun doFire() {
        for (x in 0 until WIDTH) {
            for (y in 1 until HEIGHT) {
                val src = y * WIDTH + x
                val pixel = fire[src]
                if (pixel == BLACK) {
                    fire[src - WIDTH] = BLACK
                } else {
                    val rand = (Math.random() * 3.0f).roundToInt()
                    val dst = src - rand + 1
                    fire[dst - WIDTH] = max(pixel - max(rand, 1), BLACK.toInt()).toByte()
                }
            }
        }
    }

    companion object {
        private const val WIDTH = 200
        private const val HEIGHT = 160
        private const val PIXEL_WIDTH = 2
        private const val PIXEL_HEIGHT = 2
        const val EFFECTIVE_WIDTH = WIDTH * PIXEL_WIDTH
        const val EFFECTIVE_HEIGHT = HEIGHT * PIXEL_HEIGHT

        const val FPS = 24

        val PALETTE = listOf(
            arrayOf(0x07, 0x07, 0x07, 0), // BLACK
            arrayOf(0x1F, 0x07, 0x07, 255),
            arrayOf(0x2F, 0x0F, 0x07, 255),
            arrayOf(0x47, 0x0F, 0x07, 255),
            arrayOf(0x57, 0x17, 0x07, 255),
            arrayOf(0x67, 0x1F, 0x07, 255),
            arrayOf(0x77, 0x1F, 0x07, 255),
            arrayOf(0x8F, 0x27, 0x07, 255),
            arrayOf(0x9F, 0x2F, 0x07, 255),
            arrayOf(0xAF, 0x3F, 0x07, 255),
            arrayOf(0xBF, 0x47, 0x07, 255),
            arrayOf(0xC7, 0x47, 0x07, 255),
            arrayOf(0xDF, 0x4F, 0x07, 255),
            arrayOf(0xDF, 0x57, 0x07, 255),
            arrayOf(0xDF, 0x57, 0x07, 255),
            arrayOf(0xD7, 0x5F, 0x07, 255),
            arrayOf(0xD7, 0x5F, 0x07, 255),
            arrayOf(0xD7, 0x67, 0x0F, 255),
            arrayOf(0xCF, 0x6F, 0x0F, 255),
            arrayOf(0xCF, 0x77, 0x0F, 255),
            arrayOf(0xCF, 0x7F, 0x0F, 255),
            arrayOf(0xCF, 0x87, 0x17, 255),
            arrayOf(0xC7, 0x87, 0x17, 255),
            arrayOf(0xC7, 0x8F, 0x17, 255),
            arrayOf(0xC7, 0x97, 0x1F, 255),
            arrayOf(0xBF, 0x9F, 0x1F, 255),
            arrayOf(0xBF, 0x9F, 0x1F, 255),
            arrayOf(0xBF, 0xA7, 0x27, 255),
            arrayOf(0xBF, 0xA7, 0x27, 255),
            arrayOf(0xBF, 0xAF, 0x2F, 255),
            arrayOf(0xB7, 0xAF, 0x2F, 255),
            arrayOf(0xB7, 0xB7, 0x2F, 255),
            arrayOf(0xB7, 0xB7, 0x37, 255),
            arrayOf(0xCF, 0xCF, 0x6F, 255),
            arrayOf(0xDF, 0xDF, 0x9F, 255),
            arrayOf(0xEF, 0xEF, 0xC7, 255),
            arrayOf(0xFF, 0xFF, 0xFF, 255) // WHITE
        )

        const val BLACK: Byte = 0
        const val WHITE: Byte = 36
    }
}