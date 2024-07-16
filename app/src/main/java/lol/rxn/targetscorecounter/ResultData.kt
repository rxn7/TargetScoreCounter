package lol.rxn.targetscorecounter

data class ResultData(var scores: ArrayList<Int>) {
    fun getTotalScore(): Int {
        return scores.sum()
    }

    fun getMaxPossibleScore(): Int {
        return 10 * scores.size
    }

    override fun hashCode(): Int {
        return scores.hashCode()
    }
}
