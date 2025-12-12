package com.example.tp1

data class TrendingMovies(
    val page: Int = 0,
    val results: List<Movie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val first_air_date: String = "",
    val genre_ids: List<Int> = listOf(),
    val id: Int = 0,
    val media_type: String = "",
    val name: String = "",
    val origin_country: List<String> = listOf(),
    val original_language: String = "",
    val original_name: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class TrendingSeries(
    val page: Int = 0,
    val results: List<Serie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

data class Serie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val first_air_date: String = "",
    val genre_ids: List<Int> = listOf(),
    val id: Int = 0,
    val media_type: String = "",
    val name: String = "",
    val origin_country: List<String> = listOf(),
    val original_language: String = "",
    val original_name: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class TrendingPersons(
    val page: Int = 0,
    val results: List<Person> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

data class Person(
    val adult: Boolean = false,
    val gender: Int = 0,
    val id: Int = 0,
    val known_for_department: String = "",
    val media_type: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = ""
)











data class PlayList(
    val checksum: String = "",
    val collaborative: Boolean = false,
    val cover: String = "",
    val creation_date: String = "",
    val creator: Creator = Creator(),
    val description: String = "",
    val duration: Int = 0,
    val fans: Int = 0,
    val id: Int = 0,
    val is_loved_track: Boolean = false,
    val link: String = "",
    val md5_image: String = "",
    val nb_tracks: Int = 0,
    val picture_type: String = "",
    val `public`: Boolean = false,
    val share: String = "",
    val title: String = "",
    val tracklist: String = "",
    val tracks: Tracks = Tracks(),
    val type: String = ""
)

data class Creator(
    val id: Int = 0,
    val name: String = "",
    val tracklist: String = "",
    val type: String = ""
)

data class Tracks(
    val checksum: String = "",
    val `data`: List<Data> = listOf()
)

data class Data(
    val album: Album = Album(),
    val artist: Artist = Artist(),
    val duration: Int = 0,
    val explicit_content_cover: Int = 0,
    val explicit_content_lyrics: Int = 0,
    val explicit_lyrics: Boolean = false,
    val id: Long = 0,
    val isrc: String = "",
    val link: String = "",
    val md5_image: String = "",
    val preview: String = "",
    val rank: Int = 0,
    val readable: Boolean = false,
    val time_add: Int = 0,
    val title: String = "",
    val title_short: String = "",
    val title_version: String = "",
    val type: String = ""
)

data class Album(
    val cover: String = "",
    val id: Int = 0,
    val md5_image: String = "",
    val title: String = "",
    val tracklist: String = "",
    val type: String = "",
    val upc: String = ""
)

data class Artist(
    val id: Int = 0,
    val link: String = "",
    val name: String = "",
    val tracklist: String = "",
    val type: String = ""
)