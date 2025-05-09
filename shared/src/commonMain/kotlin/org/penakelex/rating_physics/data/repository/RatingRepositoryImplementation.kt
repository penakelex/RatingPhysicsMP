package org.penakelex.rating_physics.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.penakelex.rating_physics.domain.repository.Platform
import org.penakelex.rating_physics.domain.model.RatingData
import org.penakelex.rating_physics.domain.repository.CipheredFileType
import org.penakelex.rating_physics.domain.repository.RatingRepository

class RatingRepositoryImplementation : RatingRepository {
    companion object {
        private const val BASE_URL = "http://45.90.46.187:8000/rating_physics"
    }

    private val client = HttpClient(CIO)

    @Throws(InvalidPasswordException::class, CanNotAccessServerException::class)
    override suspend fun getRatingDataByPassword(
        password: UInt,
        fileBytes: ByteArray,
        fileType: CipheredFileType,
    ): RatingData {
        val response = try {
            client.post("$BASE_URL/file/decipher") {
                setBody(
                    MultiPartFormDataContent(
                        parts = formData {
                            append(
                                key = "file",
                                value = fileBytes,
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        when (fileType) {
                                            CipheredFileType.Rpf -> ContentType.Application.OctetStream
                                            CipheredFileType.Zip -> ContentType.Application.Zip
                                        }
                                    )
                                }
                            )
                            append(
                                key = "password",
                                value = password.toString(),
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, ContentType.Application.Json)
                                }
                            )
                        }
                    )
                )

                contentType(ContentType.MultiPart.Mixed)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            throw CanNotAccessServerException()
        }

        if (response.status.value != 200) {
            throw InvalidPasswordException(response.bodyAsText())
        }

        return Json.decodeFromString(response.bodyAsText())
    }

    @Throws(CanNotAccessServerException::class)
    override suspend fun getLatestVersion(platform: Platform): String {
        val response = try {
            client.get(
                when (platform) {
                    Platform.Android -> "$BASE_URL/android/latest_version"
                    Platform.Desktop -> "$BASE_URL/desktop/latest_version"
                }
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            throw CanNotAccessServerException()
        }

        if (response.status.value != 200)
            throw NoAppVersionsFoundException(response.bodyAsText())

        return response.bodyAsText()
    }
}