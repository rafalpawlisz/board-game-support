package io.github.rafalpawlisz.boardgamesupport.viewmodel

import io.github.rafalpawlisz.boardgamesupport.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DieViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `result is always within 1 to 6`() = runTest {
        val viewModel = DieViewModel()
        repeat(30) {
            viewModel.generateResult()
            advanceUntilIdle()
            assertTrue("unexpected result: ${viewModel.result}", viewModel.result.toInt() in 1..6)
        }
    }
}
