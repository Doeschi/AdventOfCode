package day20

import java.io.File

internal data class Image(val width: Int, val height: Int, val pixels: CharArray, val infinitPixelBrightness: Char) {

    fun setPixel(x: Int, y: Int, brightness: Char) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return

        val key = (y * width) + x

        if (key <= pixels.lastIndex) pixels[key] = brightness
        else throw RuntimeException("index is to big booooooy: width: $width, height: $height, x: $x, y: $y")
    }

    fun getPixel(x: Int, y: Int): Char {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return infinitPixelBrightness

        val key = (y * width) + x

        return if (key <= pixels.lastIndex) pixels[key]
        else throw RuntimeException("index is to big booooooy: width: $width, height: $height, x: $x, y: $y")
    }
}

fun main() {
    val allLines = File("src/main/kotlin/Day20/input.txt").bufferedReader().readLines()

    val enhancementString = allLines.removeFirst()
    allLines.removeFirst()

    val width = allLines.first().length + 2
    val height = allLines.size + 2
    val originalImage = Image(width, height, CharArray(width * height) { '.' }, '.')

    allLines.forEachIndexed { y, line ->
        line.forEachIndexed { x, brightness ->
            originalImage.setPixel(x + 1, y + 1, brightness)
        }
    }

    printImage(originalImage)
    println()


    var image = originalImage
    for (i in 0..<2) {
        image = enhanceImage(image, enhancementString)
        printImage(image)
        println("After ${i + 1} iterations, there are ${countBrightPixels(image)} bright pixels in the ${image.width}x${image.height} image")
    }

    image = originalImage
    for (i in 0..<50) {
        image = enhanceImage(image, enhancementString)
        printImage(image)
        println("After ${i + 1} iterations, there are ${countBrightPixels(image)} bright pixels in the ${image.width}x${image.height} image)")
    }
}

private fun countBrightPixels(image: Image): Int {
    return image.pixels.count { c -> c == '#' }
}

private fun enhanceImage(image: Image, enhanceString: String): Image {
    val newInfinitePixelBrightness = if (image.infinitPixelBrightness == '.') enhanceString[0] else enhanceString[511]
    val newWidth = image.width + 2
    val newHeight = image.height + 2
    val newImage = Image(
        newWidth,
        newHeight,
        CharArray(newWidth * newHeight) { newInfinitePixelBrightness },
        newInfinitePixelBrightness
    )

    for (x in 0..<image.width) {
        for (y in 0..<image.height) {
            val enhanceIndex = getIndex(image, x, y)
            newImage.setPixel(x + 1, y + 1, enhanceString[enhanceIndex])
        }
    }

    return newImage
}

private fun getIndex(image: Image, x: Int, y: Int): Int {
    var index = 0
    var powerOfTwo = 256

    for (yOff in -1..1) {
        for (xOff in -1..1) {
            val c = image.getPixel(x + xOff, y + yOff)
            if (c == '#') {
                index += powerOfTwo
            }

            powerOfTwo /= 2
        }
    }

    return index
}

private fun printImage(image: Image) {
    for (y in 0..<image.height) {
        for (x in 0..<image.width) {
            print(image.getPixel(x, y))
        }
        println()
    }
}