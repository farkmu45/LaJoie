package com.tigro.lajoie

import com.tigro.lajoie.screens.auth.ApiStatus
import com.tigro.lajoie.screens.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class AuthUnitTest {

    private val viewModel = AuthViewModel()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `logging in using empty data will set the status to ERROR`() = runTest {
        TestScope().launch {
            viewModel.apply {
                email.value = ""
                password.value = ""
                login()
            }
            assertEquals(ApiStatus.FAILED, viewModel.status.value)
        }
    }

    @Test
    fun `logging in using empty password will set the status to ERROR`() = runTest {
        TestScope().launch {
            viewModel.apply {
                email.value = "fark@gmail.com"
                password.value = ""
                login()
            }
            assertEquals(ApiStatus.FAILED, viewModel.status.value)
        }
    }

    @Test
    fun `logging in using invalid password will set the status to ERROR`() = runTest {
        TestScope().launch {
            viewModel.apply {
                email.value = "fark@gmail.com"
                password.value = "dfsafds"
                login()
            }
            assertEquals(ApiStatus.FAILED, viewModel.status.value)
        }
    }

    @Test
    fun `logging in using empty email will set the status to ERROR`() = runTest {
        TestScope().launch {
            viewModel.apply {
                email.value = ""
                password.value = "maulana123"
                login()
            }
            assertEquals(ApiStatus.FAILED, viewModel.status.value)
        }
    }

    @Test
    fun `logging in using invalid email will set the status to ERROR`() = runTest {
        TestScope().launch {
            viewModel.apply {
                email.value = "faek@gmail.com"
                password.value = "maulana123"
                login()
            }
            assertEquals(ApiStatus.FAILED, viewModel.status.value)
        }
    }

    @Test
    fun `logging in using valid credentials will set the status to SUCCESS`() = runTest {
        TestScope().launch {
            viewModel.apply {
                email.value = "faek@gmail.com"
                password.value = "maulana123"
                login()
            }
            assertEquals(ApiStatus.SUCCESS, viewModel.status.value)
        }
    }
}