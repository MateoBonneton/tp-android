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