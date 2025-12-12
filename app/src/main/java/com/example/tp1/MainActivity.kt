package com.example.tp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.HideImage
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage
import com.example.tp1.ui.theme.TP1Theme
import kotlinx.serialization.Serializable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Serializable object ScreenDest
@Serializable object GlobalAppliDest

@Serializable object FilmsTab
@Serializable object SeriesTab
@Serializable object ActeursTab
@Serializable object MusicTab

@Serializable
data class MovieDetailsDest(
    val title: String,
    val poster_path: String? = null,
    val release_date: String = "",
    val overview: String = ""
)

data class SerieDetailsDest(
    val name: String,
    val poster_path: String? = null,
    val release_date: String = "",
    val overview: String = ""
)

data class PersonDetailsDest(
    val name: String,
    val profile_path: String? = null,
    val popularity: Double = 0.0,
    val known_for_department: String = ""
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val backStack = remember { mutableStateListOf<Any>(ScreenDest) }
                    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

                    Column(modifier = Modifier.padding(innerPadding)) {
                        NavDisplay(
                            backStack = backStack,
                            entryProvider = entryProvider {
                                entry<ScreenDest> {
                                    Screen(
                                        classes = windowSizeClass,
                                        onNavigate = { backStack.add(GlobalAppliDest) }
                                    )
                                }
                                entry<GlobalAppliDest> {
                                    MovieMainPage()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieMainPage() {
    val viewModel: MainViewModel = viewModel()
    Column {
        Text(
            text = "Application Android",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
        )
        MovieContent(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieContent(viewModel: MainViewModel) {
    val movies by viewModel.movies.collectAsStateWithLifecycle()
    val tabBackStack = remember { mutableStateListOf<Any>(MusicTab) }
    val series by viewModel.series.collectAsStateWithLifecycle()
    val persons by viewModel.persons.collectAsStateWithLifecycle()

    var searchText by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.getMovies()
        viewModel.getSeries()
        viewModel.getPersons()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    query = searchText,
                    onQueryChange = { searchText = it },
                    onSearch = {  },

                    active = false,
                    onActiveChange = { },

                    placeholder = {
                        Text(
                            "Rechercher...",
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Recherche",
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = { searchText = "" }) {
                                Icon(
                                    Icons.Rounded.HideImage,
                                    contentDescription = "Effacer",
                                    tint = Color.White
                                )
                            }
                        }
                    },

                    colors = SearchBarDefaults.colors(
                        containerColor = Color(0xFF6200EE),
                        inputFieldColors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White
                        )
                    ),

                    modifier = Modifier.fillMaxWidth()
                ) {
                }
            }
        },

        bottomBar = {
            NavigationBar (
                containerColor = Color(0xFF6200EE),
                contentColor = Color.White
            ){
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Movie, contentDescription = "Films") },
                    label = { Text("Films") },
                    selected = tabBackStack.last() is FilmsTab,
                    onClick = {
                        if (tabBackStack.last() !is FilmsTab) {
                            tabBackStack.add(FilmsTab)
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Tv, contentDescription = "Séries") },
                    label = { Text("Séries") },
                    selected = tabBackStack.last() is SeriesTab,
                    onClick = {
                        if (tabBackStack.last() !is SeriesTab) {
                            tabBackStack.add(SeriesTab)
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Face, contentDescription = "Acteurs") },
                    label = { Text("Acteurs") },
                    selected = tabBackStack.last() is ActeursTab,
                    onClick = {
                        if (tabBackStack.last() !is ActeursTab) {
                            tabBackStack.add(ActeursTab)
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.MusicNote, contentDescription = "Musiques") },
                    label = { Text("Acteurs") },
                    selected = tabBackStack.last() is MusicTab,
                    onClick = {
                        if (tabBackStack.last() !is MusicTab) {
                            tabBackStack.add(MusicTab)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavDisplay(
                backStack = tabBackStack,
                entryProvider = entryProvider {
                    entry<FilmsTab> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(movies) { movie ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            tabBackStack.add(
                                                MovieDetailsDest(
                                                    title = movie.title,
                                                    poster_path = movie.poster_path,
                                                    release_date = movie.release_date,
                                                    overview = movie.overview
                                                )
                                            )
                                        },
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        AsyncImage(
                                            model = "https://image.tmdb.org/t/p/w780${movie.poster_path}",
                                            contentDescription = movie.title,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = movie.title,
                                            modifier = Modifier.padding(8.dp),
                                            style = MaterialTheme.typography.bodyMedium,
                                            textAlign = TextAlign.Center,
                                            minLines = 2,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = movie.release_date,
                                            modifier = Modifier.padding(bottom = 8.dp),
                                            style = MaterialTheme.typography.bodySmall,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                    entry<MovieDetailsDest> { dest ->
                        MovieDetailsScreen(
                            title = dest.title,
                            posterPath = dest.poster_path,
                            releaseDate = dest.release_date,
                            overview = dest.overview,
                            onBack = {
                                if (tabBackStack.size > 1) tabBackStack.removeAt(tabBackStack.lastIndex)
                            }
                        )
                    }
                    entry<SeriesTab> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(series) { serie ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            tabBackStack.add(
                                                SerieDetailsDest(
                                                    name = serie.name,
                                                    poster_path = serie.poster_path,
                                                    release_date = serie.first_air_date,
                                                    overview = serie.overview
                                                )
                                            )
                                        },
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        AsyncImage(
                                            model = "https://image.tmdb.org/t/p/w780${serie.poster_path}",
                                            contentDescription = serie.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = serie.name,
                                            modifier = Modifier.padding(8.dp),
                                            style = MaterialTheme.typography.bodyMedium,
                                            textAlign = TextAlign.Center,
                                            minLines = 2,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = serie.first_air_date,
                                            modifier = Modifier.padding(bottom = 8.dp),
                                            style = MaterialTheme.typography.bodySmall,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                    entry<ActeursTab> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(persons) { person ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            tabBackStack.add(
                                                PersonDetailsDest(
                                                    name = person.name,
                                                    profile_path = person.profile_path,
                                                    known_for_department = person.known_for_department
                                                )
                                            )
                                        },
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        AsyncImage(
                                            model = "https://image.tmdb.org/t/p/w780${person.profile_path}",
                                            contentDescription = person.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = person.name,
                                            modifier = Modifier.padding(8.dp),
                                            style = MaterialTheme.typography.bodyMedium,
                                            textAlign = TextAlign.Center,
                                            minLines = 2,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = person.known_for_department,
                                            modifier = Modifier.padding(bottom = 8.dp),
                                            style = MaterialTheme.typography.bodySmall,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                    entry<MusicTab> {
                        Text(
                            text = "toutes vos musiques",
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    title: String,
    posterPath: String?,
    releaseDate: String,
    overview: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (!posterPath.isNullOrBlank()) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w780$posterPath",
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            if (releaseDate.isNotBlank()) {
                Text(
                    text = "Date de sortie : $releaseDate",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            if (overview.isNotBlank()) {
                Text(
                    text = "Résumé",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = overview,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 6.dp)
                )
            } else {
                Text(
                    text = "Aucune description disponible.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun Screen(classes: WindowSizeClass, onNavigate: () -> Unit) {
    val classeLargeur = classes.windowWidthSizeClass

    when (classeLargeur) {
        WindowWidthSizeClass.COMPACT -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(20.dp)
                        .padding(start = 50.dp, end = 50.dp)
                ) {
                    Image(
                        painterResource(R.drawable.profil),
                        contentDescription = "Photo de profil",
                        modifier = Modifier
                            .size(200.dp)
                            .border(BorderStroke(1.dp, Color.DarkGray), CircleShape)
                            .clip(CircleShape)
                    )
                    Text(
                        text = "Matéo Bonneton",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Text(
                        text = "Jeune étudiant en BUT MMI désespéré car l'avenir dans le web est menacé",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 15.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 50.dp)
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Rounded.Email,
                                contentDescription = "Email",
                                tint = Color.Gray,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                            Text(text = "mateobonneton@gmail.com")
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = "Youtube",
                                tint = Color.Red,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                            Text(text = "https://www.youtube.com/@mateo7vie")
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 100.dp)
                    ) {
                        Button(onClick = { onNavigate() }) {
                            Text("Démarrer")
                        }
                    }
                }
            }
        }
        else -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(20.dp)
                    ) {
                        Image(
                            painterResource(R.drawable.profil),
                            contentDescription = "Photo de profil",
                            modifier = Modifier
                                .size(200.dp)
                                .border(BorderStroke(1.dp, Color.DarkGray), CircleShape)
                                .clip(CircleShape)
                        )
                        Text(
                            text = "Matéo Bonneton",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(top = 15.dp)
                        )
                        Text(
                            text = "Jeune étudiant en BUT MMI désespéré car l'avenir dans le web est menacé",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 15.dp)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 50.dp)
                    ) {
                        Row(modifier = Modifier.padding(bottom = 8.dp)) {
                            Icon(
                                imageVector = Icons.Rounded.Email,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                            Text(text = "mateobonneton@gmail.com")
                        }
                        Row(modifier = Modifier.padding(bottom = 20.dp)) {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                            Text(text = "https://www.youtube.com/@mateo7vie")
                        }
                        Button(onClick = { onNavigate() }) {
                            Text("Démarrer")
                        }
                    }
                }
            }
        }
    }
}