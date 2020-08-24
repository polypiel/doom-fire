package com.angelcalvo.doomfire

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import kotlin.math.roundToInt

/* Based on http://fabiensanglard.net/doom_fire_psx/ */
class Doom: PApplet() {

    private val colors: List<Int> = PALETTE.map { color(it[0].toFloat(), it[1].toFloat(), it[2].toFloat(), it[3].toFloat()) }
    private var scroll: Float = (HEIGHT * PIXEL_HEIGHT) / 2F + 40

    /*
     * fire[0] => Top left
     * fire[WIDTH - 1] => Top right
     * fire[WIDTH * (HEIGHT - 1) => Bottom left
     * fire[WIDTH * HEIGHT - 1] => Bottom right
     */
    private val fire: Array<Int> = Array(WIDTH * HEIGHT) {
        when {
            it / WIDTH == HEIGHT - 1 -> WHITE
            else -> BLACK
        }
    }

    private var doomImg: PImage? = null


    fun run() {
        setSize(WIDTH * PIXEL_WIDTH, HEIGHT * PIXEL_HEIGHT)
        runSketch()
    }

    override fun setup() {
        frameRate(24f)
        doomImg = loadImage("doom.png")
    }

    override fun draw() {
        background(0)

        image(doomImg, (WIDTH * PIXEL_WIDTH) / 2f - 150, (HEIGHT * PIXEL_HEIGHT) / 2f - 91 + scroll)

        if (scroll > -40) {
            scroll -= 2
        } else {
            for (y in HEIGHT - 8 until HEIGHT) {
                for (x in 0 until WIDTH) {
                    val src = y * WIDTH + x
                    if (fire[src] > BLACK) {
                        fire[src] = max(fire[src] - random(3f).toInt(), 0)
                    }
                }
            }
        }

        doFire()
        image(updateImg(), 0f, 0f)
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
                    fire[dst - WIDTH] = max(pixel - max(rand, 1), BLACK)
                }
            }
        }
    }

    private fun updateImg(): PImage {
        val fireImg = createImage(WIDTH, HEIGHT, PConstants.ARGB)
        fireImg.loadPixels()
        fire.forEachIndexed { i, src ->
            fireImg.pixels[i] = colors[src]

        }
        fireImg.resize(WIDTH * PIXEL_WIDTH, HEIGHT * PIXEL_HEIGHT)
        fireImg.updatePixels()
        return fireImg
    }
    companion object {
        const val WIDTH = 400
        const val HEIGHT = 40
        const val PIXEL_WIDTH = 1
        const val PIXEL_HEIGHT = 8
        
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
        const val BLACK = 0
        const val WHITE = 36
    }

}

fun main() {
    Doom().run()
}