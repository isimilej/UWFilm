package com.android.play.uwfilm.widget

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun Context.toast(@StringRes msgResId: Int) = Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show()
inline fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
inline fun Context.toast(e: Throwable) = toast(e.message ?: e.toString())

inline fun Fragment.toast(@StringRes msgResId: Int) = requireContext().toast(msgResId)
inline fun Fragment.toast(message: CharSequence) = requireContext().toast(message)
inline fun Fragment.toast(e: Throwable) = toast(e.message ?: e.toString())

inline fun View.toast(@StringRes msgResId: Int) = context.toast(msgResId)
inline fun View.toast(message: CharSequence) = context.toast(message)
inline fun View.toast(e: Throwable) = toast(e.message ?: e.toString())
