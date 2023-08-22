package ktepin.penzasoft.artgallery.data.repository

import android.util.Log
import ktepin.penzasoft.artgallery.domain.model.RequestError
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.RequestSuccess
import okhttp3.internal.http2.ErrorCode
import retrofit2.Call
import retrofit2.Response

abstract class AbstractRepository {
    abstract val tag: String

    protected fun <T> executeRequest(call : Call<T>): ApiRequestResult<T> {
        var res : Response<T>? = null;
        try {
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
            return RequestError(ErrorCode.CONNECT_ERROR.toString())
        }
        return res?.let { result ->

            result.body()?.let { body ->
                RequestSuccess(body)
            } ?: RequestError(ErrorCode.INTERNAL_ERROR.toString())

        } ?: RequestError(ErrorCode.INTERNAL_ERROR.toString())

    }
}