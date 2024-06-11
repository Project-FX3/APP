package dam.adri.domain.modelo.entities

import android.os.Parcel
import android.os.Parcelable
import dam.adri.domain.modelo.dto.LeagueDto

data class League(
    val id: Int?,
    val name: String?,
    val accesscode: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        if (id != null) {
            parcel.writeInt(id)
        }
        parcel.writeString(name)
        parcel.writeString(accesscode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<League> {
        override fun createFromParcel(parcel: Parcel): League {
            return League(parcel)
        }

        override fun newArray(size: Int): Array<League?> {
            return arrayOfNulls(size)
        }
    }
}

fun LeagueDto.toLeague(): League {
    return League(
        id = this.id,
        name = this.name,
        accesscode = this.accesscode
    )
}