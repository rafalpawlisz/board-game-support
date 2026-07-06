package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.ui.graphics.Color
import io.github.rafalpawlisz.boardgamesupport.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
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

    private companion object {
        val EXPECTED_COLORS = listOf(Color.Green, Color.Yellow, Color(0xFFFF8C00))
    }
}
