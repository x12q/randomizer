package com.x12q.randomizer.ir_plugin.backend.transformers.reporting

data class ListReportData(
    val valueType:String,
    val paramName:String?,
    val enclosingClassName:String?,
) : ReportData {

    companion object{
        private const val errCode = "LIST_ERR_1"
    }

    override fun makeMsg(): String {
        val part1 = "$errCode: Unable to generate List<$valueType>"
        val part2 =  paramName?.let{"in param [$paramName]"}
        val ofClass = enclosingClassName?.let{"in class [$enclosingClassName]"}
        val rt= listOfNotNull(part1,part2, ofClass).joinToString(" ")
        return rt
    }
}