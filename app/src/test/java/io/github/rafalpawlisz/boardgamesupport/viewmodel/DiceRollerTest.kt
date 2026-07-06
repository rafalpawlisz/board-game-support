package io.github.rafalpawlisz.boardgamesupport.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class DiceRollerTest {

    @Test
    fun `settles on the value drawn up front`() = runTest {
        val emitted = mutableListOf<Int>()
        var next = 0
        DiceRoller(this).roll(randomValue = { next++ }) { emitted.add(it) }
        advanceUntilIdle()
        // The first drawn value (0) is the final result; interim values are cosmetic.
        assertEquals(0, emitted.last())
    }

    @Test
    fun `emits interim values before settling`() = runTest {
        val emitted = mutableListOf<Int>()
        var next = 0
        DiceRoller(this).roll(randomValue = { next++ }) { emitted.add(it) }
        advanceUntilIdle()
        assertTrue("expected interim values, got ${emitted.size} emission(s)", emitted.size > 1)
    }

    @Test
    fun `consecutive interim values differ when the generator allows it`() = runTest {
        val emitted = mutableListOf<Int>()
        // Deterministic generator yielding every value twice in a row: 0, 0, 1, 1, 2, 2...
        // Without the retry in nextInterim the animation would show frozen duplicates.
        var call = 0
        DiceRoller(this).roll(randomValue = { call++ / 2 }) { emitted.add(it) }
        advanceUntilIdle()
        emitted.dropLast(1).zipWithNext().forEach { (previous, current) ->
            assertNotEquals(previous, current)
        }
    }

    @Test
    fun `starting a new roll cancels the previous one`() = runTest {
        val emitted = mutableListOf<String>()
        val roller = DiceRoller(this)
        roller.roll(randomValue = { "old" }) { emitted.add(it) }
        advanceTimeBy(100.milliseconds) // partway through the first roll's animation
        roller.roll(randomValue = { "new" }) { emitted.add(it) }
        advanceUntilIdle()
        assertTrue(
            "old roll kept emitting after the new one started: $emitted",
            emitted.dropWhile { it == "old" }.all { it == "new" },
        )
        assertEquals("new", emitted.last())
    }
}
