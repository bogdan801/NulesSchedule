package com.prosto_key.nulesschedule.domain.model

data class Teacher(
    val teacherID: Int,
    val fullName: String,
    val teacherOfSubjectID: Int? = null,
    val isLector: Boolean? = null,
    val phoneNumber: String?,
    val email: String?,
    val additionalInfo: String?
){
    override fun equals(other: Any?): Boolean {
        val otherTeacher = other as Teacher
        return this.fullName       == otherTeacher.fullName       &&
               this.isLector       == otherTeacher.isLector       &&
               this.phoneNumber    == otherTeacher.phoneNumber    &&
               this.email          == otherTeacher.email          &&
               this.additionalInfo == otherTeacher.additionalInfo
    }
}
