package com.neshchereto.abitsearch.domain.usecase

class ValidateInputUseCase {
    fun isValid(input:String):Boolean{
        val pattern = Regex(
            "^([А-ЩЬЮЯЇІЄҐ][а-щьюяїієґ']+\\s[А-ЩЬЮЯЇІЄҐ]\\.\\s?[А-ЩЬЮЯЇІЄҐ]\\.)(?:\\s(\\d+(?:\\.\\d+)?))?(?:\\s(\\d+(?:\\.\\d+)?))?\$"
        )
        val isRegedexValid =  pattern.matches(input)
        if(isRegedexValid){
            val matchResult = pattern.matchEntire(input.trim())
            val name = matchResult?.groupValues?.get(1)
            val g2 = matchResult?.groupValues?.get(2)
            val g3 = matchResult?.groupValues?.get(3)

            val hasG2 = g2?.isNotBlank() ?: false
            val hasG3 = g3?.isNotBlank() ?: false

            if (hasG2 && hasG3) {
                return g2?.contains('.') == true
            }else{
                return  g2?.contains('.')==false
            }
        }else{
            return false
        }
    }
}