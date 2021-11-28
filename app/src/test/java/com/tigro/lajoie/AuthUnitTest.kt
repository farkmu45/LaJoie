package com.tigro.lajoie

import com.tigro.lajoie.models.AuthToken
import com.tigro.lajoie.network.LajoieApi
import com.tigro.lajoie.network.LoginBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class AuthUnitTest {

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
    fun `logging in using empty data will throw Exception`() = runBlocking {
        try {
            LajoieApi.retrofitService.login(LoginBody("", ""))
            assertFalse(true)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `logging in using empty password will throw Exception`() = runBlocking {
        try {
            LajoieApi.retrofitService.login(LoginBody("fark@gmail.com", ""))
            assertFalse(true)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `logging in using invalid password will throw Exception`() = runBlocking {
        try {
            LajoieApi.retrofitService.login(LoginBody("fark@gmail.com", "maulsdfas"))
            assertFalse(true)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `logging in using empty email will throw Exception`() = runBlocking {
        try {
            LajoieApi.retrofitService.login(LoginBody("", "maulana123"))
            assertFalse(true)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `logging in using invalid email will throw Exception`() = runBlocking {
        try {
            LajoieApi.retrofitService.login(LoginBody("farko@gmail.com", "maulana123"))
            assertFalse(true)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `logging in using valid credentials will send the token`() = runBlocking {
        val token = LajoieApi.retrofitService.login(LoginBody("fark@gmail.com", "maulana123"))
        assertEquals(AuthToken("ZmFya0BnbWFpbC5jb206bWF1bGFuYTEyMw=="), token)
    }
}