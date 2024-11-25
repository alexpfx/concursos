package extractor

import kotlin.reflect.KClass

class ExtractorFactory {

    companion object {
        fun createExtractor(type: String, cfg: Config): Extractor {
            return when (type.lowercase()) {
                "qc" -> QCExtractor(cfg as QCConfig)
                else -> throw IllegalArgumentException("")
            }
        }
    }

}