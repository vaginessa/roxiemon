package eu.acolombo.roxiemon.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.load(url: String) = Glide.with(context).load(url).into(this)

fun ImageView.load(@DrawableRes resId: Int) = Glide.with(context).load(resId).into(this)