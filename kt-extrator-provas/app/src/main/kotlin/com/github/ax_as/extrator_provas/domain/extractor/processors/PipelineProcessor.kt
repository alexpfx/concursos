package com.github.ax_as.extrator_provas.domain.extractor.processors

typealias MatchConvert<T> = (MatchResult) -> T
typealias MatcherFunc = (MatchResult) -> CharSequence
typealias StringMatchFunc = (String) -> String
typealias MatchReplacer = Pair<Regex, MatcherFunc>
typealias MatchReplacerConverter <T> = Pair<Regex, MatchConvert<T>>

interface PipelineProcessor<I, O> {

    fun <R> pipe(source: PipelineProcessor<O, R>): PipelineProcessor<I, R> {
        return object : PipelineProcessor<I, R> {
            override fun process(value: I): R {
                return source.process(this@PipelineProcessor.process(value))
            }
        }
    }

    fun process(value: I): O
    interface Listener<I, O> {
        fun call(input: I, output: O)
    }

    companion object {
        fun <I, O> of(source: PipelineProcessor<I, O>): PipelineProcessor<I, O> {
            return source
        }

    }

}

fun String.mlDotAllRegex(replace: String = ""): Pair<Regex, String> = Pair(
    trimIndent().toRegex(
        setOf(
            RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE
        )
    ), replace
)

fun String.mlDotAllReplacer(replace: MatcherFunc = { "" }): Pair<Regex, MatcherFunc> =
    Pair(
        toRegex(
            setOf(
                RegexOption.DOT_MATCHES_ALL,
                RegexOption.MULTILINE,
            )
        ), replace
    )

fun String.mlRepRegex(replace: String = ""): Pair<Regex, String> = Pair(
    toRegex(
        setOf(
            RegexOption.MULTILINE
        )
    ), replace
)

fun String.mlRegex(): Regex = toRegex(
    setOf(
        RegexOption.MULTILINE
    )
)


fun String.mlRegex(replace: String = ""): Pair<Regex, String> = Pair(
    toRegex(
        setOf(
            RegexOption.MULTILINE
        )
    ), replace
)

fun String.mlDotAllRegex(): Regex = toRegex(
    setOf(
        RegexOption.DOT_MATCHES_ALL,
        RegexOption.MULTILINE,
    )
)