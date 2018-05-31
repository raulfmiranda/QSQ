package br.edu.faculdadepaulopicanco.traumadeface.model

import java.io.Serializable


data class Pesquisa(var questionarios: MutableList<Questionario>, var questionarioContador: QuestionarioContador) : Serializable