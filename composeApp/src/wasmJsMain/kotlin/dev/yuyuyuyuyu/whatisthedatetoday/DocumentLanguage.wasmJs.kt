package dev.yuyuyuyuyu.whatisthedatetoday

import kotlinx.browser.document

actual fun setDocumentLanguage(language: String) {
    document.documentElement?.setAttribute("lang", language)
}
