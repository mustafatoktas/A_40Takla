package com.mustafatoktas.core_common

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.pager.PagerState
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

fun makeKonfeti(): List<Party> {
    val party = Party(
        speed = 10f,
        maxSpeed = 30f,
        angle = Angle.RIGHT - 45,
        spread = Spread.WIDE,
        emitter = Emitter(duration = 7, TimeUnit.SECONDS).perSecond(60),
        position = Position.Relative(0.0, 0.5)
    )

    return listOf(
        party,
        party.copy(
            angle = party.angle - 90, // sağdan sola çevirme açısı
            position = Position.Relative(1.0, 0.5)
        ),
    )
}

fun gecerliOffsetiHesapla(page: Int, pagerState: PagerState): Float =
    (pagerState.currentPage - page + pagerState.currentPageOffsetFraction).absoluteValue

// yatay animasyonlar
fun mySlideInHorizontalyPozitif() = slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(700)) + fadeIn()
fun mySlideInHorizontalyNegatif() = slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(700)) + fadeIn()
fun mySlideOutHorizontalyPozitif() = slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(700)) + fadeOut()
fun mySlideOutHorizontalyNegatif() = slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(700)) + fadeOut()

// dikey animasyonlar
fun mySlideInVerticallyPozitif() = slideInVertically(initialOffsetY = { 1000 }, animationSpec = tween(700)) + fadeIn()
fun mySlideInVerticallyNegatif() = slideInVertically(initialOffsetY = { -1000 }, animationSpec = tween(700)) + fadeIn()
fun mySlideOutVerticallyPozitif() = slideOutVertically(targetOffsetY = { 1000 }, animationSpec = tween(700)) + fadeOut()
fun mySlideOutVerticallyNegatif() = slideOutVertically(targetOffsetY = { -1000 }, animationSpec = tween(700)) + fadeOut()
