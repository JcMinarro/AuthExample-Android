package com.jcminarro.authexample.internal.repository

import com.jcminarro.authexample.internal.localdatasource.SessionDatasource
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.network.OAuth
import com.jcminarro.authexample.internal.network.login.LoginApiClient
import com.jcminarro.authexample.internal.network.refresh.RefreshApiClient
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class SessionRepositoryTest {

    private val VALID_USERNAME = "Jc Miñarro"
    private val VALID_PASSWORD = "password"
    private val INVALID_USERNAME = "username"
    private val INVALID_PASSWORD = "1234"
    private val ACCESS_TOKEN = "ACCESS_TOKEN"
    private val VALID_REFRESH_TOKEN = "VALID_REFRESH_TOKEN"
    private val INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN"
    private val sessionDatasource: SessionDatasource = mock()
    private val loginApiClient: LoginApiClient = mock()
    private val refreshApiClient: RefreshApiClient = mock()
    private val sessionRepository = SessionRepository(loginApiClient, refreshApiClient, sessionDatasource)
    private val validOAuth = OAuth(ACCESS_TOKEN, VALID_REFRESH_TOKEN)
    private val invalidOAuth = OAuth(ACCESS_TOKEN, INVALID_REFRESH_TOKEN)
    private val apiIoException = APIIOException(mock())

    @Before
    fun setUp() {
        When calling loginApiClient.login(VALID_USERNAME, VALID_PASSWORD) doReturn validOAuth
        When calling loginApiClient.login(INVALID_USERNAME, VALID_PASSWORD) doThrow apiIoException
        When calling loginApiClient.login(VALID_USERNAME, INVALID_PASSWORD) doThrow apiIoException
        When calling refreshApiClient.refresh(VALID_REFRESH_TOKEN) doReturn validOAuth
        When calling refreshApiClient.refresh(INVALID_REFRESH_TOKEN) doThrow apiIoException
    }

    @Test
    fun `Should store an OAuth when login with valid credential`() {
        val result = sessionRepository.login(VALID_USERNAME, VALID_PASSWORD)

        result.`should be true`()
        Verify on sessionDatasource that sessionDatasource.storeOAuthSession(validOAuth) was called
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception when try to login with an invalid password`() {
        sessionRepository.login(VALID_USERNAME, INVALID_PASSWORD)

        `Verify no further interactions` on sessionDatasource
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception when try to login with an invalid username`() {
        sessionRepository.login(INVALID_USERNAME, VALID_PASSWORD)

        `Verify no further interactions` on sessionDatasource
    }

    @Test
    fun `Should store an OAuth when refreshing with a valid refresh token receive an OAuth`() {
        When calling sessionDatasource.oAuthSession doReturn validOAuth

        val result = sessionRepository.refreshSession()

        result.`should be true`()
        Verify on sessionDatasource that sessionDatasource.oAuthSession was called
        Verify on refreshApiClient that refreshApiClient.refresh(VALID_REFRESH_TOKEN) was called
        Verify on sessionDatasource that sessionDatasource.storeOAuthSession(validOAuth) was called
    }

    @Test
    fun `Shouldn't store an OAuth when refreshing with an invalid refresh token receive an exception`() {
        When calling sessionDatasource.oAuthSession doReturn invalidOAuth

        val result = sessionRepository.refreshSession()

        result.`should be false`()
        Verify on sessionDatasource that sessionDatasource.oAuthSession was called
        Verify on refreshApiClient that refreshApiClient.refresh(INVALID_REFRESH_TOKEN) was called
        `Verify no further interactions` on sessionDatasource
    }

    @Test
    fun `Shouldn't call to refresh access token if there is no stored OAuth`() {
        val result = sessionRepository.refreshSession()

        result.`should be false`()
        Verify on sessionDatasource that sessionDatasource.oAuthSession was called
        `Verify no further interactions` on sessionDatasource
        `Verify no interactions` on refreshApiClient
    }
}