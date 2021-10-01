
// Toast Display
fun Context.toast(message: CharSequence, isLengthLong: Boolean = true) =
    Toast.makeText(
        this, message, if (isLengthLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    ).show()

fun Context.toast(@StringRes message: Int, isLengthLong: Boolean) {
    toast(getString(message), isLengthLong)
}

fun Context.simpleAlert(@StringRes message: Int) {
    MaterialDialog(this).show {
        message(res = message)
        positiveButton(R.string.ok)
        message(res = message)
    }
}

fun Context.alert(
    @StringRes titleRes: Int? = null, @StringRes message: Int, @StringRes positiveBtn: Int, @StringRes negativeBtn: Int,
    action: () -> Unit
) {
    MaterialDialog(this).show {
        titleRes?.let { title(it) }
        message(res = message)
        positiveButton(positiveBtn, click = object : DialogCallback {
            override fun invoke(p1: MaterialDialog) {
                action()
            }
        })
        negativeButton(negativeBtn)
    }
}

tailrec fun Context.activity(): Activity? = when {
  this is Activity -> this
  else -> (this as? ContextWrapper)?.baseContext?.activity()
}

fun Float.roundOff(): String {
    val df = DecimalFormat("##.##")
    return df.format(this)
}


fun Int.appendOrdinal(): String {
    return ordinalOf(this)
}

private fun ordinalOf(i: Int) = "$i" + if (i % 100 in 11..13) "th" else when (i % 10) {
    1 -> "st"
    2 -> "nd"
    3 -> "rd"
    else -> "th"
}

fun Any.logd(message: String?) {
    Log.d(this::class.java.simpleName, "" + message)
}

fun Any.loge(message: String?) {
    Log.e(this::class.java.toString(), "" + message)
}
// Toast Display
fun Context.toast(message: CharSequence, isLengthLong: Boolean = true) =
    Toast.makeText(
        this, message, if (isLengthLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    ).show()

fun Context.toast(@StringRes message: Int, isLengthLong: Boolean) {
    toast(getString(message), isLengthLong)
}

fun Context.simpleAlert(@StringRes message: Int) {
    MaterialDialog(this).show {
        message(res = message)
        positiveButton(R.string.ok)
        message(res = message)
    }
}

fun Context.alert(
    @StringRes titleRes: Int? = null, @StringRes message: Int, @StringRes positiveBtn: Int, @StringRes negativeBtn: Int,
    action: () -> Unit
) {
    MaterialDialog(this).show {
        titleRes?.let { title(it) }
        message(res = message)
        positiveButton(positiveBtn, click = object : DialogCallback {
            override fun invoke(p1: MaterialDialog) {
                action()
            }
        })
        negativeButton(negativeBtn)
    }
}

tailrec fun Context.activity(): Activity? = when {
  this is Activity -> this
  else -> (this as? ContextWrapper)?.baseContext?.activity()
}

fun Float.roundOff(): String {
    val df = DecimalFormat("##.##")
    return df.format(this)
}


fun Int.appendOrdinal(): String {
    return ordinalOf(this)
}

private fun ordinalOf(i: Int) = "$i" + if (i % 100 in 11..13) "th" else when (i % 10) {
    1 -> "st"
    2 -> "nd"
    3 -> "rd"
    else -> "th"
}

fun Any.logd(message: String?) {
    Log.d(this::class.java.simpleName, "" + message)
}

fun Any.loge(message: String?) {
    Log.e(this::class.java.toString(), "" + message)
}
