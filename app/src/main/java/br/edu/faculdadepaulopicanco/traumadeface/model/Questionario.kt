package br.edu.faculdadepaulopicanco.traumadeface.model

import android.os.Parcel
import android.os.Parcelable

data class Questionario(var status: Int, var questoes: List<Questao>) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(Questao)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(status)
        parcel.writeTypedList(questoes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Questionario> {
        override fun createFromParcel(parcel: Parcel): Questionario {
            return Questionario(parcel)
        }

        override fun newArray(size: Int): Array<Questionario?> {
            return arrayOfNulls(size)
        }
    }
}