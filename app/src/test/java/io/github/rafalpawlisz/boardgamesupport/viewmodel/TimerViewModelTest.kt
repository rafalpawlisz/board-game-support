package io.github.rafalpawlisz.boardgamesupport.viewmodel

import io.github.rafalpawlisz.boardgamesupport.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

/** Durations here are the ones the screens actually use: 60 for Wielki Zakład, 5 for 5 sekund. */
@OptIn(ExperimentalCoroutinesApi::class)
class TimerViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `the full duration is on screen for its own second`() = runTest {
        val viewModel = TimerViewModel(60)
        viewModel.onCounterClicked()
        runCurrent()
        assertEquals("starting must not skip the first second", 60, viewModel.remainingTime)
        advanceTimeBy(1.seconds)
        runCurrent()
        assertEquals(59, viewModel.remainingTime)
        viewModel.onCounterClicked() // stop the countdown
    }

    @Test
    fun `countdown decrements once per second`() = runTest {
        val viewModel = TimerViewModel(60)
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(3.seconds)
        runCurrent()
        assertEquals(57, viewModel.remainingTime)
        viewModel.onCounterClicked() // stop the countdown
    }

    @Test
    fun `second click stops the countdown and resets to start time`() = runTest {
        val viewModel = TimerViewModel(60)
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(5.seconds)
        runCurrent()
        viewModel.onCounterClicked()
        assertEquals(60, viewModel.remainingTime)
        advanceTimeBy(5.seconds)
        runCurrent()
        assertEquals(60, viewModel.remainingTime) // countdown no longer running
    }

    @Test
    fun `reset stops a running countdown`() = runTest {
        val viewModel = TimerViewModel(60)
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(3.seconds)
        runCurrent()
        viewModel.reset()
        assertFalse(viewModel.isRunning)
        assertEquals(60, viewModel.remainingTime)
    }

    @Test
    fun `running state follows the countdown`() = runTest {
        val viewModel = TimerViewModel(60)
        assertFalse(viewModel.isRunning)
        viewModel.onCounterClicked()
        runCurrent()
        assertTrue("expected the countdown to report running", viewModel.isRunning)
        viewModel.onCounterClicked()
        assertFalse("stopping should clear the running state", viewModel.isRunning)
    }

    @Test
    fun `a five second round counts down to zero`() = runTest {
        val viewModel = TimerViewModel(5)
        viewModel.onCounterClicked()
        runCurrent()
        assertEquals(5, viewModel.remainingTime)
        advanceTimeBy(4.seconds)
        runCurrent()
        assertEquals(1, viewModel.remainingTime)
        advanceTimeBy(1.seconds)
        runCurrent()
        assertEquals(0, viewModel.remainingTime)
    }

    @Test
    fun `the round is over the moment it reaches zero`() = runTest {
        val viewModel = TimerViewModel(5)
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(5.seconds)
        runCurrent()
        assertEquals(0, viewModel.remainingTime)
        assertFalse("the buzzer is not part of the round", viewModel.isRunning)
    }

    @Test
    fun `a tap while the buzzer sounds starts the next round`() = runTest {
        val viewModel = TimerViewModel(5)
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(5.seconds)
        runCurrent()

        // Mid-buzzer, the players are already going again.
        viewModel.onCounterClicked()
        runCurrent()

        assertTrue("one tap has to be enough to start the next round", viewModel.isRunning)
        assertEquals(5, viewModel.remainingTime)
        advanceTimeBy(1.seconds)
        runCurrent()
        assertEquals(4, viewModel.remainingTime)
        viewModel.onCounterClicked() // stop the countdown
    }

    @Test
    fun `zero stays visible while the finish signal plays`() = runTest {
        val viewModel = TimerViewModel(5)
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(5.seconds)
        runCurrent()
        assertEquals(0, viewModel.remainingTime)
        advanceTimeBy(1.seconds) // mid-signal
        runCurrent()
        assertEquals(0, viewModel.remainingTime)
        advanceUntilIdle()
        assertEquals(5, viewModel.remainingTime)
    }
}
