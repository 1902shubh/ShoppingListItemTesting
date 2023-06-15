package com.papayacoders.shoppinglistitemtesting

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description


@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val dispathchers : CoroutineDispatcher = TestCoroutineDispatcher()
): TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispathchers) {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispathchers)

    }

    override fun finished(description: Description) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

}