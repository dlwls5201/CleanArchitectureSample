package com.example.toyproject.base.ext

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setHtmlText")
fun setHtmlText(textView: TextView, htmlString: String?) {
    if (htmlString.isNullOrEmpty()) {
        return
    }
    textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(htmlString)
    }
}