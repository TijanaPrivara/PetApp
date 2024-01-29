package tpriv.projects.imagegalleryapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import tpriv.projects.imagegalleryapp.data.paging.SearchImageDataSource
import tpriv.projects.imagegalleryapp.domain.model.ImageState
import tpriv.projects.imagegalleryapp.domain.model.PhotoItem
import tpriv.projects.imagegalleryapp.domain.repository.Repository
import tpriv.projects.imagegalleryapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    var queryImageState by mutableStateOf(ImageState())
        private set

    var favoriteImagesState by mutableStateOf(ImageState())

    var imageDetail by mutableStateOf(ImageState())
        private set

    var metadataState by mutableStateOf(ImageState())
        private set

    val searchImagePager1 = Pager(
        PagingConfig(pageSize = 100)
    ) {
        SearchImageDataSource(repo, "dogs")
    }.flow.cachedIn(viewModelScope)

    val searchImagePager2 = Pager(
        PagingConfig(pageSize = 100)
    ) {
        SearchImageDataSource(repo, "cats")
    }.flow.cachedIn(viewModelScope)

    fun setImageDetail(image: PhotoItem) {

        imageDetail = imageDetail.copy(image = image)
        getPhotoMetadata(image.id)
    }

    private fun getPhotoMetadata(photoId: String) = viewModelScope.launch {

        metadataState = metadataState.copy(
            loading = true,
        )

        when (val result = repo.getPhotoMetaData(photoId)) {

            is Resource.Error -> {

                metadataState = metadataState.copy(
                    error = result.message.toString(),
                )
            }
            is Resource.Success -> {

                metadataState = metadataState.copy(
                    metadata = result.data?.photo,
                    loading = false
                )
            }

        }

    }
}