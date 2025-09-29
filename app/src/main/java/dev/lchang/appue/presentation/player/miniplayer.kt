package dev.lchang.appue.presentation.player

import androidx.compose.foundation.clickable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.lchang.appue.data.model.SongModel // Asumiendo que SongModel tiene imageUrl, title, views (artista)

@Composable
fun MiniPlayer(
    currentSong: SongModel?, // La canción actual, null si no hay ninguna
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (currentSong == null) {
        // No mostrar nada si no hay canción o si está vacío después de cerrar
        Spacer(modifier = modifier.height(0.dp))
        return
    }

    // El color de fondo del miniplayer en Spotify es ligeramente diferente al fondo principal
    val miniPlayerBackgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f) // O un color específico

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp) // Altura típica del mini player
            .clickable { /* Abrir pantalla completa del reproductor */ },
        color = miniPlayerBackgroundColor,
        tonalElevation = 4.dp // Pequeña sombra para destacar
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(currentSong.imageUrl),
                contentDescription = currentSong.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentSong.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = currentSong.views, // Asumiendo que 'views' es el artista aquí
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Icono de "Me gusta" (opcional)
            IconButton(onClick = { /* Acción de Me Gusta */ }) {
                Icon(
                    Icons.Filled.FavoriteBorder, // Cambiar a Favorite si le dio like
                    contentDescription = "Me gusta",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(onClick = onPlayPauseClick) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.PauseCircle else Icons.Filled.PlayCircleFilled,
                    contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                    tint = MaterialTheme.colorScheme.onSurface, // O SpotifyGreen si quieres el botón de play siempre verde
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
    // Puedes añadir una barra de progreso lineal aquí si quieres
    // LinearProgressIndicator(progress = 0.5f, modifier = Modifier.fillMaxWidth().height(2.dp), color = SpotifyGreen, trackColor = SpotifyLightGray)
}
