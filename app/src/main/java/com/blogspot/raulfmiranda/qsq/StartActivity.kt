package com.blogspot.raulfmiranda.qsq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blogspot.raulfmiranda.qsq.R
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.startActivity

class StartActivity : AppCompatActivity() {

    var nomePaciente: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar?.title = "Questionário de Saúde e Quedas"

        btnIniciar.setOnClickListener {
            if(!edtNomePaciente.text.isNullOrBlank())
                nomePaciente = edtNomePaciente.text.toString()

            startActivity<FormActivity>(FormActivity.EXTRA_NOME_PACIENTE to nomePaciente)
//            startActivity<FormActivity>(FormActivity.EXTRA_NOME_PACIENTE to nomePaciente)
        }

    }
}
