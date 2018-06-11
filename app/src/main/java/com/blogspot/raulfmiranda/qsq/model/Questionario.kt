package com.blogspot.raulfmiranda.qsq.model

import java.io.Serializable

data class Questionario(var status: Int, var questoes: List<Questao>) : Serializable