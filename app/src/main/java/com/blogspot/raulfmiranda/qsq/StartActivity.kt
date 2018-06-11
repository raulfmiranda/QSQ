package com.blogspot.raulfmiranda.qsq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blogspot.raulfmiranda.qsq.R
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.startActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btnIniciar.setOnClickListener {
            startActivity<FormActivity>()
        }

    }
}
