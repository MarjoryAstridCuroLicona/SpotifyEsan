package dev.lchang.appue.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayCircle // Icono de Play grande y verde
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import dev.lchang.appue.data.model.SongModel
import dev.lchang.appue.ui.theme.SpotifyGreen // Asegúrate de importar tu color verde

// Vamos a simular datos para una "Playlist" en lugar de un "ArtistProfile"
data class PlaylistDetails(
    val title: String,
    val description: String, // Ej: "Los grandes éxitos de IZ*ONE"
    val coverImageUrl: String,
    val creator: String, // Ej: "Creado por AppUE" o "IZ*ONE Oficial"
    val likes: String,
    val totalDuration: String,
    val songs: List<SongModel>
)

// Usaremos el mock de IZ*ONE adaptado a esta estructura
val izonePlaylist = PlaylistDetails(
    title = "IZ*ONE", // Título de la playlist
    description = "1,359,114 oyentes mensuales",
    // Necesitarás una imagen de portada para la playlist.
    // Usaré la imagen de grupo por ahora, pero idealmente sería una portada de playlist.
    coverImageUrl = "https://e1.pxfuel.com/desktop-wallpaper/559/189/desktop-wallpaper-iz-one-on-twitter-in-2021-izone-2021-thumbnail.jpg",
    creator = "Spotify",
    likes = "1.2M likes",
    totalDuration = "2h 35min", // Calcula la duración total de las canciones
    songs = listOf(
        SongModel("Panorama", "IZ*ONE", "3:42", "https://i.ytimg.com/vi/G8GaQdW2wHc/maxresdefault.jpg"),
        SongModel("La Vie en Rose", "IZ*ONE", "3:39", "https://i.pinimg.com/1200x/f8/c3/fb/f8c3fb20ba40b876d904cc1a49518a7f.jpg"),
        SongModel("Secret Story of the Swan", "IZ*ONE", "3:12", "https://i.ytimg.com/vi/nnVjsos40qk/maxresdefault.jpg"),
        SongModel("FIESTA", "IZ*ONE", "3:37", "https://i.ytimg.com/vi/eDEFolvLn0A/maxresdefault.jpg"),
        SongModel("Violeta", "IZ*ONE", "3:20", "https://i.ytimg.com/vi/6eEZ7DJMzuk/maxresdefault.jpg")
    )
)
// En SongModel, el segundo parámetro ahora lo interpreto como "artista" para la lista de canciones.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(playlist: PlaylistDetails = izonePlaylist) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. Cabecera de la Playlist
        item {
            PlaylistHeader(
                title = playlist.title,
                coverImageUrl = playlist.coverImageUrl,
                creator = playlist.creator,
                likes = playlist.likes,
                totalDuration = playlist.totalDuration,
                description = playlist.description
            )
        }

        // 2. Botones de Acción
        item {
            PlaylistActionButtons()
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 3. Lista de Canciones
        itemsIndexed(playlist.songs) { index, song ->
            PlaylistSongItem(
                song = song,
                trackNumber = index + 1, // Opcional, las playlists no siempre numeran
                onSongClick = { /* Acción al hacer clic */ },
                onMoreOptionsClick = { /* Más opciones */ }
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp)) // Espacio para la barra de navegación inferior (si la tuvieras)
        }
    }
}

@Composable
fun PlaylistHeader(
    title: String,
    coverImageUrl: String,
    creator: String,
    likes: String,
    totalDuration: String,
    description: String
) {
    val headerHeight = 300.dp // Altura de la imagen de cabecera
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background.copy(alpha = 0.5f), MaterialTheme.colorScheme.background),
        startY = headerHeight.value * 0.4f, // Inicia el degradado más abajo
        endY = headerHeight.value
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
    ) {
        Image(
            painter = rememberAsyncImagePainter(coverImageUrl),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.85f } // Un poco de transparencia a la imagen
                .drawWithCache {
                    onDrawWithContent {
                        drawContent() // Dibuja la imagen
                        drawRect(gradientBrush, blendMode = BlendMode.Multiply) // Dibuja el degradado encima
                    }
                }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom // Alinea el texto abajo
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Playlist de $creator", // "Spotify" en el ejemplo de la imagen
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$likes \u2022 $totalDuration", // \u2022 es el punto medio
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PlaylistActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Iconos de acción a la izquierda
        IconButton(onClick = { /* Añadir a biblioteca/likes */ }) {
            Icon(
                Icons.Filled.FavoriteBorder,
                contentDescription = "Guardar en biblioteca",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
        }
        IconButton(onClick = { /* Descargar */ }) {
            Icon(
                Icons.Filled.ArrowDownward,
                contentDescription = "Descargar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
        }
        IconButton(onClick = { /* Más opciones */ }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "Más opciones",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón grande de Play
        IconButton(
            onClick = { /* Acción de reproducir playlist */ },
            modifier = Modifier.size(60.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayCircle,
                contentDescription = "Reproducir Playlist",
                tint = SpotifyGreen, // Color verde Spotify
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun PlaylistSongItem(
    song: SongModel,
    trackNumber: Int? = null,
    onSongClick: () -> Unit,
    onMoreOptionsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSongClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberAsyncImagePainter(song.imageUrl),
            contentDescription = song.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = song.views,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onMoreOptionsClick) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "Más opciones",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
