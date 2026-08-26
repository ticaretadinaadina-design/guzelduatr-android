package com.guzelduatr.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzelduatr.app.data.ZikirRepository
import com.guzelduatr.app.data.local.ZikirEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ZikirUiItem(val id: Long, val isim: String, val hedef: Int, val sayac: Int)

@HiltViewModel
class ZikirViewModel @Inject constructor(
    private val repo: ZikirRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<ZikirUiItem>>(emptyList())
    val items: StateFlow<List<ZikirUiItem>> = _items.asStateFlow()

    init {
        viewModelScope.launch {
            // seed defaults if empty
            val defaults = listOf(
                ZikirEntity(isim = "Sübhanallah", hedef = 33),
                ZikirEntity(isim = "Elhamdülillah", hedef = 33),
                ZikirEntity(isim = "Allahu Ekber", hedef = 33),
                ZikirEntity(isim = "Lâ ilâhe illallah", hedef = 100),
                ZikirEntity(isim = "Estağfirullah", hedef = 100)
            )
            repo.seedIfEmpty(defaults)

            repo.getAllZikir().collect { list ->
                _items.value = list.map { ZikirUiItem(it.id, it.isim, it.hedef, it.sayac) }
            }
        }
    }

    fun increment(id: Long) {
        viewModelScope.launch { repo.increment(id) }
    }

    fun decrement(id: Long) {
        viewModelScope.launch { repo.decrement(id) }
    }

    fun reset(id: Long) {
        viewModelScope.launch { repo.reset(id) }
    }
}
