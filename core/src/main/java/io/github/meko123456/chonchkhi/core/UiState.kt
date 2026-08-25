package io.github.meko123456.chonchkhi.core

/**
 * A tiny loading/success/error wrapper for screen state — the shape most apps end up
 * needing on day one, and a realistic example of the kind of pure, framework-free logic
 * that belongs in `:core` and gets covered by fast JVM tests.
 *
 * Replace or extend it with your own domain types.
 */
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>

    val isLoading: Boolean get() = this is Loading

    /** The value when successful, otherwise null. */
    fun dataOrNull(): T? = (this as? Success)?.data

    /** Transforms a success value, leaving loading/error untouched. */
    fun <R> map(transform: (T) -> R): UiState<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
        Loading -> Loading
    }

    companion object {
        /** Wraps a computation: its value on success, its message on failure. */
        fun <T> of(block: () -> T): UiState<T> = runCatching(block).fold(
            onSuccess = { Success(it) },
            onFailure = { Error(it.message ?: it::class.simpleName ?: "Unknown error") },
        )
    }
}
