package extractor

interface Extractor {
    suspend fun extract(): Result
}

interface Config {

}

interface Result {

}
