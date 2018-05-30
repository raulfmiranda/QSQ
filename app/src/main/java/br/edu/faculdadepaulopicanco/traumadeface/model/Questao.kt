package br.edu.faculdadepaulopicanco.traumadeface.model

import java.io.Serializable

data class Questao(val pergunta: String, val respostas: List<String>, var respostaEscolhida: Int) : Serializable