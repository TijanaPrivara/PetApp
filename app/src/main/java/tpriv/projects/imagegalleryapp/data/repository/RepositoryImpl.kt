package tpriv.projects.imagegalleryapp.data.repository

import tpriv.projects.imagegalleryapp.data.local.ImageDatabase
import tpriv.projects.imagegalleryapp.data.remote.ImagesAPI
import tpriv.projects.imagegalleryapp.domain.model.MetadataResponse
import tpriv.projects.imagegalleryapp.domain.repository.Repository
import tpriv.projects.imagegalleryapp.domain.model.SearchResponse
import tpriv.projects.imagegalleryapp.util.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ImagesAPI,
    private val db: ImageDatabase
) : Repository {

    override suspend fun searchPhotos(
        query: String,
        page: Int?,
        perPage: Int?
    ): Resource<SearchResponse> {

        return try {

            val data = api.searchImages(query, page = page, perPage = perPage)
            Resource.Success(data)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error has occurred")
        }
    }

    override suspend fun getPhotoMetaData(photoId: String): Resource<MetadataResponse> {
        return try {

            val data = api.getPhotoMetadata(photoId)
            Resource.Success(data)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

}