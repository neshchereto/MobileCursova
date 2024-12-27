package com.neshchereto.abitsearch.data.mappers

import com.neshchereto.abitsearch.data.remote.model.GoogleSheetsDto
import com.neshchereto.abitsearch.domain.model.Entrant
import com.neshchereto.abitsearch.domain.model.EntrantTable

object EntrantMapper {

    fun mapToDomain(googleSheetsDto: GoogleSheetsDto): EntrantTable {


        val columnsDto = googleSheetsDto.table?.cols
        val columns = Entrant(
            name = columnsDto?.get(1)?.label,
            qualificationLevel = columnsDto?.get(0)?.label,
            status = columnsDto?.get(2)?.label,
            positionNumber = columnsDto?.get(3)?.label,
            priority = columnsDto?.get(4)?.label,
            numberOfPlaces = columnsDto?.get(5)?.label,
            competitiveScore = columnsDto?.get(6)?.label,
            averageScoreOfEducation = columnsDto?.get(7)?.label,
            componentsOfCompetitiveScore = columnsDto?.get(8)?.label,
            nameOfInstitution = columnsDto?.get(9)?.label,
            faculty = columnsDto?.get(10)?.label,
            specialty = columnsDto?.get(11)?.label,
            quota = columnsDto?.get(12)?.label,
            documentsSubmitted = columnsDto?.get(13)?.label,
            year = columnsDto?.get(14)?.label
        )
        val rowsDto = googleSheetsDto.table?.rows
        val rows = mutableListOf<Entrant>()
        rowsDto?.forEach {
            println("ELEMENT DTO"+it)
            rows.add(
                Entrant(
                    name = (if(it?.c?.get(1)?.f!=null) it?.c?.get(1)?.f else it?.c?.get(1)?.v).toString(),
                    qualificationLevel = (if(it?.c?.get(0)?.f!=null) it?.c?.get(0)?.f else it?.c?.get(0)?.v).toString(),
                    status = (if(it?.c?.get(2)?.f!=null) it?.c?.get(2)?.f else it?.c?.get(2)?.v).toString(),
                    positionNumber = (if(it?.c?.get(3)?.f!=null) it?.c?.get(3)?.f else it?.c?.get(3)?.v).toString(),
                    priority = (if(it?.c?.get(4)?.f!=null) it?.c?.get(4)?.f else it?.c?.get(4)?.v).toString(),
                    numberOfPlaces = (if(it?.c?.get(5)?.f!=null) it?.c?.get(5)?.f else it?.c?.get(5)?.v).toString(),
                    competitiveScore = (if(it?.c?.get(6)?.f!=null) it?.c?.get(6)?.f else it?.c?.get(6)?.v).toString(),
                    averageScoreOfEducation = (if(it?.c?.get(7)?.f!=null) it?.c?.get(7)?.f else it?.c?.get(7)?.v).toString(),
                    componentsOfCompetitiveScore = (if(it?.c?.get(8)?.f!=null) it?.c?.get(8)?.f else it?.c?.get(8)?.v).toString(),
                    nameOfInstitution = (if(it?.c?.get(9)?.f!=null) it?.c?.get(9)?.f else it?.c?.get(9)?.v).toString(),
                    faculty = (if(it?.c?.get(10)?.f!=null) it?.c?.get(10)?.f else it?.c?.get(10)?.v).toString(),
                    specialty = (if(it?.c?.get(11)?.f!=null) it?.c?.get(11)?.f else it?.c?.get(11)?.v).toString(),
                    quota = (if(it?.c?.get(12)?.f!=null) it?.c?.get(12)?.f else it?.c?.get(12)?.v).toString(),
                    documentsSubmitted = (if(it?.c?.get(13)?.f!=null) it?.c?.get(13)?.f else it?.c?.get(13)?.v).toString(),
                    year = (if(it?.c?.get(14)?.f!=null) it?.c?.get(14)?.f else it?.c?.get(14)?.v).toString()
                )
            )
        }
        return EntrantTable(columns,rows)
    }

}