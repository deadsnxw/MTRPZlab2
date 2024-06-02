import java.io.File
import java.lang.Exception

object Program {
    var text = ""
    var path = ""
    var name = ""

    fun Char.isPunctuation(): Boolean {
        val punctuationSet = setOf('.', ',', ';', ':', '!', '?', '-', '_', '"', '\'',
            '(', ')', '[', ']', '{', '}', '/', '\\', '|',
            '<', '>', '~', '`', '@', '#', '$', '%', '^', '&', '*')
        return this in punctuationSet
    }

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            filePath()
            checkParag()
            changeText()
            checkIncluded()
            addHTMLStructure()
            createHTML()
            println("File successfully created!")
        } catch (ex: Exception) {
            println("Error: $ex")
        }
    }

    private fun filePath() {
        println("Enter the path to the text file:\nExample: \"C:\\Documents\\Super Secret\\file.md\"")
        val filePath = readLine() ?: ""
        if (File(filePath).extension == "md") {
            if (File(filePath).exists()) {
                text = File(filePath).readText()
                path = File(filePath).parent + "\\"
                name = File(filePath).nameWithoutExtension
            } else {
                println("File does not exist. Please try again.")
                filePath()
            }
        } else {
            println("The extension must be .md. Please try again.")
            filePath()
        }
    }

    fun changeText() {
        for (i in text.indices) {
            checkItalic(i)
            checkBold(i)
            checkPref(i)
            checkMono(i)
        }
    }

    private fun checkItalic(i: Int) {
        if (text[i] == '_') {
            if (i == 0 || (i > 0 && !text[i - 1].isLetterOrDigit())) {
                if (i < text.length - 1) {
                    if (text[i + 1].isLetterOrDigit()) {
                        text = text.removeRange(i, i + 1).replaceRange(i, i, "<i>")
                        closeItalic(i)
                    } else if (text[i + 1].isPunctuation() || text[i + 1] == '`') {
                        text = text.removeRange(i, i + 1).replaceRange(i, i, "<i>")
                        closeItalic(i)
                    }
                }
            }
        }
    }

    private fun closeItalic(i: Int) {
        var j = i + 1
        while (j < text.length) {
            if (text[j] == '_') {
                if (j == text.length - 1 || (j < text.length - 1 && !text[j + 1].isLetterOrDigit())) {
                    if (j > 0) {
                        if (text[j - 1].isLetterOrDigit()) {
                            text = text.removeRange(j, j + 1).replaceRange(j, j, "</i>")
                            break
                        } else if (text[j - 1].isPunctuation() || text[j - 1] == '`' || text[j - 1] == '>') {
                            text = text.removeRange(j, j + 1).replaceRange(j, j, "</i>")
                            break
                        }
                    }
                }
            } else if (j == text.length - 1) {
                throw Exception("Unclosed Punctuation")
            }
            j++
        }
    }

    private fun checkBold(i: Int) {
        if (text[i] == '*') {
            if (i < text.length - 2 && text[i + 1] == '*') {
                if (text[i + 2].isLetterOrDigit()) {
                    text = text.removeRange(i, i + 2).replaceRange(i, i, "<b>")
                    closeBold(i)
                } else if (text[i + 2].isPunctuation() || text[i + 2] == '`') {
                    text = text.removeRange(i, i + 2).replaceRange(i, i, "<b>")
                    closeBold(i)
                }
            }
        }
    }

    private fun closeBold(i: Int) {
        var j = i + 1
        while (j < text.length) {
            if (text[j] == '*') {
                if (j > 1 && text[j - 1] == '*') {
                    if (text[j - 2].isLetterOrDigit()) {
                        text = text.removeRange(j - 1, j + 1).replaceRange(j - 1, j - 1, "</b>")
                        break
                    } else if (text[j - 2].isPunctuation() || text[j - 2] == '`' || text[j - 2] == '>') {
                        text = text.removeRange(j - 1, j + 1).replaceRange(j - 1, j - 1, "</b>")
                        break
                    }
                }
            } else if (j == text.length - 1) {
                throw Exception("Unclosed Punctuation")
            }
            j++
        }
    }

    private fun checkMono(i: Int) {
        if (text[i] == '`') {
            if (i < text.length - 1) {
                if (text[i + 1].isLetterOrDigit()) {
                    text = text.removeRange(i, i + 1).replaceRange(i, i, "<tt>")
                    closeMono(i)
                } else if (text[i + 1].isPunctuation() || text[i + 1] == '`') {
                    text = text.removeRange(i, i + 1).replaceRange(i, i, "<tt>")
                    closeMono(i)
                }
            }
        }
    }

    private fun closeMono(i: Int) {
        var j = i + 1
        while (j < text.length) {
            if (text[j] == '`') {
                if (j > 0) {
                    if (text[j - 1].isLetterOrDigit()) {
                        text = text.removeRange(j, j + 1).replaceRange(j, j, "</tt>")
                        break
                    } else if (text[j - 1].isPunctuation() || text[j - 1] == '`' || text[j - 1] == '>') {
                        text = text.removeRange(j, j + 1).replaceRange(j, j, "</tt>")
                        break
                    }
                }
            } else if (j == text.length - 1) {
                throw Exception("Unclosed Punctuation")
            }
            j++
        }
    }

    private fun checkPref(i: Int) {
        if (text[i] == '`') {
            if (i < text.length - 3 && text[i + 1] == '`' && text[i + 2] == '`') {
                text = text.removeRange(i, i + 3).replaceRange(i, i, "<pre>")
                closePref(i)
            }
        }
    }

    private fun closePref(i: Int) {
        var j = i + 1
        while (j < text.length) {
            if (text[j] == '`') {
                if (j > 2 && text[j - 1] == '`' && text[j - 2] == '`') {
                    text = text.removeRange(j - 2, j + 1).replaceRange(j - 2, j - 2, "</pre>")
                    break
                }
            } else if (j == text.length - 1) {
                throw Exception("Unclosed Punctuation")
            }
            j++
        }
    }

    fun checkParag() {
        text = "<p>$text"
        closeParag(0)
    }

    private fun closeParag(i: Int) {
        var j = i
        while (j < text.length) {
            if (text[j] == '\n') {
                if (j < text.length - 3 && text[j + 2] == '\n') {
                    text = text.removeRange(j - 1, j + 3).replaceRange(j - 1, j - 1, "</p><p>")
                    closeParag(j)
                    break
                } else if (j == text.length - 3 && text[j + 2] == '\n') {
                    text = text.removeRange(j - 1, j + 3).replaceRange(j - 1, j - 1, "</p>")
                    break
                } else if (j == text.length - 1) {
                    text = text.removeRange(j - 1, j + 1).replaceRange(j - 1, j - 1, "</p>")
                    break
                }
            } else if (j == text.length - 1) {
                text += "</p>"
                break
            }
            j++
        }
    }

    fun checkIncluded() {
        for (i in text.indices) {
            when {
                i < text.length - 2 && text.substring(i, i + 3) == "<i>" -> checkIncluded_I(i)
                i < text.length - 2 && text.substring(i, i + 3) == "<b>" -> checkIncluded_B(i)
                i < text.length - 3 && text.substring(i, i + 4) == "<tt>" -> checkIncluded_TT(i)
            }
        }
    }

    private fun checkIncluded_I(i: Int) {
        var j = i + 2
        while (j < text.length) {
            when {
                j < text.length - 3 && text.substring(j, j + 4) == "</i>" -> return
                j < text.length - 2 && text.substring(j, j + 3) == "<b>" -> throw Exception("Included Punctuation")
                j < text.length - 3 && text.substring(j, j + 4) == "<tt>" -> throw Exception("Included Punctuation")
            }
            j++
        }
    }

    private fun checkIncluded_B(i: Int) {
        var j = i + 2
        while (j < text.length) {
            when {
                j < text.length - 3 && text.substring(j, j + 4) == "</b>" -> return
                j < text.length - 2 && text.substring(j, j + 3) == "<i>" -> throw Exception("Included Punctuation")
                j < text.length - 3 && text.substring(j, j + 4) == "<tt>" -> throw Exception("Included Punctuation")
            }
            j++
        }
    }

    private fun checkIncluded_TT(i: Int) {
        var j = i + 2
        while (j < text.length) {
            when {
                j < text.length - 4 && text.substring(j, j + 5) == "</tt>" -> return
                j < text.length - 2 && text.substring(j, j + 3) == "<b>" -> throw Exception("Included Punctuation")
                j < text.length - 4 && text.substring(j, j + 3) == "<i>" -> throw Exception("Included Punctuation")
            }
            j++
        }
    }

    fun createHTML() {
        val filePath = "$path\\$name.html"
        File(filePath).writeText(text)
    }

    fun addHTMLStructure() {
        text =
            """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
$text
</body>
</html>"""
    }
}
