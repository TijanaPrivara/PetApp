package tpriv.projects.imagegalleryapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import tpriv.projects.imagegalleryapp.R
import tpriv.projects.imagegalleryapp.domain.model.Exif
import tpriv.projects.imagegalleryapp.ui.navigation.TopBar
import tpriv.projects.imagegalleryapp.ui.theme.darkGreen
import tpriv.projects.imagegalleryapp.ui.theme.lightGreen
import tpriv.projects.imagegalleryapp.ui.viewmodel.ImageViewModel

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    viewModel: ImageViewModel,
) = with(viewModel) {

    val uriHandler = LocalUriHandler.current

    val context = LocalContext.current

    imageDetail.image?.let { photoItem ->

        val shareIntent = Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_TEXT, photoItem.url)
            .putExtra(Intent.EXTRA_SUBJECT, photoItem.title)
            .setType("text/plain")

        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.detail_screen_title),
                    onBackPressed = { navHostController.navigateUp() }
                )
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround,
            ) {

                Text(
                    text = photoItem.title,
                    style = TextStyle(
                        fontSize = 25.sp,
                    ),
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )

                GlideImage(
                    imageModel = photoItem.url,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .clickable {
                            uriHandler.openUri(photoItem.url)
                        }
                )

                Text(
                    text = stringResource(R.string.data),
                    style = TextStyle(
                        fontSize = 25.sp,
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)
                )

                if (metadataState.loading) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = lightGreen,
                            strokeWidth = 2.dp,

                            )
                    }
                } else {
                    metadataState.metadata?.let { data ->

                        if (data.exif.isNotEmpty()) {
                            LazyColumn(
                                contentPadding = PaddingValues(bottom = 70.dp),
                                modifier = Modifier.height(350.dp)
                            ) {

                                items(data.exif) { exif ->

                                    MetaDataItem(data = exif)
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp),
                                        color = darkGreen.copy(0.4f)
                                    )

                                }

                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = stringResource(R.string.no_metadata))
                            }
                        }
                    } ?: Column(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(R.string.no_metadata))
                    }


                }

                Spacer(modifier = Modifier.height(65.dp))

            }

        }

    }
}

@Composable
fun MetaDataItem(data: Exif, modifier: Modifier = Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {

        Text(text = data.label + ":", maxLines = 1, modifier = Modifier.padding(end = 5.dp))

        Text(text = data.raw._content)
    }

}