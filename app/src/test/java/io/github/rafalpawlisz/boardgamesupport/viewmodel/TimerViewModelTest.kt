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

@OptIn(ExperimentalCoroutinesApi::class)
class TimerViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `countdown decrements once per second`() = runTest {
        val viewModel = TimerViewModel()
        viewModel.onCounterClicked()
        runCurrent()
        assertEquals(29, viewModel.remainingTime)
        advanceTimeBy(1.seconds)
        runCurrent()
        assertEquals(28, viewModel.remainingTime)
        advanceTimeBy(3.seconds)
        runCurrent()
        assertEquals(25, viewModel.remainingTime)
        viewModel.onCounterClicked() // stop the countdown
    }

    @Test
    fun `second click stops the countdown and resets to start time`() = runTest {
        val viewModel = TimerViewModel()
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(5.seconds)
        runCurrent()
        viewModel.onCounterClicked()
        assertEquals(30, viewModel.remainingTime)
        advanceTimeBy(5.seconds)
        runCurrent()
        assertEquals(30, viewModel.remainingTime) // countdown no longer running
    }

    @Test
    fun `countdown runs to zero and resets to start time`() = runTest {
        val viewModel = TimerViewModel()
        viewModel.onCounterClicked()
        advanceUntilIdle()
        assertEquals(30, viewModel.remainingTime)
    }

    @Test
    fun `running state follows the countdown`() = runTest {
        val viewModel = TimerViewModel()
        assertFalse(viewModel.isRunning)
        viewModel.onCounterClicked()
        runCurrent()
        assertTrue("expected the countdown to report running", viewModel.isRunning)
        viewModel.onCounterClicked()
        assertFalse("stopping should clear the running state", viewModel.isRunning)
    }

    @Test
    fun `running state clears once the countdown finishes`() = runTest {
        val viewModel = TimerViewModel()
        viewModel.onCounterClicked()
        advanceUntilIdle()
        assertFalse(viewModel.isRunning)
    }

    @Test
    fun `zero stays visible while the finish signal plays`() = runTest {
        val viewModel = TimerViewModel()
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(29.seconds)
        runCurrent()
        assertEquals(0, viewModel.remainingTime)
        advanceTimeBy(1.seconds) // mid-signal
        runCurrent()
        assertEquals(0, viewModel.remainingTime)
        advanceUntilIdle()
        assertEquals(30, viewModel.remainingTime)
    }

    @Test
    fun `click during the finish signal stops it and resets`() = runTest {
        val viewModel = TimerViewModel()
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(29.seconds)
        runCurrent() // countdown just hit zero, signal playing
        assertEquals(0, viewModel.remainingTime)
        viewModel.onCounterClicked()
        assertEquals(30, viewModel.remainingTime)
    }

    @Test
    fun `changing start time stops the countdown and applies the new value`() = runTest {
        val viewModel = TimerViewModel()
        viewModel.onCounterClicked()
        runCurrent()
        advanceTimeBy(5.seconds)
        runCurrent()
        viewModel.startTime = 60
        assertEquals(60, viewModel.remainingTime)
        advanceTimeBy(5.seconds)
        runCurrent()
        assertEquals(60, viewModel.remainingTime) // countdown no longer running
    }
}
