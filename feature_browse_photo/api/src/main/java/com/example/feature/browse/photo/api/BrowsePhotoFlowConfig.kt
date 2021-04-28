package com.example.feature.browse.photo.api

import com.example.core.flow.FlowConfig
import kotlinx.parcelize.Parcelize

@Parcelize
data class BrowsePhotoFlowConfig(val photoId: Long): FlowConfig