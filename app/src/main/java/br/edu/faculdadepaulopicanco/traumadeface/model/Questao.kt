package br.edu.faculdadepaulopicanco.traumadeface.model

data class Questao(val pergunta: String, val respostas: List<String>, var respostaEscolhida: Int)