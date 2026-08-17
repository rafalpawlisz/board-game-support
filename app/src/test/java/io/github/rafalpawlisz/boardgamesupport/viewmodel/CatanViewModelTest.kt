package io.github.rafalpawlisz.boardgamesupport.viewmodel

import io.github.rafalpawlisz.boardgamesupport.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatanViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `rolls two ordinary dice`() = runTest {
        val viewModel = CatanViewModel()
        repeat(30) {
            viewModel.generateResult()
            advanceUntilIdle()
            assertEquals("Catan rolls two dice", 2, viewModel.dice.size)
            viewModel.dice.forEach { die ->
                assertTrue("die out of range: $die", die in 1..6)
            }
        }
    }

    @Test
    fun `the total is the two dice added together`() = runTest {
        val viewModel = CatanViewModel()
        repeat(30) {
            viewModel.generateResult()
            advanceUntilIdle()
            // Added up here rather than asked of the same sum() the view model uses:
            // the requirement is that the total is the pair, and a test that reaches
            // for the implementation to say so cannot disagree with it.
            val (first, second) = viewModel.dice
            assertEquals("$first + $second", first + second, viewModel.total)
        }
    }

    @Test
    fun `nothing is shown before the first roll`() {
        val viewModel = CatanViewModel()
        assertTrue(viewModel.dice.isEmpty())
        assertFalse(viewModel.isRolling)
    }

    @Test
    fun `the dice are not a result until they settle`() = runTest {
        val viewModel = CatanViewModel()

        viewModel.generateResult()
        runCurrent()
        assertTrue("the animation's pairs must not pass for a roll", viewModel.isRolling)

        advanceUntilIdle()
        assertFalse("the dice have stopped, so the total stands", viewModel.isRolling)
    }
}
