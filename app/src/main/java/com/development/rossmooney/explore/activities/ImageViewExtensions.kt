package com.development.rossmooney.explore.activities

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by rossmooney on 11/20/17.
 */

fun ImageView.loadUrl(url: String) {
    Picasso.with(context).load(url).into(this)
}