package com.papayacoders.shoppinglistitemtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.papayacoders.shoppinglistitemtesting.MainCoroutineRule
import com.papayacoders.shoppinglistitemtesting.getOrAwaitValueTest
import com.papayacoders.shoppinglistitemtesting.other.Constant
import com.papayacoders.shoppinglistitemtesting.other.Status
import com.papayacoders.shoppinglistitemtesting.repository.FakeShoppingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ShoppingViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var shoppingViewModel: ShoppingViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        shoppingViewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty fields return error`() {
        shoppingViewModel.insertShoppingItem("name", "", "45.5")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun `insert shopping item with too long name return error`() {
        val string = buildString {
            for (i in 1..Constant.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem(string, "5", "45.5")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert shopping item with too price return error`() {
        val string = buildString {
            for (i in 1..Constant.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem("name", "5", string)

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun `insert shopping item with too high amount return error`() {

        shoppingViewModel.insertShoppingItem(
            "name",
            "4648948554189489651894981265498491655",
            "45.0"
        )

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun `insert shopping item with valid input return success`() {

        shoppingViewModel.insertShoppingItem("name", "5", "3.5")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)

    }
}