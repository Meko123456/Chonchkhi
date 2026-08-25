package io.github.meko123456.chonchkhi.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UiStateTest {

    @Test
    fun loadingReportsItself() {
        assertTrue(UiState.Loading.isLoading)
        assertFalse(UiState.Success("hi").isLoading)
    }

    @Test
    fun dataOrNullOnlyReturnsOnSuccess() {
        assertEquals("hi", UiState.Success("hi").dataOrNull())
        assertNull(UiState.Loading.dataOrNull())
        assertNull(UiState.Error("boom").dataOrNull())
    }

    @Test
    fun mapTransformsSuccessValues() {
        val mapped = UiState.Success(2).map { it * 21 }
        assertEquals(42, mapped.dataOrNull())
    }

    @Test
    fun mapLeavesLoadingAndErrorAlone() {
        assertTrue(UiState.Loading.map { it }.isLoading)
        val error = UiState.Error("boom").map { it }
        assertEquals("boom", (error as UiState.Error).message)
    }

    @Test
    fun ofCapturesSuccess() {
        assertEquals(7, UiState.of { 7 }.dataOrNull())
    }

    @Test
    fun ofCapturesFailureMessage() {
        val state = UiState.of { error("no network") }
        assertEquals("no network", (state as UiState.Error).message)
    }

    @Test
    fun ofFallsBackToExceptionTypeWhenMessageIsMissing() {
        val state = UiState.of { throw IllegalStateException() }
        assertEquals("IllegalStateException", (state as UiState.Error).message)
    }
}
