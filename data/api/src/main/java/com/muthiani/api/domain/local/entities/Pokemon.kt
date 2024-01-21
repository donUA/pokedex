package com.muthiani.api.domain.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "pokemon")
data class Pokemon (
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  var name : String? = null,
  var url  : String? = null
)