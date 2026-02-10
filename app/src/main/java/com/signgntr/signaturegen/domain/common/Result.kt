package com.signgntr.signaturegen.domain.common

/**
 * Data state for processing api response Loading, Success and Error
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exceptionMessage: String) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}