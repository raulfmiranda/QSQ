package br.edu.faculdadepaulopicanco.traumadeface.model

import android.os.Parcel
import android.os.Parcelable

data class Questao(val pergunta: String, val respostas: List<String>, var respostaEscolhida: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.createStringArrayList(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pergunta)
        parcel.writeStringList(respostas)
        parcel.writeInt(respostaEscolhida)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Questao> {
        override fun createFromParcel(parcel: Parcel): Questao {
            return Questao(parcel)
        }

        override fun newArray(size: Int): Array<Questao?> {
            return arrayOfNulls(size)
        }
    }
}