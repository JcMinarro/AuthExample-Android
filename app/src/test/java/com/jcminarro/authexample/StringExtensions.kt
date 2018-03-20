package com.jcminarro.authexample

fun String.removeAllSpaces() =
        replace(" ", "")
                .replace("\n", "")