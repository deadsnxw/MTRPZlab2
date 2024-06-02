import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class ProgramTest {

    @BeforeEach
    fun setup() {
        Program.text = ""
        Program.path = ""
        Program.name = ""
    }

    @Test
    fun testIsPunctuation() {
        assertTrue(Program.run { '.'.isPunctuation() })
        assertTrue(Program.run { ','.isPunctuation() })
        assertFalse(Program.run { 'a'.isPunctuation() })
        assertFalse(Program.run { '1'.isPunctuation() })
    }

    @Test
    fun testChangeTextItalic() {
        Program.text = "_italic_"
        Program.changeText()
        assertEquals("<i>italic</i>", Program.text)
    }

    @Test
    fun testChangeTextBold() {
        Program.text = "**bold**"
        Program.changeText()
        assertEquals("<b>bold</b>", Program.text)
    }

    @Test
    fun testChangeTextMono() {
        Program.text = "`mono`"
        Program.changeText()
        assertEquals("<tt>mono</tt>", Program.text)
    }

    @Test
    fun testChangeTextPre() {
        Program.text = "```pref```"
        Program.changeText()
        assertEquals("<pre>pref</pre>", Program.text)
    }

    @Test
    fun testCheckParag() {
        Program.text = "First paragraph.\n\nSecond paragraph."
        Program.checkParag()
        assertEquals("<p>First paragraph.</p><p>Second paragraph.</p>", Program.text)
    }

    @Test
    fun testCheckIncludedItalic() {
        Program.text = "<i>text</i>"
        assertDoesNotThrow { Program.checkIncluded() }
    }

    @Test
    fun testCheckIncludedBold() {
        Program.text = "<b>text</b>"
        assertDoesNotThrow { Program.checkIncluded() }
    }

    @Test
    fun testCheckIncludedMono() {
        Program.text = "<tt>text</tt>"
        assertDoesNotThrow { Program.checkIncluded() }
    }

    @Test
    fun testAddHTMLStructure() {
        Program.text = "Body content"
        Program.addHTMLStructure()
        val expected = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
            </head>
            <body>
            Body content
            </body>
            </html>
        """.trimIndent()
        assertEquals(expected, Program.text)
    }

    @Test
    fun testCreateHTML() {
        Program.text = "Test content"
        Program.path = System.getProperty("java.io.tmpdir")
        Program.name = "test"
        Program.createHTML()
        val filePath = "${Program.path}\\${Program.name}.html"
        val file = File(filePath)
        assertTrue(file.exists())
        assertEquals("Test content", file.readText())
    }

    @Test
    fun testMain() {
        val args = arrayOf("test.md")
        Program.main(args)
        // Verify the expected output - this part might need adjustments based on actual file creation and content.
        val expectedFile = File("${Program.path}\\${Program.name}.html")
        assertTrue(expectedFile.exists())
        expectedFile.deleteOnExit()  // Clean up
    }
}
