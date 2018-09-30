package com.sakura.footballscore.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventResponse(
	val events: List<EventsItem>
) : Parcelable
