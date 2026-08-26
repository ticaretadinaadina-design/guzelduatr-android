package com.guzelduatr.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzelduatr.app.data.DuaRepository
import com.guzelduatr.app.data.local.DuaEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuaViewModel @Inject constructor(
    private val repo: DuaRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<DuaEntity>>(emptyList())
    val items: StateFlow<List<DuaEntity>> = _items.asStateFlow()

    init {
        viewModelScope.launch {
            val defaults = listOf(
                DuaEntity(baslik = "Sabah Duası", kategori = "Sabah", icerik = "Allahüm..."),
                DuaEntity(baslik = "Akşam Duası", kategori = "Akşam", icerik = "Allahu..."),
            )
            repo.seedIfEmpty(defaults)

            repo.getAll().collect { list ->
                _items.value = list
            }
        }
    }

    fun toggleFavorite(id: Long, current: Boolean) {
        viewModelScope.launch { repo.toggleFavorite(id, !current) }
    }

    fun search(q: String) {
        viewModelScope.launch {
            repo.search(q).collect { list ->
                _items.value = list
            }
        }
    }
}
