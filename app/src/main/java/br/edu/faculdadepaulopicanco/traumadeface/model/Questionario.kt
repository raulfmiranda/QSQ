package br.edu.faculdadepaulopicanco.traumadeface.model

import java.io.Serializable

data class Questionario(var status: Int, var questoes: List<Questao>) : Serializable