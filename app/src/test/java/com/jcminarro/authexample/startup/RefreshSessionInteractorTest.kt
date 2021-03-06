package com.jcminarro.authexample.startup

import com.jcminarro.authexample.internal.interactor.AsyncInteractor
import com.jcminarro.authexample.internal.repository.SessionRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.spy
import org.amshove.kluent.*
import org.junit.Test

class RefreshSessionInteractorTest {

    private val sessionRepository: SessionRepository = mock()
    private val refreshSessionInteractor = RefreshSessionInteractor(sessionRepository)
    private val callback: AsyncInteractor.Callback<Boolean, Exception> = spy()

    @Test
    fun `Should refresh session`() {
        When calling sessionRepository.refreshSession() doReturn true

        refreshSessionInteractor.execute(null, callback)

        Verify on sessionRepository that sessionRepository.refreshSession() was called
        Verify on callback that callback.onSuccess(true) was called
        `Verify no further interactions` on sessionRepository
        `Verify no further interactions` on callback
    }

    @Test
    fun `Shouldn't refresh session`() {
        When calling sessionRepository.refreshSession() doReturn false

        refreshSessionInteractor.execute(null, callback)

        Verify on sessionRepository that sessionRepository.refreshSession() was called
        Verify on callback that callback.onSuccess(false) was called
        `Verify no further interactions` on sessionRepository
        `Verify no further interactions` on callback
    }
}