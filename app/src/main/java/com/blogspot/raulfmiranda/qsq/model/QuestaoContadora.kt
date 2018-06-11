package com.blogspot.raulfmiranda.qsq.model

import java.io.Serializable

class QuestaoContadora(val pergunta: String, val respostasContadoras: LinkedHashMap<String, Int>) : Serializable