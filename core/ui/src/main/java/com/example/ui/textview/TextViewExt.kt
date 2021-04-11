package com.example.ui.textview

import android.widget.TextView

fun TextView.setText(delegate: TextDelegate?) = when (delegate) {
    is PureText -> text = delegate.text
    is ResourceText -> setText(delegate.resId)
     null -> text = null
}