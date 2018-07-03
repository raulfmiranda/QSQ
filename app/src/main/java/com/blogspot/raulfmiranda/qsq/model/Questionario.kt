package com.blogspot.raulfmiranda.qsq.model

import java.io.Serializable

data class Questionario(var status: Int, var questoes: List<Questao>, var nomePaciente: String, var localPesquisa: String, var idadePaciente: String) : Serializable