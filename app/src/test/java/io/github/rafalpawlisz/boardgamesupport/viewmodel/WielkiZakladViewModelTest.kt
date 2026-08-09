package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.ui.graphics.Color
import io.github.rafalpawlisz.boardgamesupport.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WielkiZakladViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `first roll settles on one of the three colors`() = runTest {
        val viewModel = WielkiZakladViewModel()
        viewModel.generateResult()
        advanceUntilIdle()
        val result = viewModel.result
        assertTrue("expected a color, got $result", result is WielkiZakladViewModel.ColorResult)
        assertTrue(
            "unexpected color: $result",
            (result as WielkiZakladViewModel.ColorResult).color in EXPECTED_COLORS,
        )
    }

    @Test
    fun `subsequent rolls settle on numbers 1 to 3`() = runTest {
        val viewModel = WielkiZakladViewModel()
        viewModel.generateResult()
        advanceUntilIdle()
        repeat(20) {
            viewModel.generateResult()
            advanceUntilIdle()
            val result = viewModel.result
            assertTrue("expected a number, got $result", result is WielkiZakladViewModel.TextResult)
            assertTrue(
                "result out of range: $result",
                (result as WielkiZakladViewModel.TextResult).text.toInt() in 1..3,
            )
        }
    }

    @Test
    fun `new game makes the next draw a colour again`() = runTest {
        val viewModel = WielkiZakladViewModel()
        viewModel.generateResult() // colour
        advanceUntilIdle()
        viewModel.generateResult() // number
        advanceUntilIdle()
        viewModel.newGame()
        assertEquals(WielkiZakladViewModel.TextResult(""), viewModel.result)
        viewModel.generateResult()
        advanceUntilIdle()
        assertTrue(
            "expected a colour after starting a new game, got ${viewModel.result}",
            viewModel.result is WielkiZakladViewModel.ColorResult,
        )
    }

    @Test
    fun `new game stops an in-flight draw`() = runTest {
        val viewModel = WielkiZakladViewModel()
        viewModel.generateResult()
        runCurrent() // animation has started emitting interim values
        viewModel.newGame()
        advanceUntilIdle()
        assertEquals(
            "a cancelled draw overwrote the cleared state",
            WielkiZakladViewModel.TextResult(""),
            viewModel.result,
        )
    }

    private companion object {
        val EXPECTED_COLORS = listOf(Color.Green, Color.Yellow, Color(0xFFFF8C00))
    }
}
