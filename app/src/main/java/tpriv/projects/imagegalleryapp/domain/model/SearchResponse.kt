package tpriv.projects.imagegalleryapp.domain.model

import tpriv.projects.imagegalleryapp.domain.model.Photos

data class SearchResponse(
    val photos: Photos,
    val stat: String
)