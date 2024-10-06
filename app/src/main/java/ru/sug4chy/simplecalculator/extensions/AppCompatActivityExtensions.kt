package ru.sug4chy.simplecalculator.extensions

import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.findTextViewById(@IdRes id: Int): TextView =
    this.findViewById(id)

fun AppCompatActivity.findButtonById(@IdRes id: Int): Button =
    this.findViewById(id)