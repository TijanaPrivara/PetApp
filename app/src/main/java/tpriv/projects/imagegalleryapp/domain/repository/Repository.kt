package tpriv.projects.imagegalleryapp.domain.repository

import tpriv.projects.imagegalleryapp.domain.model.SearchResponse
import tpriv.projects.imagegalleryapp.util.Resource
import tpriv.projects.imagegalleryapp.domain.model.MetadataResponse

interface Repository {

    suspend fun searchPhotos(query:String,page:Int?=null,perPage:Int?=null): Resource<SearchResponse>
    suspend fun getPhotoMetaData(photoId:String):Resource<MetadataResponse>
}