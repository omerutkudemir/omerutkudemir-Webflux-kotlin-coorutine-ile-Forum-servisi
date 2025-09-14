package com.poslifay.Poslifay_social_service.converters
import dto.EntryDtoForList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.zip
import model.Entry
import org.springframework.stereotype.Component

@Component
class ConvertToEntryDtoForList {
    suspend fun convert(entries: Flow<Entry>, names: Flow<String>): Flow<EntryDtoForList> {
        return entries.zip(names) { entry, name ->
            EntryDtoForList(name, entry.subjectHeader)
        }
    }
}
