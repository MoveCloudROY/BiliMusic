package top.roy1994.bilimusic.data.objects.biliapi

data class PlayUrlRoot (
    val code: Int,
    val message: String,
    val ttl: Int,
    val data: PlayUrlData? = null,
)

data class PlayUrlData (
    val from: String,
    val result: String,
    val message: String,
    val quality: Int,
    val format: String,
    val timelength: Int,
    val accept_format: String,
    val accept_description: List<String>,
    val accept_quality: List<String>,
    val video_codecid: Int,
    val seek_param: String,
    val seek_type: String,
    val dash: PlayUrlDash,
    val support_formats: List<PlayUrlSupportFormatObject>,
    val high_format: Int? = null,
    val last_play_time: Int,
    val last_play_cid: Int,
)

data class PlayUrlDash (
    val duration: Int,
    val minBufferTime: Float,
    val min_buffer_time: Float,
    val video: List<PlayUrlVAObject>,
    val audio: List<PlayUrlVAObject>,
    val dolby: PlayUrlDashDolby,
    val flac: PlayUrlDashFlac,
)

data class PlayUrlVAObject (
    val id: Int,
    val baseUrl: String,
    val base_url: String,
    val backupUrl: List<String>,
    val backup_url: List<String>,
    val bandwidth: Int,
    val mimeType: String,
    val mime_type: String,
    val codecs: String,
    val width: Int,
    val height: Int,
    val frameRate: String,
    val frame_rate: String,
    val sar: String,
    val startWithSap: Int,
    val start_with_sap: Int,
    val SegmentBase: PlayUrlDashObjectSegmentBase,
    val segment_base: PlayUrlDashObjectSegmentBase,
    val codecid: Int,
)

data class PlayUrlDashObjectSegmentBase (
    val initialization: String,
    val index_range: String,
)

data class PlayUrlDashDolby (
    val type: Int,
    val audio: List<PlayUrlDashDolbyAudioObject>,
)

data class PlayUrlDashDolbyAudioObject (
    val id: Int,
    val base_url: String,
    val backup_url: List<String>,
    val bandwidth: Int,
    val mime_type: Int,
    val codecs: Int,
    val segment_base: List<PlayUrlDashObjectSegmentBase>,
    val size: Int,
)

data class PlayUrlSupportFormatObject (
    val quality: Int,
    val format: String,
    val new_description: String,
    val display_desc: String,
    val superscript: String,
    val codecs: List<String>,
)

data class PlayUrlDashFlac (
    val display: Boolean,
    val audio: PlayUrlVAObject,
)


data class PageListRoot (
    val code: Int,
    val message: String,
    val ttl: Int,
    val data: List<PageListObject>,
)

data class PageListObject (
    val cid: Int,
    val page: Int,
    val from: String,
    val part: String,
    val duration: Int,
    val vid: String,
    val weblink: String,
    val dimension: PageListDimension,
)

data class PageListDimension (
    val width: Int,
    val height: Int,
    val rotate: Int,
)

data class VideoInfo(
    val code: Int,
    val message: String,
    val ttl: Int,
    val data: Data
) {
    data class Data(
        val bvid: String,
        val aid: Int,
        val videos: Int,
        val tid: Int,
        val tname: String,
        val copyright: Int,
        val pic: String,
        val title: String,
        val pubdate: Int,
        val ctime: Int,
        val desc: String,
        val desc_v2: List<DescV2>,
        val state: Int,
        val duration: Int,
        val mission_id: Int,
        val rights: Rights,
        val owner: Owner,
        val stat: Stat,
        val dynamic: String,
        val cid: Int,
        val dimension: Dimension,
        val premiere: Any? = null,
        val teenage_mode: Int,
        val is_chargeable_season: Boolean,
        val is_story: Boolean,
        val no_cache: Boolean,
        val pages: List<Page>,
        val subtitle: Subtitle,
        val staff: List<Staff>,
        val is_season_display: Boolean,
        val user_garb: UserGarb,
        val honor_reply: HonorReply,
        val like_icon: String
    ) {
        data class DescV2(
            val raw_text: String,
            val type: Int,
            val biz_id: Int
        )

        data class Rights(
            val bp: Int,
            val elec: Int,
            val download: Int,
            val movie: Int,
            val pay: Int,
            val hd5: Int,
            val no_reprint: Int,
            val autoplay: Int,
            val ugc_pay: Int,
            val is_cooperation: Int,
            val ugc_pay_preview: Int,
            val no_background: Int,
            val clean_mode: Int,
            val is_stein_gate: Int,
            val is_360: Int,
            val no_share: Int,
            val arc_pay: Int,
            val free_watch: Int
        )

        data class Owner(
            val mid: Int,
            val name: String,
            val face: String
        )

        data class Stat(
            val aid: Int,
            val view: Int,
            val danmaku: Int,
            val reply: Int,
            val favorite: Int,
            val coin: Int,
            val share: Int,
            val now_rank: Int,
            val his_rank: Int,
            val like: Int,
            val dislike: Int,
            val evaluation: String,
            val argue_msg: String
        )

        data class Dimension(
            val width: Int,
            val height: Int,
            val rotate: Int
        )

        data class Page(
            val cid: Int,
            val page: Int,
            val from: String,
            val part: String,
            val duration: Int,
            val vid: String,
            val weblink: String,
            val dimension: Dimension
        ) {
            data class Dimension(
                val width: Int,
                val height: Int,
                val rotate: Int
            )
        }

        data class Subtitle(
            val allow_submit: Boolean,
            val list: List<SubObject>
        ) {
            data class SubObject(
                val id: Long,
                val lan: String,
                val lan_doc: String,
                val is_lock: Boolean,
                val author_mid: Int,
                val subtitle_url: String,
                val author: Author,
            ) {
                data class Author(
                    val mid: Int,
                    val name: String,
                    val sex: String,
                    val face: String,
                    val sign: String,
                    val rank: Int,
                    val birthday: Int,
                    val is_fake_account: Int,
                    val is_deleted: Int,
                )
            }
        }

        data class Staff(
            val mid: Int,
            val title: String,
            val name: String,
            val face: String,
            val vip: Vip,
            val official: Official,
            val follower: Int,
            val label_style: Int
        ) {
            data class Vip(
                val type: Int,
                val status: Int,
                val due_date: Long,
                val vip_pay_type: Int,
                val theme_type: Int,
                val label: Label,
                val avatar_subscript: Int,
                val nickname_color: String,
                val role: Int,
                val avatar_subscript_url: String,
                val tv_vip_status: Int,
                val tv_vip_pay_type: Int
            ) {
                data class Label(
                    val path: String,
                    val text: String,
                    val label_theme: String,
                    val text_color: String,
                    val bg_style: Int,
                    val bg_color: String,
                    val border_color: String,
                    val use_img_label: Boolean,
                    val img_label_uri_hans: String,
                    val img_label_uri_hant: String,
                    val img_label_uri_hans_static: String,
                    val img_label_uri_hant_static: String
                )
            }

            data class Official(
                val role: Int,
                val title: String,
                val desc: String,
                val type: Int
            )
        }

        data class UserGarb(
            val url_image_ani_cut: String
        )

        data class HonorReply(
            val honor: List<Honor>
        ) {
            data class Honor(
                val aid: Int,
                val type: Int,
                val desc: String,
                val weekly_recommend_num: Int
            )
        }
    }
}