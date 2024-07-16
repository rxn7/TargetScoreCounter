package lol.rxn.targetscorecounter

data class ResultData(val scores: Array<Int>) {
    fun getTotalScore(): Int {
        return scores.sum()
    }

    fun getMaxPossibleScore(): Int {
        return 10 * scores.size
    }

    override fun hashCode(): Int {
        return scores.contentHashCode()
    }
}
