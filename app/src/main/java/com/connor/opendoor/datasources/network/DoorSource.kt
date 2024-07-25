package com.connor.opendoor.datasources.network

import android.util.Log
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.connor.opendoor.utlis.logCat
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class DoorSource @Inject constructor(
    private val client: HttpClient
) {

    private val postBody = "oNMiegtPRmqwUUPFO7HS+bNtoItbNFp2akHPt79zeDOa+DFXP6CdcgH1EpnEXDQk3C3B8Sg5zaYc2vIYCktlFkdwsN53mucxACnt0bBQcOgJ0S/IZSnNtmH26C7+ZLR+OZs40tZiSSeLIlW5t+b9ijwXLVwKhh/+dBteymbJNP59ThfJmoe+58ySWQ7Ag0hR6qotOeapJ2Nhl9oNJ3e11Cj7jtC1uJlsg9p+m2Fdr55kc5p/rMs9gbCqpsFcOa74"

    private suspend fun openDoor() = client.post("https://zhgm.yrcid.com:15615/api/index.php/v18/doors/open") {
        headers {
            append("Authorization", "Basic bGFqZm55cXhyazZ1aHRwM3NkNG0wejd3MTk1djJiZW86OHljYXF1aGQxeDRmMnozZ2U2dzkwcHJsNW1qb3R2bmk=")
            append("doordu-system", """{"app_version":"2.0.7.005","system_version":"12","system_models":"GM1910","system_type":0}""")
            append("Package-Name", "com.guangmingzmt.oem")
            append("Accept-Language", "zh-CN")
            append("X-Enc-Level", "2")
            append("Content-Type", "text/plain; charset=utf-8")
        }
        setBody(postBody)
    }

    suspend fun open() = either {
        val response = openDoor()
        "openDoor response: $response".logCat()
        ensure(response.status == HttpStatusCode.OK) {
            raise("Error HttpStatusCode: ${response.status}")
        }
        response.bodyAsText().also {
            ensure(!it.startsWith("{")) {
                raise("Error content: $it")
            }
        }
    }
}