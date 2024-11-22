import com.github.ax_as.extrator_provas.TestHelper
import com.github.ax_as.extrator_provas.TestHelper.Companion.p1PdfOriginal
import com.github.ax_as.extrator_provas.TestHelper.Companion.testFilesDir
import com.github.ax_as.extrator_provas.domain.extractor.DITestLifeCycle
import com.github.ax_as.extrator_provas.domain.extractor.pdf.PDFBoxExtractor
import com.github.ax_as.extrator_provas.domain.extractor.pdf.PDFExtractor
import net.bytebuddy.matcher.ElementMatchers.named
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.io.path.absolutePathString

class GenerateTestFilesTest  {
    private lateinit var textExtractor: PDFExtractor
    private lateinit var gabaritoExtractor: PDFExtractor

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun generateTxts() {
        val helper = TestHelper(testFilesDir)
        helper.generateTextFiles(
            textExtractor,
            pdfFiles = arrayOf(p1PdfOriginal.absolutePathString())
        )
        helper.generateTextFiles(
            gabaritoExtractor,
            pdfFiles = arrayOf(TestHelper.g1PdfOriginal.absolutePathString())
        )

    }
}