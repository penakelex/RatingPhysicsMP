package org.penakelex.rating_physics.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingData(
    @SerialName("full_name") val fullName: String,
    val group: String,
    val summary: Float,
    @SerialName("rating_group") val ratingGroup: UByte,
    @SerialName("rating_flow") val ratingFlow: UShort,
    val colloquium: UByte?,
    @SerialName("cgt_cw") val cgtCw: Float,
    val lw: UByte?,
    val it: UByte?,
    val essay: UByte?,
    val nirs: UByte?,
    @SerialName("sum_practice") val sumPractice: UShort,
    val omissions: UByte,
    @SerialName("practical_lessons") val practicalLessons: List<PracticalLesson>,
    val cgts: List<UByte?>,
)