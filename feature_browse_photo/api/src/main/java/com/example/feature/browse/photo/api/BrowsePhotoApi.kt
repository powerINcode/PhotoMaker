package com.example.feature.browse.photo.api

import com.example.core.flow.FlowEntity
import com.example.feature.browse.photo.api.domain.GetPhotoByIdUseCase

interface BrowsePhotoApi: FlowEntity {
    fun getGetPhotoByIdUseCase(): GetPhotoByIdUseCase
}