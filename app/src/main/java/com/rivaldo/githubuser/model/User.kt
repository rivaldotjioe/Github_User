package com.rivaldo.githubuser.model


import android.os.Parcel
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class User(
    val username: String,
    val avatar: String,
    var avatarInt: Int? = null,
    val company: String,
    val follower: Int,
    val following: Int,
    val location: String,
    val name: String,
    val repository: Int,
    ) : Parcelable
//{
//    constructor(parcel: Parcel) : this(
//        parcel.readString().toString(),
//        parcel.readString().toString(),
//        parcel.readValue(Int::class.java.classLoader) as? Int,
//        parcel.readString().toString(),
//        parcel.readInt(),
//        parcel.readInt(),
//        parcel.readString().toString(),
//        parcel.readString().toString(),
//        parcel.readInt(),
//    ) {
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<User> {
//        override fun createFromParcel(source: Parcel): User {
//            return User(source)
//        }
//
//        override fun newArray(size: Int): Array<User?> {
//            return arrayOfNulls(size)
//        }
//
//    }
//
//    override fun writeToParcel(dest: Parcel, flags: Int) {
//        dest.writeString(name)
//        dest.writeString(username)
//        dest.writeValue(avatarInt)
//        dest.writeString(avatar)
//        dest.writeString(company)
//        dest.writeInt(follower)
//        dest.writeInt(following)
//        dest.writeString(location)
//        dest.writeInt(repository)
//    }
//
//
//}

val USER_KEY = "user"

