package tpriv.projects.imagegalleryapp.data.remote

import tpriv.projects.imagegalleryapp.BuildConfig
import tpriv.projects.imagegalleryapp.domain.model.MetadataResponse
import tpriv.projects.imagegalleryapp.domain.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesAPI {

    companion object {
        const val API_KEY = BuildConfig.API_KEY

    }

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&sort=interestingness-desc&api_key=$API_KEY")
    suspend fun searchImages(
        @Query("text") query: String? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = 100,
    ): SearchResponse

    @GET("?method=flickr.photos.getExif&format=json&nojsoncallback=1&api_key=$API_KEY")
    suspend fun getPhotoMetadata(
        @Query("photo_id") photoId:String
    ):MetadataResponse

}