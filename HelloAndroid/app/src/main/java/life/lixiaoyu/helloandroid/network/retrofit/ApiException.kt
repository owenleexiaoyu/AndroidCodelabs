package life.lixiaoyu.helloandroid.network.retrofit

class ApiException(errorCode: Int, errorMsg: String): Exception(errorMsg) {
}