package com.mustafa.movieapp.api.api

import com.mustafa.movieapp.api.ApiSuccessResponse
import com.mustafa.movieapp.api.LiveDataCallAdapterFactory
import com.mustafa.movieapp.api.MovieService
import com.mustafa.movieapp.api.TvService
import com.mustafa.movieapp.utils.LiveDataTestUtil.getValue
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MovieServiceTest : ApiHelperAbstract<MovieService>() {

    private lateinit var service: MovieService

    @Before
    fun initService() {
        this.service = createService(MovieService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchMovieKeywordsTest() {
        enqueueResponse("/keywords.json")
        val keywordResponse = getValue(service.fetchKeywords(1)) as ApiSuccessResponse

        assertRequestPath("/3/movie/1/keywords")
        assertThat(keywordResponse.body.id, `is`(100))
        assertThat(keywordResponse.body.keywords[0].id, `is`(1992))
        assertThat(keywordResponse.body.keywords[0].name, `is`("super hero"))
    }

    @Throws(IOException::class)
    @Test
    fun fetchMovieVideosTest() {
        enqueueResponse("/movie_videos.json")
        val movieVideosResponse = getValue(service.fetchVideos(1)) as ApiSuccessResponse
        assertThat(movieVideosResponse.body.id, `is`(550))
        assertThat(movieVideosResponse.body.results[0].id, `is`("1"))
        assertThat(movieVideosResponse.body.results[0].key, `is`("key"))
    }

    @Throws(IOException::class)
    @Test
    fun fetchMovieReviewsTest() {
        enqueueResponse("/movie_reviews.json")
        val reviewsResponse = getValue(service.fetchReviews(1)) as ApiSuccessResponse
        assertThat(reviewsResponse.body.id, `is`(297761))
        assertThat(reviewsResponse.body.results[0].id, `is`("1"))
        assertThat(reviewsResponse.body.results[0].author, `is`("Mustafa"))
        assertThat(reviewsResponse.body.results[0].content, `is`("That is a great Movie"))
    }
}
