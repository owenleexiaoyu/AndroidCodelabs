package life.lixiaoyu.helloandroid.network.entity.retrofit

class ApiException(errorCode: Int, errorMsg: String): Exception(errorMsg) {
}