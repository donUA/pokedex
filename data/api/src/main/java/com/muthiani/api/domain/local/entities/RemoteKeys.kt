
package com.muthiani.api.domain.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val remoteKeyId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
