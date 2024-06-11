package dam.adri.domain.modelo.entities

import android.os.Parcel
import android.os.Parcelable
import dam.adri.domain.modelo.dto.UserLeagueDto

data class UserLeague(
    val league: Int?,
    val user: Int?,
    val userName: String?,
    val driver4Number: Int?,
    val driver2Number: Int?,
    val driver3Number: Int?,
    val driver1Number: Int?,
    val puntuation: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(league)
        parcel.writeValue(user)
        parcel.writeString(userName)
        parcel.writeValue(driver4Number)
        parcel.writeValue(driver2Number)
        parcel.writeValue(driver3Number)
        parcel.writeValue(driver1Number)
        parcel.writeValue(puntuation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserLeague> {
        override fun createFromParcel(parcel: Parcel): UserLeague {
            return UserLeague(parcel)
        }

        override fun newArray(size: Int): Array<UserLeague?> {
            return arrayOfNulls(size)
        }
    }
}

fun UserLeagueDto.toUserLeague(): UserLeague {
    return UserLeague(
        league = this.league,
        user = this.user,
        userName = this.userName,
        driver4Number = this.driver4Number,
        driver2Number = this.driver2Number,
        driver3Number = this.driver3Number,
        driver1Number = this.driver1Number,
        puntuation = this.puntuation
    )
}