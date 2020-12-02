package com.example.takenote.viewmodel

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SharedViewModelTest {

    private lateinit var sharedViewModel: SharedViewModel

    @Before
    fun setup() {
        sharedViewModel = SharedViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun `empty title returns false`() {
        sharedViewModel = SharedViewModel(ApplicationProvider.getApplicationContext())
        val result = sharedViewModel.verifyData(emptyTitle, description)
        assertThat(result).isFalse()
    }

    @Test
    fun `empty description returns false`() {
        sharedViewModel = SharedViewModel(ApplicationProvider.getApplicationContext())
        val result = sharedViewModel.verifyData(title, emptyDescription)
        assertThat(result).isFalse()
    }

    @Test
    fun `empty title and description returns false`() {
        sharedViewModel = SharedViewModel(ApplicationProvider.getApplicationContext())
        val result = sharedViewModel.verifyData(emptyTitle, emptyDescription)
        assertThat(result).isFalse()
    }

    @Test
    fun `not empty title and description returns true`() {
        sharedViewModel = SharedViewModel(ApplicationProvider.getApplicationContext())
        val result = sharedViewModel.verifyData(title, description)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid url returns empty string`() {
        sharedViewModel = SharedViewModel(ApplicationProvider.getApplicationContext())
        val result = sharedViewModel.validateImageUrl(invalidURL)
        assertThat(result).isEmpty()
    }

    @Test
    fun `valid url return itself`() {
        sharedViewModel = SharedViewModel(ApplicationProvider.getApplicationContext())
        val result = sharedViewModel.validateImageUrl(validURL)
        assertThat(result).isEqualTo(validURL)
    }

    @Test
    fun `empty url input returns empty string`() {
        sharedViewModel = SharedViewModel(ApplicationProvider.getApplicationContext())
        val result = sharedViewModel.validateImageUrl(emptyURL)
        assertThat(result).isEmpty()
    }
}
