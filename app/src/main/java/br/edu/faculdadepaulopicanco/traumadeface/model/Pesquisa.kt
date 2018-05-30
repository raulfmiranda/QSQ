package br.edu.faculdadepaulopicanco.traumadeface.model

import android.os.Parcel
import android.os.Parcelable


data class Pesquisa(var questionarios: MutableList<Questionario>) : Parcelable {
    constructor(parcel: Parcel) : this(
            arrayListOf<Questionario>().apply {
                parcel.readArrayList(Questionario::class.java.classLoader)
            })
    { }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pesquisa> {
        override fun createFromParcel(parcel: Parcel): Pesquisa {
            return Pesquisa(parcel)
        }

        override fun newArray(size: Int): Array<Pesquisa?> {
            return arrayOfNulls(size)
        }
    }
}