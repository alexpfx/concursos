package com.github.ax_as.extrator_provas.domain.extractor.cebraspe

import com.github.ax_as.extrator_provas.domain.extractor.text.cebraspe.CebraspeGabaritoExtractor
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CebraspeGabaritoExtractorTest {
    val extractor = CebraspeGabaritoExtractor()
    val text = """SERVIÇO FEDERAL DE PROCESSAMENTO DE DADOS – SERPRO
CONCURSO PÚBLICO PARA O PREENCHIMENTO DE VAGAS E A FORMAÇÃO DE CADASTRO DE RESERVA 
PARA O CARGO DE ANALISTA – ESPECIALIZAÇÃO: TECNOLOGIA
EDITAL Nº 1 – SERPRO, DE 18 DE ABRIL DE 2023
Aplicação: 23/07/2023
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
C C E E C E E C C E E X C C E C E C E C
21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40
E C C E E X C E C C C E E C E E C E C C
41 42 43 44 45 46 47 48 49 50 0 0 0 0 0 0 0 0 0 0
C E E C C E E C C C 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
846_SERPRO_23_CB1_01
CARGO DE ANALISTA – ESPECIALIZAÇÃO: TECNOLOGIA
Item
GABARITOS OFICIAIS DEFINITIVOS
Obs.: ( X ) item anulado.
Item
Gabarito
Gabarito
Item
Gabarito

51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70
E C E C E E E C C X C E C C E X X E E E
71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90
C X C E C C E C C C E E C E E E C C X C
91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 108 109 110
X C X C E C E C C E E C E E C X X C E C
111 112 113 114 115 116 117 118 119 120 0 0 0 0 0 0 0 0 0 0
C E E C E C C E E C 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
Item
Obs.: ( X ) item anulado.
GABARITOS OFICIAIS DEFINITIVOS
SERVIÇO FEDERAL DE PROCESSAMENTO DE DADOS – SERPRO
CONCURSO PÚBLICO PARA O PREENCHIMENTO DE VAGAS E A FORMAÇÃO DE CADASTRO DE RESERVA 
PARA O CARGO DE ANALISTA – ESPECIALIZAÇÃO: TECNOLOGIA
EDITAL Nº 1 – SERPRO, DE 18 DE ABRIL DE 2023
Aplicação: 23/07/2023
846_SERPRO_23_001_01
Item
CARGO DE ANALISTA – ESPECIALIZAÇÃO: TECNOLOGIA
Gabarito
Item
Gabarito
Item
Gabarito
Gabarito
"""

    @Test
    fun extract() {
        val r = extractor.extractMap(text)
        val expected =
            "C C E E C E E C C E E X C C E C E C E C " +
                    "E C C E E X C E C C C E E C E E C E C C " +
                    "C E E C C E E C C C E C E C E E E C C X " +
                    "C E C C E X X E E E C X C E C C E C C C " +
                    "E E C E E E C C X C X C X C E C E C C E " +
                    "E C E E C X X C E C C E E C E C C E E C"

        val splitExpected = expected
            .split(" ").map { it[0] }
        assertEquals(120, r.size)
        assertEquals(120, splitExpected.size)

        splitExpected
            .mapIndexed { i, g -> (i + 1) to g }
            .forEachIndexed { i, a ->
                assertEquals(a.first, i+1)
                assertEquals(a.second, r[i+1])
            }
    }
}