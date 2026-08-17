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
    fun `total is the sum of the dice`() = runTest {
        val viewModel = CatanViewModel()
        repeat(30) {
            viewModel.generateResult()
            advanceUntilIdle()
            assertEquals(viewModel.dice.sum(), viewModel.total)
            assertTrue("total out of range: ${viewModel.total}", viewModel.total in 2..12)
        }
    }

    @Test
    fun `nothing is shown before the first roll`() {
        assertTrue(CatanViewModel().dice.isEmpty())
        assertFalse(CatanViewModel().isRolling)
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
