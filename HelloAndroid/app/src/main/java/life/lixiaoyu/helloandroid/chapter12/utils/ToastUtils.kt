package life.lixiaoyu.helloandroid.chapter12.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.makeToast(@StringRes strId: Int) {
    Toast.makeText(this, strId, Toast.LENGTH_SHORT).show()
}

fun View.showSnackBar(
    text: String,
    actionText: String? = null,
    duration: Int = Snackbar.LENGTH_SHORT,
    action: (() -> Unit)? = null
) {
    val snack = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
    if (!actionText.isNullOrEmpty() && action != null) {
        snack.setAction(actionText) {
            action()
        }
    }
    snack.show()
}

fun View.showSnackBar(
    @StringRes strId: Int,
    @StringRes actionStrId: Int? = null,
    duration: Int = Snackbar.LENGTH_SHORT,
    action: (() -> Unit)? = null
) {
    val snack = Snackbar.make(this, strId, Snackbar.LENGTH_SHORT)
    if (actionStrId != null && action != null) {
        snack.setAction(actionStrId) {
            action()
        }
    }
    snack.show()
}