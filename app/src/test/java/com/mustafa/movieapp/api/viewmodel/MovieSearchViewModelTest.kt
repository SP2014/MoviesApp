package com.mustafa.movieapp.api.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mustafa.movieapp.models.Resource
import com.mustafa.movieapp.models.entity.Movie
import com.mustafa.movieapp.repository.DiscoverRepository
import com.mustafa.movieapp.utils.MockTestUtil.Companion.mockMovie
import com.mustafa.movieapp.view.ui.search.MovieSearchViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class MovieSearchViewModelTest {


    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieSearchViewModel

    private val repository = mock<DiscoverRepository>()

    @Before
    fun init() {
        viewModel = MovieSearchViewModel(repository)
    }

    @Test
    fun movieSuggestionsNullQueryTest() {
        val observer = mock<Observer<List<Movie>>>()
        viewModel.movieSuggestions.observeForever(observer)
        viewModel.setMovieSuggestionsQuery(null)
        verifyNoMoreInteractions(repository)
        verify(repository, never()).getMovieSuggestionsFromRoom(anyString())
    }

    @Test
    fun searchMoviesTest() {
        val observer = mock<Observer<Resource<List<Movie>>>>()
        val searchMoviesResultLiveData = MutableLiveData<Resource<List<Movie>>>()
        val searchMovies = Resource.success(listOf(mockMovie()), true)

        `when`(repository.searchMovies(anyString(), anyInt())).thenReturn(
            searchMoviesResultLiveData
        )
        viewModel.searchMovieListLiveData.observeForever(observer)
        viewModel.setSearchMovieQueryAndPage("query", 1)
        searchMoviesResultLiveData.postValue(searchMovies)
        verify(repository).searchMovies("query", 1)
        verify(observer).onChanged(searchMovies)
    }

    @Test
    fun searchMoviesNullQueryOrEmptyTest() {
        val observer = mock<Observer<Resource<List<Movie>>>>()
        viewModel.searchMovieListLiveData.observeForever(observer)
        viewModel.setSearchMovieQueryAndPage("", 1)
        verifyNoMoreInteractions(repository)
        verify(repository, never()).searchMovies(anyString(), anyInt())

        viewModel.setSearchMovieQueryAndPage(null, 1)
        verifyNoMoreInteractions(repository)
        verify(repository, never()).searchMovies(anyString(), anyInt())

    }

    @Test
    fun movieSuggestionsTest() {
        val observer = mock<Observer<List<Movie>>>()
        val suggestionResultLiveData = MutableLiveData<List<Movie>>()
        val movieSuggestions = listOf(mockMovie())

        `when`(repository.getMovieSuggestionsFromRoom(anyString())).thenReturn(
            suggestionResultLiveData
        )
        viewModel.movieSuggestions.observeForever(observer)
        viewModel.setMovieSuggestionsQuery("Movie")
        suggestionResultLiveData.postValue(movieSuggestions)
        verify(repository).getMovieSuggestionsFromRoom("Movie")
        verify(observer).onChanged(movieSuggestions)
    }
}