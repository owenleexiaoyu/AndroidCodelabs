package life.lixiaoyu.hirestful.volley

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import life.lixiaoyu.hirestful.HiConvert
import life.lixiaoyu.hirestful.HiResponse
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

class GsonConvert: HiConvert {

    private val gson = Gson()

    override fun <T> convert(rawData: String, dataType: Type): HiResponse<T> {
        val hiResponse = HiResponse<T>()
        try {
            val jsonObject = JSONObject(rawData)
            hiResponse.code = jsonObject.optInt("errorCode")
            hiResponse.msg = jsonObject.optString("errorMsg")
            val data = jsonObject.optString("data")

            if (hiResponse.code == HiResponse.SUCCESS) {
                hiResponse.data = gson.fromJson(data, dataType)
            } else {
                hiResponse.errorData = gson.fromJson<Map<String, String>>(data, object : TypeToken<Map<String, String>>() {}.type)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            hiResponse.code = -1
            hiResponse.msg = e.message
        }
        return hiResponse
    }

}