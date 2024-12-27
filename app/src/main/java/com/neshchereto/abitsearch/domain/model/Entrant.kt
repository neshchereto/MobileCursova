package com.neshchereto.abitsearch.domain.model

data class EntrantTable(
    val columnsName:Entrant,
    val rows:List<Entrant>
)
data class Entrant(
    val qualificationLevel:String?,
    val name:String?,
    val status:String?,
    val positionNumber:String?,
    val priority:String?,
    val numberOfPlaces:String?,
    val competitiveScore:String?,
    val averageScoreOfEducation:String?,
    val componentsOfCompetitiveScore:String?,
    val nameOfInstitution:String?,
    val faculty:String?,
    val specialty:String?,
    val quota:String?,
    val documentsSubmitted:String?,
    val year:String?
)
