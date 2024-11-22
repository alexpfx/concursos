package extractor

interface Extractor<in C : IConfig, out R : IResult> {
    fun extract(config: C): R
}

interface IConfig {

}

interface IResult {

}

class Config : IConfig{

}
class Result : IResult{

}