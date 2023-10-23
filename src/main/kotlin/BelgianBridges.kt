//import org.openrndr.application
//import org.openrndr.color.rgb
//import org.openrndr.draw.circleBatch
//import org.openrndr.extra.olive.oliveProgram
//import org.openrndr.math.Vector2
//import org.openrndr.math.map
//import kotlin.math.cos
//import kotlin.math.sin
//import kotlin.math.tan
//
//data class BangCircle(val location: Vector2, var radius: Double)
//
//fun main() = application {
//    configure {
//        width = 800
//        height = 800
//    }
//
//
//    oliveProgram {
//        val horizontalNr = 30
//        val verticalNr = 30
//        val xOffset = (width/horizontalNr)/2
//        val yOffset = (height/verticalNr)/2
//        val circles = List(
//            horizontalNr * verticalNr
//        ) { index ->
//            BangCircle(
//                Vector2(
//                    ((index % verticalNr).toDouble() * (height/verticalNr)) + yOffset,
//                    ((index / horizontalNr).toDouble() * (width/horizontalNr)) + xOffset
//                ),
//                0.0
//            )
//        }
//
//        extend {
//            drawer.circleBatch {
//                circles.forEachIndexed { index, c ->
//                    val sinValue = sin(index * seconds * 0.005)
//                    val cosValue = cos(index * seconds * 0.001)
//                    val tanValue = tan(index * seconds * 0.0001)
//                    c.radius = tanValue * 10
//                    val sinNormalized = map(-1.0, 1.0, 0.0, 1.0, sinValue)
//                    val cosNormalized = map(-1.0, 1.0, 0.0, 1.0, cosValue)
//                    val tanNormalized = map(-1.0, 1.0, 0.0, 0.3, tanValue)
//                    drawer.stroke = rgb(cosNormalized, tanNormalized, sinNormalized)
//                    drawer.fill = rgb(sinNormalized, tanNormalized, cosNormalized).opacify(sinNormalized)
//                    drawer.circle(c.location, c.radius)
//                }
//            }
//        }
//    }
//}

import org.openrndr.application
import org.openrndr.color.ColorHSLa
import org.openrndr.draw.circleBatch
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import org.openrndr.math.map
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

data class BangCircle(val location: Vector2, var radius: Double)

fun main() = application {
    configure {
        width = 800
        height = 800
    }


    oliveProgram {
        val horizontalNr = 40
        val verticalNr = 40
        val xOffset = (width / horizontalNr) / 2
        val yOffset = (height / verticalNr) / 2
        val circles = List(
            horizontalNr * verticalNr
        ) { index ->
            BangCircle(
                Vector2(
                    ((index % verticalNr).toDouble() * (height / verticalNr)) + yOffset,
                    ((index / horizontalNr).toDouble() * (width / horizontalNr)) + xOffset
                ),
                0.0
            )
        }

        extend {
            drawer.circleBatch {
                circles.forEachIndexed { index, c ->
                    val sinValue = sin(index * seconds * 0.001)
                    val cosValue = cos(index * seconds * 0.001)
                    val tanValue = tan(index * (seconds * 0.0005 % 50) * 0.1)
                    val indexNormalized =
                        map(0.0, (horizontalNr * verticalNr).toDouble(), 0.0, 1.0, index.toDouble())
                    c.radius = (sinValue)
                    val sinNormalized = map(-1.0, 1.0, 0.0, 0.4, sinValue)
                    val sinRedNormalized = map(-1.0, 1.0, 0.0, sinNormalized, sinValue)
                    val cosNormalized = map(-1.0, 1.0, 0.0, 1.0, cosValue)
                    val cosBlueNormalized = map(-1.0, 1.0, 0.0, sinValue, cosValue)
                    val tanNormalized = map(-1.0, 1.0, 0.0, 0.3, tanValue)
                    val hueNormalized = map(-1.0, 1.0, 0.0, 60.0, sinValue)
//                    drawer.stroke = rgb(cosNormalized, 0.2, sinNormalized).opacify(0.3)
//                    drawer.stroke = null
                    val opacity = map(0.0, 100000000 * width.toDouble(), 1.0, 0.0, c.radius)
//                    drawer.fill = rgb(sinRedNormalized, cosNormalized, cosBlueNormalized).opacify(0.3)
                    val colorHSVa = ColorHSLa(hueNormalized, 0.8, 0.5)
                    drawer.fill = colorHSVa.toRGBa().opacify(0.1)
//                    drawer.fill = null
                    drawer.strokeWeight = sinNormalized
                    drawer.stroke = colorHSVa.toRGBa().opacify(0.5)

//                    drawer.circle(c.location, tanValue * 5)
                    drawer.rectangle(c.location, tanValue, tanValue * 5)
                }
            }
        }
    }
}
