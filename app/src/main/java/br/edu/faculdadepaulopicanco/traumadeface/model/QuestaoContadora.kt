package br.edu.faculdadepaulopicanco.traumadeface.model

import java.io.Serializable

class QuestaoContadora(val pergunta: String, val respostasContadoras: LinkedHashMap<String, Int>) : Serializable