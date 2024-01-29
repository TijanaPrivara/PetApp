package tpriv.projects.imagegalleryapp.domain.model

import tpriv.projects.imagegalleryapp.domain.model.PhotoMetadata

data class MetadataResponse(
    val photo: PhotoMetadata,
    val stat: String
)