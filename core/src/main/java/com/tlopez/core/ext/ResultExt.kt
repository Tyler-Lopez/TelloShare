package com.tlopez.core.ext

inline fun <TypeOfData> Result<TypeOfData>.doOnSuccess(
    callback: (TypeOfData) -> Unit
): Result<TypeOfData> {
    if (isSuccess) callback(getOrNull()!!)
    return this
}

inline fun <TypeOfData> Result<TypeOfData>.doOnFailure(
    callback: (Throwable?) -> Unit
): Result<TypeOfData> {
    if (isFailure) callback(exceptionOrNull())
    return this
}
