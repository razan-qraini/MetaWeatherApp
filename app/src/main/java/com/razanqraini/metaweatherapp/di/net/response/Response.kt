package com.razanqraini.metaweatherapp.di.net.response

/**
 * A generic response class that holds a data and error with a [Status] to indicate if the response is successful
 */
data class Response<out DATA, out ERROR>(val status: Status, val data: DATA?, val error: ERROR?) {

    /**
     * Indicates that response is successful ([status] == [Status.SUCCESS]).
     */
    val isSuccessful: Boolean
        get() = status == Status.SUCCESS

    /**
     * map Response<D1, E> into Response<D2, E>
     */
    fun <RESULT> map(map: (DATA?) -> RESULT?) = Response(status, map(data), error)

    /**
     * map Response<D1, E> into Response<D2, E>, pass a callback to map the data if it is not null.
     */
    fun <DATA2> mapNotNull(map: (DATA) -> DATA2?) = Response(status, data?.let { map(data) }, error)

    companion object {
        fun <DATA, ERROR> success(data: DATA?): Response<DATA, ERROR> {
            return Response(Status.SUCCESS, data, null)
        }

        fun <DATA, ERROR> error(error: ERROR?): Response<DATA, ERROR> {
            return Response(Status.ERROR, null, error)
        }

        /**
         * Combines the [r1] and [r2] into a single response of type [R] using the given [mapper].
         * It also combines the [r1]'s error and [r2]'s error into a single error of type [E] using the given [errorMapper].
         *
         * It returns a success response [Response.success] if and only if [r1] and [r2] were successful responses,
         * otherwise it returns an error response [Response.error].
         *
         * @param DATA1 1st-response data type
         * @param DATA2 2nd-response data type
         * @param ERROR response error type
         * @param R the combined response type
         * @param E the combined error type
         */
        fun <DATA1, DATA2, ERROR, R, E> combine(
            r1: Response<DATA1, ERROR>,
            r2: Response<DATA2, ERROR>,
            mapper: (
                r1: DATA1?,
                r2: DATA2?
            ) -> R,
            errorMapper: (
                e1: ERROR?,
                e2: ERROR?
            ) -> E?
        ): Response<R, E> {
            val allResponsesSuccessful =
                r1.status == Status.SUCCESS && r2.status == Status.SUCCESS

            return if (allResponsesSuccessful) {
                success(mapper(r1.data, r2.data))
            } else {
                error(errorMapper(r1.error, r2.error))
            }
        }

        /**
         * Combines the [r1], [r2] and [r3] into a single response of type [R] using the given [mapper].
         * It also combines the [r1]'s error, [r2]'s error and [r3]'s error into a single error of type [E] using the given [errorMapper].
         *
         * It returns a success response [Response.success] if and only if [r1], [r2] and [r3] were successful responses,
         * otherwise it returns an error response [Response.error].
         *
         * @param DATA1 1st-response data type
         * @param DATA2 2nd-response data type
         * @param DATA3 3rd-response data type
         * @param ERROR response error type
         * @param R the combined response type
         * @param E the combined error type
         */
        fun <DATA1, DATA2, DATA3, ERROR, R, E> combine(
            r1: Response<DATA1, ERROR>,
            r2: Response<DATA2, ERROR>,
            r3: Response<DATA3, ERROR>,
            mapper: (
                r1: DATA1?,
                r2: DATA2?,
                r3: DATA3?
            ) -> R,
            errorMapper: (
                e1: ERROR?,
                e2: ERROR?,
                e3: ERROR?
            ) -> E?
        ): Response<R, E> {
            val allResponsesSuccessful =
                r1.status == Status.SUCCESS && r2.status == Status.SUCCESS && r3.status == Status.SUCCESS

            return if (allResponsesSuccessful) {
                success(mapper(r1.data, r2.data, r3.data))
            } else {
                error(errorMapper(r1.error, r2.error, r3.error))
            }
        }

        /**
         * Combines the [r1], [r2], [r3] and [r4] into a single response of type [R] using the given [mapper].
         * It also combines the [r1]'s error, [r2]'s error, [r3]'s error and [r4]'s error into a single error of type [E] using the given [errorMapper].
         *
         * It returns a success response [Response.success] if and only if [r1], [r2], [r3] and [r4] were successful responses,
         * otherwise it returns an error response [Response.error].
         *
         * @param DATA1 1st-response data type
         * @param DATA2 2nd-response data type
         * @param DATA3 3rd-response data type
         * @param DATA4 4th-response data type
         * @param ERROR response error type
         * @param R the combined response type
         * @param E the combined error type
         */
        fun <DATA1, DATA2, DATA3, DATA4, ERROR, R, E> combine(
            r1: Response<DATA1, ERROR>,
            r2: Response<DATA2, ERROR>,
            r3: Response<DATA3, ERROR>,
            r4: Response<DATA4, ERROR>,
            mapper: (
                r1: DATA1?,
                r2: DATA2?,
                r3: DATA3?,
                r4: DATA4?
            ) -> R,
            errorMapper: (
                e1: ERROR?,
                e2: ERROR?,
                e3: ERROR?,
                e4: ERROR?
            ) -> E?
        ): Response<R, E> {
            val allResponsesSuccessful =
                r1.status == Status.SUCCESS && r2.status == Status.SUCCESS && r3.status == Status.SUCCESS && r4.status == Status.SUCCESS

            return if (allResponsesSuccessful) {
                success(mapper(r1.data, r2.data, r3.data, r4.data))
            } else {
                error(errorMapper(r1.error, r2.error, r3.error, r4.error))
            }
        }

        /**
         * Combines the [r1], [r2], [r3], [r4] and [r5] into a single response of type [R] using the given [mapper].
         * It also combines the [r1]'s error, [r2]'s error, [r3]'s error, [r4]'s error and [r5]'s error into a single error of type [E] using the given [errorMapper].
         *
         * It returns a success response [Response.success] if and only if [r1], [r2], [r3], [r4] and [r5] were successful responses,
         * otherwise it returns an error response [Response.error].
         *
         * @param DATA1 1st-response data type
         * @param DATA2 2nd-response data type
         * @param DATA3 3rd-response data type
         * @param DATA4 4th-response data type
         * @param DATA5 5-th response data type
         * @param ERROR response error type
         * @param R the combined response type
         * @param E the combined error type
         */
        fun <DATA1, DATA2, DATA3, DATA4, DATA5, ERROR, R, E> combine(
            r1: Response<DATA1, ERROR>,
            r2: Response<DATA2, ERROR>,
            r3: Response<DATA3, ERROR>,
            r4: Response<DATA4, ERROR>,
            r5: Response<DATA5, ERROR>,
            mapper: (
                r1: DATA1?,
                r2: DATA2?,
                r3: DATA3?,
                r4: DATA4?,
                r5: DATA5?
            ) -> R,
            errorMapper: (
                e1: ERROR?,
                e2: ERROR?,
                e3: ERROR?,
                e4: ERROR?,
                e5: ERROR?
            ) -> E?
        ): Response<R, E> {
            val allResponsesSuccessful =
                r1.status == Status.SUCCESS && r2.status == Status.SUCCESS && r3.status == Status.SUCCESS && r4.status == Status.SUCCESS && r5.status == Status.SUCCESS

            return if (allResponsesSuccessful) {
                success(mapper(r1.data, r2.data, r3.data, r4.data, r5.data))
            } else {
                error(errorMapper(r1.error, r2.error, r3.error, r4.error, r5.error))
            }
        }

        /**
         * Combines the [r1], [r2], [r3], [r4] and [r5] into a single response of type [R] using the given [mapper].
         * It also combines the [r1]'s error, [r2]'s error, [r3]'s error, [r4]'s error and [r5]'s error into a single error of type [E] using the given [errorMapper].
         *
         * It returns a success response [Response.success] if and only if [r1], [r2], [r3], [r4] and [r5] were successful responses,
         * otherwise it returns an error response [Response.error].
         *
         * @param DATA1 1st-response data type
         * @param DATA2 2nd-response data type
         * @param DATA3 3rd-response data type
         * @param DATA4 4th-response data type
         * @param DATA5 5-th response data type
         * @param ERROR response error type
         * @param R the combined response type
         * @param E the combined error type
         */
        fun <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, ERROR, R, E> combine(
            r1: Response<DATA1, ERROR>,
            r2: Response<DATA2, ERROR>,
            r3: Response<DATA3, ERROR>,
            r4: Response<DATA4, ERROR>,
            r5: Response<DATA5, ERROR>,
            r6: Response<DATA6, ERROR>,
            mapper: (
                r1: DATA1?,
                r2: DATA2?,
                r3: DATA3?,
                r4: DATA4?,
                r5: DATA5?,
                r6: DATA6?
            ) -> R,
            errorMapper: (
                e1: ERROR?,
                e2: ERROR?,
                e3: ERROR?,
                e4: ERROR?,
                e5: ERROR?,
                e6: ERROR?
            ) -> E?
        ): Response<R, E> {
            val allResponsesSuccessful =
                r1.status == Status.SUCCESS && r2.status == Status.SUCCESS
                        && r3.status == Status.SUCCESS && r4.status == Status.SUCCESS
                        && r5.status == Status.SUCCESS && r6.status == Status.SUCCESS

            return if (allResponsesSuccessful) {
                success(mapper(r1.data, r2.data, r3.data, r4.data, r5.data, r6.data))
            } else {
                error(errorMapper(r1.error, r2.error, r3.error, r4.error, r5.error, r6.error))
            }
        }

        /**
         * map Response<D1, E> into Response<D2, E>
         */
        fun <DATA, ERROR, DATA2> fromResponse(
            res: Response<DATA, ERROR>,
            map: (DATA?) -> DATA2?
        ): Response<DATA2, ERROR> {
            return Response(res.status, map.invoke(res.data), res.error)
        }
    }
}

enum class Status {
    SUCCESS, ERROR
}