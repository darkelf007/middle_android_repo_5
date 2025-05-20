package com.yandex.practicum.middle_homework_5.gradle_plugins

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

abstract class FindUntranslatedStringsTask: DefaultTask() {
    @TaskAction
    fun findUntranslatedStrings() {
        val resDir = File(project.projectDir, "src/main/res")
        val strings = File(resDir, "values/strings.xml")

        val stringsFromXml = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder()
            .parse(strings)
            .getElementsByTagName("string")

        val stringIdentities =  stringsFromXml.let { nodeList ->
            (0 until nodeList.length).map { i ->
                val node = nodeList.item(i)
                val name = node.attributes?.getNamedItem("name")?.nodeValue ?: ""
                name
            }
        }

        val missingStrings = mutableMapOf<String,MutableList<String>>()

        resDir.listFiles { file -> file.isDirectory && file.name.startsWith("values-") }
            ?.forEach { localeDir ->
                val locale = localeDir.name
                val translatedFile = File(localeDir, "strings.xml")

                if (translatedFile.exists()) {
                    val translatedStrings = DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .parse(translatedFile)
                        .getElementsByTagName("string")

                    val translatedIdentities = (0 until translatedStrings.length).mapNotNull { i ->
                        translatedStrings.item(i).attributes?.getNamedItem("name")?.nodeValue
                    }.toSet()

                    val missingForLocale = stringIdentities.filterNot { it in translatedIdentities }
                    if (missingForLocale.isNotEmpty()) {
                        missingStrings[locale] = missingForLocale.toMutableList()
                    }
                } else {
                    missingStrings[locale] = stringIdentities.toMutableList()
                }
            }

        if (missingStrings.isNotEmpty()) {
            val stringBuilderErrorText = StringBuilder("Missing translations").append(System.lineSeparator())
            missingStrings.forEach { missing ->
                stringBuilderErrorText
                    .append("=== ${missing.key} ===")
                    .append(System.lineSeparator())
                    .append(missing.value.joinToString(separator = System.lineSeparator()))
                    .append(System.lineSeparator())

            }
            throw GradleException(stringBuilderErrorText.toString())
        }
    }
}