package com.example.ui.textview

import androidx.annotation.StringRes

sealed class TextDelegate
data class ResourceText(@StringRes val resId: Int): TextDelegate()
data class PureText(val text: String): TextDelegate()