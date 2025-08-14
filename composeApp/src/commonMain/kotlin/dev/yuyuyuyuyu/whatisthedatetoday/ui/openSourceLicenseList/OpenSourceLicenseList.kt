package dev.yuyuyuyuyu.whatisthedatetoday.ui.openSourceLicenseList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.rememberLibraries
import kotlinx.collections.immutable.toImmutableList
import whatisthedatetoday.composeapp.generated.resources.Res

@Composable
fun OpenSourceLicenseList(modifier: Modifier = Modifier) {
    val libraries by rememberLibraries {
        Res.readBytes("files/aboutlibraries.json").decodeToString()
    }

    LibrariesContainer(
        libraries = libraries?.libraries?.distinctBy { it.name }?.let {
            libraries?.copy(libraries = it.toImmutableList())
        },
        modifier = modifier,
        showDescription = true,
    )
}
