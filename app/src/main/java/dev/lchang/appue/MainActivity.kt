package dev.lchang.appue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.lchang.appue.data.model.SongModel // Para el estado del MiniPlayer
import dev.lchang.appue.presentation.navigation.AppNavGraph
import dev.lchang.appue.presentation.navigation.BottomNavigationBar
import dev.lchang.appue.presentation.player.MiniPlayer
import dev.lchang.appue.ui.theme.AppUETheme
import dev.lchang.appue.presentation.home.izonePlaylist // Para obtener una canción de ejemplo

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppUETheme {
                val navController = rememberNavController()

                // Estado de ejemplo para el MiniPlayer
                // En una app real, esto vendría de un ViewModel o Service
                var currentSongForPlayer by remember { mutableStateOf<SongModel?>(null) }
                var isPlayingState by remember { mutableStateOf(false) }

                // Simulación: establece una canción al inicio para ver el MiniPlayer
                LaunchedEffect(Unit) {
                    currentSongForPlayer = izonePlaylist.songs.firstOrNull()
                    // isPlayingState = true // Descomenta si quieres que empiece reproduciendo
                }


                Scaffold(
                    bottomBar = {
                        Column { // Para apilar el MiniPlayer encima de la NavigationBar
                            MiniPlayer(
                                currentSong = currentSongForPlayer,
                                isPlaying = isPlayingState,
                                onPlayPauseClick = { isPlayingState = !isPlayingState }
                            )
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
